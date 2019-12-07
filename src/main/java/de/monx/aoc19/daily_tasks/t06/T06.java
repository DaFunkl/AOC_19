package de.monx.aoc19.daily_tasks.t06;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.monx.aoc19.helper.TDay;

public class T06 extends TDay {

	@Override
	public TDay exec() {
		// Part 1:
		Map<String, String> orbitalConnections = parseConnections();
		// for Debugging:
//		printOrbitalConnections(orbitalConnections);
		Map<String, Integer> weights = calculateWeight(orbitalConnections);
		System.out.println("Part1: " + sumOfWeights(weights));
		// Part 2:
		System.out.println("Part2: " + calculateDistance("YOU", "SAN", orbitalConnections));

		return this;
	}

	int calculateDistance(String aStart, String bStart, Map<String, String> orbitalConnections) {
		Map<String, Integer> aTravelled = new HashMap<>();
		Map<String, Integer> bTravelled = new HashMap<>();
		boolean found = false;
		int aDis = -1;
		int bDis = -1;
		String aNextKey = aStart;
		String bNextKey = bStart;
		String intersection = null;
		while (!found) {
			if (orbitalConnections.containsKey(orbitalConnections.get(aNextKey))) {
				aTravelled.put(aNextKey, aDis++);
				if (bTravelled.containsKey(aNextKey)) {
					intersection = aNextKey;
					break;
				}
				aNextKey = orbitalConnections.get(aNextKey);

			}
			if (orbitalConnections.containsKey(orbitalConnections.get(bNextKey))) {
				bTravelled.put(bNextKey, bDis++);
				if (aTravelled.containsKey(bNextKey)) {
					intersection = bNextKey;
					break;
				}
				bNextKey = orbitalConnections.get(bNextKey);
			}
		}
		return aTravelled.get(intersection) + bTravelled.get(intersection);
	}

	int sumOfWeights(Map<String, Integer> weights) {
		int sum = 0;
		for (String key : weights.keySet()) {
			sum += weights.get(key);
		}
		return sum;
	}

	Map<String, Integer> calculateWeight(Map<String, String> orbitalConnections) {
		Map<String, Integer> weights = new HashMap<>();
		for (String key : orbitalConnections.keySet()) {
			weights.put(key, calculateWeight(key, orbitalConnections, weights));
		}
		return weights;
	}

	int calculateWeight(String key, Map<String, String> orbitalConnections, Map<String, Integer> weights) {
		int weight = 0;
		String connection = key;
		while (orbitalConnections.containsKey(connection)) {
			connection = orbitalConnections.get(connection);
			weight++;
			if (weights.containsKey(connection)) {
				return weight + weights.get(connection);
			}
		}

		return weight;
	}

	Map<String, String> parseConnections() {
		Map<String, String> orbitalConnections = new HashMap<>();
		Iterator<String> it = stream.iterator();
		while (it.hasNext()) {
			String line = it.next();
			String[] sarr = line.split("\\)");
			if (!orbitalConnections.containsKey(sarr[1])) {
				orbitalConnections.put(sarr[1], sarr[0]);
			} else {
				System.err.println("This Object (" + sarr[1] + ") has multiple roots: "
						+ orbitalConnections.get(sarr[1]) + ", " + sarr[0]);
			}
		}
		return orbitalConnections;
	}

	void printOrbitalConnections(Map<String, String> orbitalConnections) {
		for (String key : orbitalConnections.keySet()) {
			System.out.println(key + " -> " + orbitalConnections.get(key));
		}
	}
}
