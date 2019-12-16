package de.monx.aoc19.daily_tasks.t16;

import de.monx.aoc19.helper.TDay;

public class T16 extends TDay {
	final static int[] pattern = { 0, 1, 0, -1 };

	@Override
	public TDay exec() {
		int[] input = getInput();
		int[] p1Res = part1(input, 100);
		System.out.println("Part1: " + getFirstDigits(p1Res, 8, 0));
		System.out.println("Part2: " + part2(input, 100));
		return this;
	}

	int part2(int[] input, int steps) {
		int[] ret = new int[input.length * 10000];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = input[i % input.length];
		}
		int offset = getFirstDigits(input, 7, 0);
		for (int i = 0; i < 100; i++) {
			for (int j = ret.length - 1; j >= ret.length / 2; j--) {
				ret[j] = j == ret.length - 1 ? ret[j] : ret[j + 1] + ret[j];
				ret[j] %= 10;
			}
		}
		return getFirstDigits(ret, 8, offset);
	}

	int[] part1(int[] input, int steps) {
		int[] ret = input.clone();
		for (int i = 0; i < 100; i++) {
			for (int j = ret.length - 1; j >= ret.length / 2; j--) {
				ret[j] = j == ret.length - 1 ? ret[j] : ret[j + 1] + ret[j];
				ret[j] %= 10;
			}
		}
		for (int step = 0; step < steps; step++) {
			int[] temp = new int[ret.length];
			for (int i = 0; i < ret.length; i++) {
				int patternIdx = 0;
				int piC = 0;
				for (int j = 0; j < ret.length; j++) {
					if (piC++ >= i) {
						patternIdx = (patternIdx + 1) % pattern.length;
						piC = 0;
					}
					int mult = pattern[patternIdx];
					if (mult == 0 || ret[j] == 0) {
						continue;
					}
					temp[i] += ret[j] * mult;
				}
			}
			for (int i = 0; i < ret.length; i++) {
				ret[i] = Math.abs(temp[i]) % 10;
			}
		}
		return ret;
	}

	int getFirstDigits(int[] ret, int amt, int offset) {
		String res = "";
		for (int i = offset; i < offset + amt; i++) {
			res = res + ret[i];
		}
		return Integer.valueOf(res);
	}

	int[] getInput() {
		String in = stream.iterator().next();
		char[] car = in.toCharArray();
		int[] ret = new int[car.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) car[i] - (int) '0';
		}
		return ret;
	}
}
