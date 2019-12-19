package de.monx.aoc19.daily_tasks.t19;

import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T19 extends TDay {

	@Override
	public TDay exec() {
		long[] input = getInput();
		System.out.println("Part1: " + part1(input));
		return this;
	}

	int part1(long[] in) {
		IntCode ic = new IntCode();
		ic.setStack(in);
		printOut(ic.getOutputList());
		int state = ic.execIO();
		for (int y = 0; y < 50; y++) {
			for (int x = 0; x < 50; x++) {
				ic.setInput(x);
				state = ic.execIO();
				ic.setInput(y);
				state = ic.execIO();
			}
		}
		printOut(ic.getAndResetOutput());
		return 0;
	}

	void printOut(List<Long> list) {
		System.out.println("print:");
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
