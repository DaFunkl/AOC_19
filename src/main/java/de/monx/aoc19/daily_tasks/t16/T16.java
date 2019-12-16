package de.monx.aoc19.daily_tasks.t16;

import java.util.Arrays;

import de.monx.aoc19.helper.TDay;

public class T16 extends TDay {
	final static int[] pattern = { 0, 1, 0, -1 };

	@Override
	public TDay exec() {
		int[] input = getInput();
		System.out.println("Part1: " + part1(input, 100));
		return this;
	}

	int part1(int[] input, int steps) {
		int[] ret = new int[8];
		int i = 0;
		for (int j = input.length - 8; j < input.length; j++) {
			ret[i++] = input[j];
		}
		for (int step = 0; step < steps; step++) {
			System.out.println("Step: " + step + " -> " + Arrays.toString(ret));
			int[] temp = new int[ret.length];
			for (i = 0; i < ret.length; i++) {
				int patternIdx = 0;
				int piC = input.length - 8;
				for (int j = 0; j < ret.length; j++) {
					if (piC++ <= (input.length - 8 + i)) {
						patternIdx = (patternIdx + 1) % pattern.length;
						piC = input.length - 8;
					}
					int mult = pattern[patternIdx];
					if (mult == 0 || ret[j] == 0) {
						continue;
					}
					temp[i] = temp[i] + ret[j] * mult;
				}
			}
			for (i = 0; i < ret.length; i++) {
				ret[i] = Math.abs(temp[i]) % 10;
			}
		}
		System.out.println("Step: " + steps + " -> " + Arrays.toString(ret));
		return 0;
	}

	int[] getInput() {
		char[] car = stream.iterator().next().toCharArray();
		int[] ret = new int[car.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) car[i] - (int) '0';
		}
		return ret;
	}
}
