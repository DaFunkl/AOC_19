package de.monx.aoc19.daily_tasks.t24;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.monx.aoc19.daily_tasks.t03.Boundry;
import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;

public class T24 extends TDay {
	private static List<Integer> lookUp2Exp = new ArrayList<>();
	final static Vec2[] _DIRS = { //
			new Vec2(0, -1), // up
			new Vec2(-1, 0), // left
			new Vec2(1, 0), // right
			new Vec2(0, 1), // down
	};
	final int _UP = 0;
	final int _LEFT = 1;
	final int _RIGHT = 2;
	final int _DOWN = 3;

	private void initLookUp2Exp() {
		lookUp2Exp.add(0);
		lookUp2Exp.add(1);
	}

	@Override
	public TDay exec() {
		initLookUp2Exp();
		boolean[][] input = getInput();
		System.out.println("Part1: " + part1(input));
		System.out.println("Part2: " + part2(input));
		return this;
	}

	int part2(boolean[][] input) {
		List<boolean[][]> grids = new ArrayList<>();
		grids.add(input);

		for (int step = 0; step < 200; step++) {
			List<boolean[][]> newGrids = new ArrayList<>();

			boolean[][] grid = spawnInnerGrid(grids.get(0));
			if (grid != null) {
				newGrids.add(grid);
			}
			for (int i = 0; i < grids.size(); i++) {
				boolean[][] innerGrid = i == 0 ? null : grids.get(i - 1);
				boolean[][] currentGrid = grids.get(i);
				boolean[][] outerGrid = i == grids.size() - 1 ? null : grids.get(i + 1);
				grid = new boolean[5][5];
				for (int y = 0; y < 5; y++) {
					for (int x = 0; x < 5; x++) {
						if (x == 2 && y == 2) {
							continue;
						}
						int count = getCount(x, y, innerGrid, currentGrid, outerGrid);
						if (currentGrid[y][x]) {
//							A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
							grid[y][x] = count == 1;
						} else if (!currentGrid[y][x]) {
//							An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
							grid[y][x] = count == 1 || count == 2;
						}

					}
				}
				newGrids.add(grid);
			}
			grid = spawnOuterGrid(grids.get(grids.size() - 1));
			if (grid != null) {
				newGrids.add(grid);
			}
			grids = newGrids;
		}
		int count = countGrids(grids);
		return count;
	}

