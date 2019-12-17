package de.monx.aoc19.daily_tasks.t17;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T17 extends TDay {

	@Override
	public TDay exec() {
		long[] input = getInput();
		int[][] grid = getGrid(input);
		System.out.println("Part1: " + part1(grid));
		System.out.println("Part2: " + part2(input));
		return this;
	}

	long part2(long[] input) {
		// parsed grid by hand, input/17/a0c19_17.xslx contains resolution of moves to inarr
		String[] inarr = { //
				"A,B,A,B,C,C,B,C,B,A", //
				"R,12,L,8,R,12", //
				"R,8,R,6,R,6,R,8", //
				"R,8,L,8,R,8,R,4,R,4"//
		};
		input[0] = 2;
		IntCode ic = new IntCode();
		ic.setStack(input);
		int state = ic.execIO();
		int inArrI = 0;
		int inArrJ = 0;
		while (state != IntCode._STATE_HALT) {
			if (inArrI >= inarr.length) {
				ic.setInput((int) 'n');
				state = ic.execIO();
				ic.setInput(10);
				state = ic.execIO();
				break;
			} else if (inArrJ >= inarr[inArrI].length()) {
				ic.setInput(10);
				inArrI++;
				inArrJ = 0;
			} else {
				ic.setInput((int) inarr[inArrI].charAt(inArrJ++));
			}
			state = ic.execIO();
		}
		List<Long> out = ic.getOutputList();
		return out.get(out.size()-1);
	}

	void printGrid(int[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print((char) grid[y][x]);
			}
			System.out.println();
		}
	}

	int[][] getGrid(long[] input) {
		int[][] ret = new int[51][45];
		IntCode robo = new IntCode();
		robo.setStack(input);
		List<Long> out = robo.executeStack();
		return longListToGrid(ret, out);
	}

	private int[][] longListToGrid(int[][] ret, List<Long> out) {
		int outIdx = 0;
		for (int y = 0; y < ret.length; y++) {
			for (int x = 0; x < ret[0].length; x++) {
				long l = out.get(outIdx++);
				ret[y][x] = (int) l;
			}
			outIdx++;
		}
		return ret;
	}

	int part1(int[][] grid) {
		int sum = 0;
		int pathNum = (int) '#';
		for (int y = 1; y < grid.length - 1; y++) {
			for (int x = 1; x < grid[0].length - 1; x++) {
				if (grid[y][x] == pathNum && grid[y + 1][x] == pathNum && grid[y - 1][x] == pathNum
						&& grid[y][x + 1] == pathNum && grid[y][x - 1] == pathNum) {
					sum += x * y;
				}
			}
		}
		return sum;
	}

	long[] getInput() {
		String[] sar = stream.iterator().next().split(",");
		long[] input = new long[sar.length];
		for (int i = 0; i < input.length; i++) {
			input[i] = Long.valueOf(sar[i]);
		}
		return input;
	}
}
