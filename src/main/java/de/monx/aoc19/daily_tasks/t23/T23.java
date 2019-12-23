package de.monx.aoc19.daily_tasks.t23;

import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T23 extends TDay {

	@Override
	public TDay exec() {
		long[] in = getInput();
		System.out.println("Part1: " + part1(in));

		return this;
	}

	String part1(long[] stack) {
		IntCode[] ic = initNIC(stack, 50);

		return null;
	}

	IntCode[] initNIC(long[] stack, int amt) {
		IntCode[] ic = new IntCode[50];

		System.out.println("init");
		for (int i = 0; i < ic.length; i++) {
			ic[i] = new IntCode();
			ic[i].setStack(stack);
			ic[i].execIO();
			printIC_OUT(ic[i], i);
		}
		System.out.println();
		System.out.println("Insert ID");
		for (int i = 0; i < ic.length; i++) {
			ic[i].setInput(i);
			ic[i].execIO();
			printIC_OUT(ic[i], i);
		}
		System.out.println();
		System.out.println("Insert -1");
		for (int i = 0; i < ic.length; i++) {
			ic[i].setInput(-1);
			ic[i].execIO();
			printIC_OUT(ic[i], i);
		}

		return ic;
	}

	void printIC_OUT(IntCode ic, int id) {
		List<Long> song = ic.getAndResetOutput();
		int state = ic.getState();
		String ids = (id < 10 ? "0" : "") + id;
		System.out.println("IC[" + ids + "] -> " + state);
		for (long l : song) {
			System.out.println((char) l);
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
