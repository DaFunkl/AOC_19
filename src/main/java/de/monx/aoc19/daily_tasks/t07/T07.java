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
		part1(line);
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
//			System.out.println("Amp[" + i + "] -> " + Arrays.toString(new int[] { seq[i], out }));
			amp.init(new int[] { seq[i], out }, stack.clone());
			out = amp.executeStack().get(0);
		}
		return out;
	}
}
