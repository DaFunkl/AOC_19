package de.monx.aoc19.daily_tasks.t09;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T09 extends TDay {
	int inputP1 = 0;
	int inputP2 = 0;

	public T09 setInputP1(int input) {
		inputP1 = input;
		return this;
	}

	public T09 setInputP2(int input) {
		inputP2 = input;
		return this;
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
		long[] stack = IntCode.parseLineToStack(line);
		part1(stack.clone());
		part2(stack);
		return this;
	}

	private void part1(long[] stack) {
		IntCode intCode = new IntCode();
		intCode.init(new int[] { inputP1 }, stack);
		List<Long> ret = intCode.executeStack();
		System.out.println("Part1: " + Arrays.toString(ret.toArray()));
	}

	private void part2(long[] stack) {
		IntCode intCode = new IntCode();
		intCode.init(new int[] { inputP2 }, stack);
		List<Long> ret = intCode.executeStack();
		System.out.println("Part2: " + Arrays.toString(ret.toArray()));
	}
}
