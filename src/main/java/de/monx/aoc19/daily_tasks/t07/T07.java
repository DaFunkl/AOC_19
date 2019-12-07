package de.monx.aoc19.daily_tasks.t07;

import java.util.Arrays;
import java.util.Iterator;

import de.monx.aoc19.helper.Permutations;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T07 extends TDay {

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
//		part1(line);
		part2(line);
		return this;
	}

	void part1(String line) {
		int[] stack = IntCode.parseLineToStack(line);
		int[] seq = { 0, 1, 2, 3, 4 };
		Permutations permutations = new Permutations(seq);
		seq = permutations.getFirst();
		int max = executeAmtSeq(stack, permutations.getNext());
		int[] optSeq = seq.clone();
		while (permutations.hasNext()) {
			seq = permutations.getNext();
			int out = executeAmtSeq(stack, permutations.getNext());
			if (out > max) {
				max = out;
				optSeq = seq.clone();
			}
		}
		System.out.println("Part1: OPtSeq:" + Arrays.toString(optSeq) + " -> " + max);
	}

	int executeAmtSeq(int[] stack, int[] seq) {
		IntCode amp = new IntCode();
		int out = 0;
		for (int i = 0; i < seq.length; i++) {
			amp.init(new int[] { seq[i], out }, stack.clone());
			out = amp.executeStack().get(0);
		}
		return out;
	}

	void part2(String line) {
		int[] stack = IntCode.parseLineToStack(line);
		// only this ascending order is getting the right result
		// it might be, that permutation class is bugged
		int[] seq = { 5, 6, 7, 8, 9 };
//		int[] seq = { 9, 8, 7, 6, 5 };
		Permutations permutations = new Permutations(seq);
		seq = permutations.getFirst();
		int max = executeAmtSeqIO(stack, permutations.getNext());
		int[] optSeq = seq.clone();
		while (permutations.hasNext()) {
			seq = permutations.getNext();
			int out = executeAmtSeqIO(stack, permutations.getNext());
			if (out > max) {
				max = out;
				optSeq = seq.clone();
			}
		}
		// OptSeq is bugged, swap first two values of array for "right" result
		// only tested with 2 tests inputs p2ex1 and p2ex2 from aoc day 7 part 2 examples
		System.out.println("Part2: OPtSeq:" + Arrays.toString(optSeq) + " -> " + max);
	}

	int executeAmtSeqIO(int[] stack, int[] seq) {
		IntCode[] amp = new IntCode[seq.length];
		int[] ampState = new int[seq.length];
		int out = 0;
		for (int i = 0; i < amp.length; i++) {
			amp[i] = new IntCode();
			amp[i].setStack(stack.clone());
			ampState[i] = amp[i].execIO();
			amp[i].setInput(seq[i]);
			ampState[i] = amp[i].execIO();
			amp[i].setInput(out);
			ampState[i] = amp[i].execIO();
			out = amp[i].getOutput();
			if (ampState[i] == IntCode._STATE_HALT) {
				return out;
			}
		}
		boolean didHalt = false;
		for (int i = 0; i < amp.length; i = (i + 1) % amp.length) {
			amp[i].setInput(out);
			ampState[i] = amp[i].execIO();
			out = amp[i].outputReg;
			if (ampState[i] == IntCode._STATE_HALT) {
				didHalt = true;
			}
			if (didHalt && i == amp.length - 1) {
				return out;
			}
		}
		return 0;
	}
}
