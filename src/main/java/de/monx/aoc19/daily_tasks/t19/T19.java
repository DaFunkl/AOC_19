package de.monx.aoc19.daily_tasks.t19;

import java.util.List;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.intcode.IntCode;

public class T19 extends TDay {

	@Override
	public TDay exec() {
		long[] input = getInput();
		System.out.println("Part1: " + part1(input));
		System.out.println("Part2: " + part2(input));
		return this;
	}

	int part2(long[] in) {
		int size = 2000;
		int[][] grid = new int[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				grid[y][x] = checkPosition(x, y, in);
			}
		}
//		BF.printToFile(grid, getPath() + "out2.csv");
//		printGridExcel(grid);
//		Vec2 v = findSquare(grid);
//		return v.x * 10000 + v.y;
		return 0;
	}

	Vec2 findSquare(int[][] grid) {
		Vec2 ret = null;
		for (int y = 900; y < grid.length; y++) {
			for (int x = 900; x < grid.length; x++) {
				
			}
		}
		return ret;
	}

	int part1(long[] in) {
		int size = 50;
		int[][] grid = new int[size][size];
		int count = 0;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				grid[y][x] = checkPosition(x, y, in);
				count += grid[y][x];
			}
		}
		return count;
	}

	void printGridExcel(int[][] grid) {
		for (int[] g : grid) {
			for (int i : g) {
				System.out.print(i + ",");
			}
			System.out.println();
		}
	}

	void printGrid(int[][] grid) {
		for (int[] g : grid) {
			for (int i : g) {
				System.out.print(i);
			}
			System.out.println();
		}
	}

	int checkPosition(int x, int y, long[] in) {
		IntCode ic = new IntCode();
		ic.setStack(in);
		printOut(ic.getOutputList());
		ic.setInput(x);
		ic.execIO();
		ic.setInput(y);
		ic.execIO();
		return (int) ic.getOutput();
	}

	void printOut(List<Long> list) {
		for (Long s : list) {
			System.out.println(s);
		}
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
