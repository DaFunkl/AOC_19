package de.monx.aoc19.daily_tasks.t11;

import java.util.Iterator;

import de.monx.aoc19.helper.TDay;

public class T11 extends TDay {

	@Override
	public TDay exec() {
		int[] input = getInput();

		int p1 = part1(input);
		System.out.println("Part1: " + p1);
		
		return this;
	}

	int part1(int[] in) {
		
		return 0;
	}

	int[] getInput() {
		Iterator<String> it = stream.iterator();
		String[] arr = it.next().split(",");
		int[] ret = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = Integer.valueOf(arr[i]);
		}
		return ret;
	}
}
