package de.monx.aoc19.daily_tasks.t15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc19.daily_tasks.t03.Boundry;
//import de.monx.aoc19.helper.Animation;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.Vec3;
import de.monx.aoc19.helper.intcode.IntCode;

public class T15 extends TDay {
	Vec2[] dirMoves = { //
			new Vec2(0, -1), // up
			new Vec2(0, 1), // down
			new Vec2(-1, 0), // left
			new Vec2(1, 0), // right
	};

	final static int _WALL = 0;
	final static int _FREE = 1;
	final static int _OXYG = 2;
	final static int _BOTY = 3;
	final static int _NONO = 4;
	final static char[] car = { //
			'█', // _WALL = 0;
			' ', // _FREE = 1;
			'X', // _OXYG = 2;
			'B', // _BOTY = 3;
			'#' // _NONO = 4;
	};

	@Override
	public TDay exec() {
		long[] input = getInput();
		int[][] grid = getGrid(input);
//		System.out.println("Part1: " + part1(grid));
//		System.out.println("Part2: " + part2(grid));
		execBoth(grid);
		return this;
	}

	void execBoth(int[][] grid) {
		Vec3 start = findWhat(grid, _OXYG);
		List<Vec3> todo = new ArrayList<>();
		todo.add(start);
		List<Vec3> done = new ArrayList<>();
		int max = 0;
		while (!todo.isEmpty()) {
			Vec3 v = todo.get(0);
			if (v.z > max) {
				max = v.z;
			}
			todo.remove(0);
			if (grid[v.y][v.x] == _BOTY) {
				System.out.println("found: " + v);
				System.out.println("Part1: " + v.z);
			}
			addTodos(v, grid, done, todo);
			done.add(v);
		}
		System.out.println("Part2: " + max);
	}

	int part2(int[][] grid) {
		Vec3 start = findWhat(grid, _OXYG);
		List<Vec3> todo = new ArrayList<>();
		todo.add(start);
		List<Vec3> done = new ArrayList<>();
		int max = 0;
		while (!todo.isEmpty()) {
			Vec3 v = todo.get(0);
			if (v.z > max) {
				max = v.z;
			}
			todo.remove(0);
			addTodos(v, grid, done, todo);
			done.add(v);
		}
		return max;
	}

	int part1(int[][] grid) {
		Vec3 start = findWhat(grid, _BOTY);
		List<Vec3> todo = new ArrayList<>();
		todo.add(start);
		List<Vec3> done = new ArrayList<>();
		while (!todo.isEmpty()) {
			Vec3 v = todo.get(0);
			todo.remove(0);
			if (grid[v.y][v.x] == _OXYG) {
				System.out.println("found: " + v);
				return v.z;
			}
			addTodos(v, grid, done, todo);
			done.add(v);
		}

		return 0;
	}

	void addTodos(Vec3 pos, int[][] grid, List<Vec3> done, List<Vec3> todo) {
		for (int i = 0; i < dirMoves.length; i++) {
			Vec2 v2 = dirMoves[i];
			Vec3 v = pos.add(new Vec3(v2.x, v2.y, 1));
			if (containsPos(done, v) || containsPos(todo, v)) {
				continue;
			}
			if (grid[v.y][v.x] == _FREE || grid[v.y][v.x] == _OXYG || grid[v.y][v.x] == _BOTY) {
				todo.add(v);
			}
		}
	}

	boolean containsPos(List<Vec3> l, Vec3 p) {
		for (Vec3 v : l) {
			if (v.x == p.x && v.y == p.y) {
				return true;
			}
		}
		return false;
	}

	Vec3 findWhat(int[][] grid, int what) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[y][x] == what) {
					return new Vec3(x, y, 0);
				}
			}
		}
		return null;
	}

	int[][] getGrid(long[] input) {
		IntCode ic = new IntCode();
		ic.setStack(input);
		ic.execIO();
		Map<Vec2, Integer> grid = new HashMap<>();
		Map<Vec2, IntCode> states = new HashMap<>();
		List<Vec2> todos = new ArrayList<>();
		Set<Vec2> done = new HashSet<>();
		todos.add(new Vec2());
		states.put(new Vec2(), ic.clone());
		grid.put(new Vec2(), _FREE);
		
//		Animation animation = new Animation(500, 500);
//		animation.pane.drawGrid(gridToMatrix(grid, new Vec2()));
		
		while (!todos.isEmpty()) {
			Vec2 v = todos.get(0);
			todos.remove(0);
			for (int i = 0; i < dirMoves.length; i++) {
				IntCode newIC = states.get(v).clone();
				Vec2 newPos = v.add(dirMoves[i]);
				newIC.setInput(i + 1);
				newIC.execIO();
				int res = (int) newIC.getOutput();
				if (res == _WALL) {
					grid.put(newPos, res);
				} else if (res == _FREE || res == _OXYG) {
					if (!grid.containsKey(newPos)) {
						grid.put(newPos, res);
					}
					if (!todos.contains(newPos) && !done.contains(newPos)) {
						todos.add(newPos.clone());
						states.put(newPos.clone(), newIC.clone());
					}
				} else {
					System.err.println("Wrong output: " + res + " -> " + newPos);
				}
				done.add(newPos);		
//				animation.pane.drawGrid(gridToMatrix(grid, newPos));
			}
		}
//		animation.pane.drawGrid(gridToMatrix(grid, new Vec2()));
		return gridToMatrix(grid, new Vec2());
	}

	int[][] gridToMatrix(Map<Vec2, Integer> grid, Vec2 bot) {
		Boundry b = getBoundry(grid);
		int xSize = b.getMax().x - b.getMin().x + 1;
		int ySize = b.getMax().y - b.getMin().y + 1;
		int[][] ret = new int[ySize][xSize];
		int y = 0;
		for (int j = b.getMin().y; j <= b.getMax().y; j++) {
			int x = 0;
			for (int i = b.getMin().x; i <= b.getMax().x; i++) {
				Vec2 v = new Vec2(i, j);
				if (v.equals(bot)) {
					ret[y][x] = _BOTY;
				} else if (grid.containsKey(v)) {
					ret[y][x] = grid.get(v);
				} else {
					ret[y][x] = _NONO;
				}
				x++;
			}
			y++;
		}
		return ret;
	}

	void drawGrid(int[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				System.out.print(car[grid[y][x]]);
			}
			System.out.println();
		}
	}

	void drawGrid(Map<Vec2, Integer> grid, Vec2 bot) {
		Boundry b = getBoundry(grid);
		StringBuilder sb = new StringBuilder();
		sb.append("===============================================\n");
		for (int y = b.getMin().y; y <= b.getMax().y; y++) {
			for (int x = b.getMin().x; x <= b.getMax().x; x++) {
				Vec2 v = new Vec2(x, y);
				if (v.equals(bot)) {
					sb.append(car[_BOTY]);
				} else if (grid.containsKey(v)) {
					sb.append(car[grid.get(v)]);
				} else {
					sb.append(car[_NONO]);
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	Boundry getBoundry(Map<Vec2, Integer> grid) {
		Boundry ret = new Boundry();
		for (Vec2 v : grid.keySet()) {
			ret.refresh(v);
		}
		return ret;
	}

	long[] getInput() {
		String[] arr = stream.iterator().next().split(",");
		long[] ret = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = Long.valueOf(arr[i]);
		}
		return ret;
	}
}