	int countGrids(List<boolean[][]> grids) {
		int count = 0;
		for (boolean[][] grid : grids) {
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 5; x++) {
					if (y == 2 && x == 2) {
						continue;
					}
					count += grid[y][x] ? 1 : 0;
				}
			}
		}
		return count;
	}

	int getCount(int x, int y, boolean[][] inner, boolean[][] grid, boolean[][] outer) {
		int count = 0;
		for (Vec2 d : _DIRS) {
			int xx = d.x + x;
			int yy = d.y + y;
			if (isOuterInvalid(outer, xx, yy) || isInnerInvalid(inner, xx, yy)) {
				continue;
			}
			// outer Rules
			if (xx < 0) { // outer left
				count += outer[2][1] ? 1 : 0;
			} else if (xx >= 5) { // outer right
				count += outer[2][3] ? 1 : 0;
			} else if (yy < 0) { // outer up
				count += outer[1][2] ? 1 : 0;
			} else if (yy >= 5) { // outer down
				count += outer[3][2] ? 1 : 0;
			} else if (xx == 2 && yy == 2) { // inner Rules
				if (x == 1) { // inner right
					for (int i = 0; i < 5; i++) {
						count += inner[i][0] ? 1 : 0;
					}
				} else if (x == 3) { // inner left
					for (int i = 0; i < 5; i++) {
						count += inner[i][4] ? 1 : 0;
					}

				} else if (y == 1) { // inner down
					for (int i = 0; i < 5; i++) {
						count += inner[0][i] ? 1 : 0;
					}

				} else if (y == 3) { // inner up
					for (int i = 0; i < 5; i++) {
						count += inner[4][i] ? 1 : 0;
					}
				} else {
					System.err.println("Error: " + x + ", " + y);
					printGrid(inner);
					printGrid(grid);
					printGrid(outer);
				}
			} else {
				count += grid[yy][xx] ? 1 : 0;
			}
		}
		return count;
	}

	boolean isOuterInvalid(boolean[][] outer, int xx, int yy) {
		return outer == null && (xx < 0 || yy < 0 || xx >= 5 || yy >= 5);
	}

	boolean isInnerInvalid(boolean[][] inner, int xx, int yy) {
		return inner == null && xx == 2 && yy == 2;
	}

	boolean[][] spawnOuterGrid(boolean[][] inner) {
		boolean[][] grid = new boolean[5][5];
		int[] vals = new int[4];
		// up
		for (int i = 0; i < 5; i++) {
			vals[_UP] += inner[0][i] ? 1 : 0;
		}
		// down
		for (int i = 0; i < 5; i++) {
			vals[_DOWN] += inner[4][i] ? 1 : 0;
		}
		// left
		for (int i = 0; i < 5; i++) {
			vals[_LEFT] += inner[i][0] ? 1 : 0;
		}
		// right
		for (int i = 0; i < 5; i++) {
			vals[_RIGHT] += inner[i][4] ? 1 : 0;
		}

		boolean isNeeded = false;
		if (vals[_UP] == 1 || vals[_UP] == 2) {
			grid[1][2] = true;
			isNeeded = true;
		}
		if (vals[_DOWN] == 1 || vals[_DOWN] == 2) {
			grid[3][2] = true;
			isNeeded = true;
		}
		if (vals[_LEFT] == 1 || vals[_LEFT] == 2) {
			grid[2][1] = true;
			isNeeded = true;
		}
		if (vals[_RIGHT] == 1 || vals[_RIGHT] == 2) {
			grid[2][3] = true;
			isNeeded = true;
		}
		if (isNeeded) {
			return grid;
		} else {
			return null;
		}
	}

	boolean[][] spawnInnerGrid(boolean[][] outer) {
		boolean[][] grid = new boolean[5][5];
		boolean isNeeded = false;

		boolean[] vals = new boolean[4];
		vals[_UP] = outer[1][2]; // up
		vals[_DOWN] = outer[3][2]; // down
		vals[_LEFT] = outer[2][1]; // left
		vals[_RIGHT] = outer[2][3]; // right
		if (vals[_UP]) {
			for (int i = 0; i < 5; i++) {
				grid[0][i] = true;
			}
			isNeeded = true;
		}
		if (vals[_DOWN]) {
			for (int i = 0; i < 5; i++) {
				grid[4][i] = true;
			}
			isNeeded = true;
		}
		if (vals[_LEFT]) {
			for (int i = 0; i < 5; i++) {
				grid[i][0] = true;
			}
			isNeeded = true;
		}
		if (vals[_RIGHT]) {
			for (int i = 0; i < 5; i++) {
				grid[i][4] = true;
			}
			isNeeded = true;
		}
		if (isNeeded) {
			return grid;
		} else {
			return null;
		}
	}

	int part1(boolean[][] input) {
		boolean[][] grid = input.clone();
		Set<Integer> mem = new HashSet<>();
		int hash = calcHash(grid);
		mem.add(hash);

		hash = 0;
		while (true) {
			boolean[][] newGrid = new boolean[grid.length][grid[0].length];
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[y].length; x++) {
					int count = 0;
					for (Vec2 d : _DIRS) {
						Vec2 v = new Vec2(x, y).add(d);
						if (v.x < 0 || v.y < 0 || v.x >= grid[y].length || v.y >= grid.length) {
							continue;
						}
						if (grid[v.y][v.x]) {
							count++;
						}
					}
					if (grid[y][x]) {
//						A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
						newGrid[y][x] = count == 1;
					} else if (!grid[y][x]) {
//						An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
						newGrid[y][x] = count == 1 || count == 2;
					}
				}
			}
			grid = newGrid;
			hash = calcHash(grid);
			if (mem.contains(hash)) {
				return hash;
			}
			mem.add(hash);
		}
	}

	int calcHash(boolean[][] grid) {
		int hash = 0;
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x]) {
					int idx = x + 1 + y * (grid[0].length);
					hash += fetch2Exp(idx);
				}
			}
		}
		return hash;
	}

	void printGrid(boolean[][] grid) {
		if (grid == null) {
			return;
		}
		for (boolean[] bar : grid) {
			for (boolean b : bar) {
				System.out.print(b ? '#' : '.');
			}
			System.out.println();
		}
	}

	int calcIndex(Vec2 v, Boundry bounds) {
		return (v.x - bounds.getMin().x + 1) // x val
				+ (v.y - bounds.getMin().y) // y val
						* (bounds.getMax().x - bounds.getMin().x + 1) // multiply y val by x size
		;
	}

	int fetch2Exp(int idx) {
		while (idx >= lookUp2Exp.size()) {
			lookUp2Exp.add(lookUp2Exp.get(lookUp2Exp.size() - 1) << 1);
		}
		return lookUp2Exp.get(idx);
	}

	boolean[][] getInput() {
		Iterator<String> it = stream.iterator();
		boolean[][] input = new boolean[5][5];
		int y = 0;
		while (it.hasNext()) {
			char[] car = it.next().toCharArray();
			for (int x = 0; x < car.length; x++) {
				if (car[x] == '#') {
					input[y][x] = true;
				}
			}
			y++;
		}
		return input;
	}
}
