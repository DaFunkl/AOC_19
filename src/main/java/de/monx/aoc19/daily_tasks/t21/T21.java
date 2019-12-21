package de.monx.aoc19.daily_tasks.t21;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T21 extends TDay {

	@Override
	public TDay exec() {
		long[] in = getInput();
		System.out.println("Part1: " + part1(in));
//		System.out.println("Part2: " + part2(in));
		return this;
	}

	int part1(long[] opCode) {
		IntCode ic = new IntCode();
		ic.setStack(opCode);
		int state = ic.execIO();
		printOut(ic.getAndResetOutput());
		String input = ""//
		// resetting T
//				+ "NOT A T\n" //
//				+ "AND D T\n" //
//				+ "OR T J\n" //
				
//				+ "NOT B T\n" //
				+ "NOT C J\n" //
				+ "AND T J\n" //
				+ "NOT A T\n" //
				+ "OR T J\n" //
				+ "AND D J\n" //

				+ "WALK\n";
		printOut(ic.getAndResetOutput());
		for (char c : input.toCharArray()) {
			System.out.print(c);
			ic.setInput((int) c);
			ic.execIO();
		}
		List<Long> ll = ic.getOutputList();
		printOut(ll);
		System.out.println(Arrays.toString(ll.toArray()));
		return 0;
	}

	void printOut(List<Long> out) {
//		System.out.println("LongList: " + Arrays.toString(out.toArray()));
		StringBuilder sb = new StringBuilder();
		for (long l : out) {
			if (l < Integer.MAX_VALUE && l > Integer.MIN_VALUE) {
				sb.append((char) l);
			} else {
				return;
			}
		}
		System.out.println(sb.toString());
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
