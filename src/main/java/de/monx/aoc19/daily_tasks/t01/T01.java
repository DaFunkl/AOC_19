package de.monx.aoc19.daily_tasks.t01;

import java.util.Iterator;

import de.monx.aoc19.helper.TDay;

public class T01 extends TDay {

	public T01(String path, String day) {
		super(path, day);
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		int sumP1 = 0;
		int sumP2 = 0;
		while (it.hasNext()) {
			String line = it.next();
			int value = Integer.valueOf(line);
			sumP1 += doCalc(value);
			int fuelWeight = value;
			while(fuelWeight > 8) {
				fuelWeight = doCalc(fuelWeight);
				sumP2 += fuelWeight;
			}
		}
		System.out.println("Part 1: " + sumP1);
		System.out.println("Part 2: " + sumP2);
		return this;
	}

	static int doCalc(int in) {
		return in / 3 - 2;
	}
}
