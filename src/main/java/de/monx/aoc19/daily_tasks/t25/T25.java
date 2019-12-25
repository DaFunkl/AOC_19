package de.monx.aoc19.daily_tasks.t25;

import java.util.List;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T25 extends TDay {

	@Override
	public TDay exec() {
		long[] stack = getInput();
		System.out.println("Part1: " + part1(stack));
//		System.out.println("Part2: " + part2(in));
		return this;
	}

	int part1(long[] stack) {
		IntCode ic = new IntCode();
		ic.setStack(stack);
		int state = IntCode._STATE_NONE;
		while (state != IntCode._STATE_HALT) {
			state = ic.execIO();
			printOut(ic.getAndResetOutput());
			String input = fetchInput();
			if(input.equals("w")) {
				input = "north";
			} else if(input.equals("d")) {
				input = "east";
			} else if(input.equals("s")) {
				input = "south";
			} else if(input.equals("a")) {
				input = "west";
			}
			for(int i : inputToIntArray(input)) {
				ic.setInput(i);
				ic.execIO();
			}
		}
		List<Long> out = ic.getAndResetOutput();
		printOut(out);

		return 0;
	}

	String fetchInput() {
		return BF.haltInput();
	}
	
	int[] inputToIntArray(String in) {
		int[] input = new int[in.length() + 1];
		int i = 0;
		for (char c : in.toCharArray()) {
			input[i++] = (int) c;
		}
		input[input.length - 1] = '\n';
		return input;
	}

	void printOut(List<Long> out) {
		for (long l : out) {
			System.out.print((char) l);
			if(l > (int) 'z') {
				System.out.println("===> " + l);
			}
		}
		System.out.println();
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
