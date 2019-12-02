package de.monx.aoc19.t02;

import java.util.Iterator;

import de.monx.aoc19.TDay;

public class T02 extends TDay {
	final static int _END = 99;
	final static int _ADD = 1;
	final static int _MUL = 2;
	final static int _OP = 0;
	final static int _R1 = 1;
	final static int _R2 = 2;
	final static int _DE = 3;

	public T02(String path, String day) {
		super(path, day);
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
		String[] arr = line.split(",");
		int[] vals_P1 = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			vals_P1[i] = Integer.valueOf(arr[i]);
		}
		int[] vals_Copy = vals_P1.clone();
		// part 1
		vals_P1[1] = 12;
		vals_P1[2] = 2;
		System.out.println("Part 1: " + executeOpCode(vals_P1));

		// Part 2
		int p2Goal = 19690720;
		System.out.println("P2 Goal val[0] = 19690720 is reached by setting val[1] = 65 val[2]=33 -> 6533");
		int res = 0;
		int noun = 0;
		int verb = 0;
		int[] vals_P2 = vals_Copy.clone();
		while (res < p2Goal) {
			vals_P2[2] = verb;
			while (res < p2Goal) {
				vals_P2 = vals_Copy.clone();
				vals_P2[1] = noun;
				res = executeOpCode(vals_P2);
				noun++;
			}
			System.out.println(res);
			noun -= 2;
			res = 0;
			while (res < p2Goal) {
				vals_P2 = vals_Copy.clone();
				vals_P2[2] = verb++;
				vals_P2[1] = noun;
				res = executeOpCode(vals_P2);
			}
			verb--;
		}
		System.out.println("P2: " + noun + "" + verb + " -> " + res);
		return this;
	}

	private int executeOpCode(int[] vals_P1) {
		for (int i = 0; i < vals_P1.length && vals_P1[i] != _END; i += 4) {
			int[] args = new int[4];
			for (int j = 0; j < args.length; j++) {
				args[j] = vals_P1[i + j];
			}
			switch (args[_OP]) {
			case _END:
				System.out.println("End reached: vals[" + i + "]: " + args[0]);
				break;
			case _ADD:
				vals_P1[args[_DE]] = vals_P1[args[_R1]] + vals_P1[args[_R2]];
				break;
			case _MUL:
				vals_P1[args[_DE]] = vals_P1[args[_R1]] * vals_P1[args[_R2]];
				break;
			default:
				System.err.println("Something went wrong: vals[" + i + "]: " + args[i]);
			}
		}
		return vals_P1[0];
	}

}
