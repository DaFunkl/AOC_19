package de.monx.aoc19.daily_tasks.t15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc19.daily_tasks.t03.Boundry;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.intcode.IntCode;

public class T15 extends TDay {

	@Override
	public TDay exec() {
		long[] ic = getInput();

		return this;
	}

	final static int _WALL = 0;
	final static int _FREE = 1;
	final static int _OXYG = 2;
	final static int _BOTY = 3;
	final static int _NONO = 4;

	int part1(long[] input) {
		IntCode ic = new IntCode();
		ic.execIO();
		Map<Vec2, Integer> grid = new HashMap<>();
		Map<Vec2, IntCode> states = new HashMap<>();
		List<Vec2> todos = new ArrayList<>();
		Set<Vec2> done = new HashSet<>();
		todos.add(new Vec2());
		states.put(new Vec2(), ic.clone());
		grid.put(new Vec2(), _FREE);
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
					if (grid.containsKey(newPos))
						grid.put(newPos, res);
					if (!todos.contains(newPos)) {
						todos.add(newPos.clone());
						states.put(newPos.clone(), newIC.clone());
					}
				} else {
					System.err.println("Wrong output: " + res + " -> " + newPos);
				}
			}
//			printG
		}

		ic.setStack(input);

		return 0;
	}

	Vec2[] dirMoves = { //
			new Vec2(0, -1), // up
			new Vec2(0, 1), // down
			new Vec2(-1, 0), // left
			new Vec2(1, 0), // right
	};

	void drawGrid(Map<Vec2, Integer> grid, Vec2 bot, Boundry b) {
		char[] car = { '#', ' ', 'X', 'B', '.' };
		for (int y = b.getMin().y; y <= b.getMax().y; y++) {
			for (int x = b.getMin().x; x <= b.getMax().x; x++) {
				Vec2 v = new Vec2(x, y);
				if (v.equals(bot)) {
					System.out.print(car[_BOTY]);
				} else if (grid.containsKey(v)) {
					System.out.print(car[grid.get(v)]);
				} else {
					System.out.print(car[_NONO]);
				}
			}
			System.out.println();
		}
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
