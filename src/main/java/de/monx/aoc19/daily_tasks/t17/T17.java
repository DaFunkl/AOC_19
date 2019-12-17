package de.monx.aoc19.daily_tasks.t17;

import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T17 extends TDay {

	@Override
	public TDay exec() {
		long[] input = getInput();
		System.out.println("Part1: " + part1(input));
		return this;
	}

	int part1(long[] input) {
		IntCode robo = new IntCode();
		robo.setStack(input);
		int result = 0;
		List<Long> ret = robo.executeStack();
		for (long l : ret) {
			int i = (int) l;
			System.out.print((char) i);
		}
		System.out.println();
		return result;
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
