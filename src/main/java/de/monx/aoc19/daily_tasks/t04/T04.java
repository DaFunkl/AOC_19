package de.monx.aoc19.daily_tasks.t04;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import de.monx.aoc19.helper.TDay;

public class T04 extends TDay {

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		while (it.hasNext()) {
			String line = it.next();
			System.out.println(line);
			String[] sar = line.split("-");
			int rangeMin = Integer.valueOf(sar[0]);
			int rangeMax = Integer.valueOf(sar[1]);
			System.out.println("Range: " + rangeMin + " - " + rangeMax);
			part1(rangeMin, rangeMax);
		}
		return this;
	}

	void part1(int min, int max) {
		int count = 0;
		int count2 = 0;
		for (int i = min; i <= max; i = getNextAdj(i)) {
			int adjCount = meetsP1Criteria(i);
			if (adjCount > 0) {
				count++;
			}
			if (adjCount == 2) {
				count2++;
			}
		}
		System.out.println("Part1: " + count);
		System.out.println("Part2: " + count2);
	}

	int getNextAdj(int number) {
		int[] arr = numberToDigitArray(number);
		int idx = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i + 1] < arr[i]) {
				idx = i;
				break;
			}
		}
		StringBuilder nrStr = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i < idx) {
				nrStr.append(arr[i]);
			} else {
				nrStr.append(arr[idx]);
			}
		}
		int ret = Integer.valueOf(nrStr.toString());
		if (ret <= number) {
			ret = number + 1;
		}
		return ret;
	}

	int meetsP1Criteria(int number) {
		String nrStr = number + "";
		if (nrStr.length() != 6) {
			return -1;
		}
		int[] arr = numberToDigitArray(number);
		int n = arr[0];
		Map<Integer, Integer> adj = new HashMap<>();
		for (int i = 1; i < arr.length; i++) {
			if (n > arr[i]) {
				return -1;
			}
			if (n == arr[i]) {
				if (adj.containsKey(n)) {
					adj.put(n, adj.get(n) + 1);
				} else {
					adj.put(n, 2);
				}
			}
			n = arr[i];
		}
		if (adj.size() == 0) {
			return -1;
		}
		for (int i : adj.keySet()) {
			if (adj.get(i) == 2) {
				return 2;
			}
		}
		return 1;
	}

	int[] numberToDigitArray(int number) {
		char[] numberAsCharArray = (number + "").toCharArray();
		int[] digitArray = new int[numberAsCharArray.length];
		for (int i = 0; i < numberAsCharArray.length; i++) {
			digitArray[i] = numberAsCharArray[i] - '0';
		}
		return digitArray;
	}
}
