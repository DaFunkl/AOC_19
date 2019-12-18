package de.monx.aoc19.daily_tasks.t18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.Vec3;

public class T18 extends TDay {
	// z is 1 to add a step automatically
	final Vec3[] _DIRS_ADDER = { //
			new Vec3(-1, 0, 1), // up
			new Vec3(1, 0, 1), // down
			new Vec3(0, -1, 1), // left
			new Vec3(0, 1, 1) // right
	};
	final Vec2[] _DIRS_ADDER2 = { //
			new Vec2(1, 1), // down right
			new Vec2(-1, 1), // up right
			new Vec2(-1, -1), // up left
			new Vec2(1, -1) // down left
	};

	@Override
	public TDay exec() {
		List<char[]> input = getInput();
		System.out.println("Part1: " + part1(input));
		System.out.println("Part2: " + part2(input));
		return this;
	}

	int part2(List<char[]> in) {
		Map<Character, Vec2> objectMap = parseMap(in);
		Vec2 position = objectMap.get('@');
		int minSteps = Integer.MAX_VALUE;
		adaptInput(in, position);
		List<Character> todoKeys = getKeys(objectMap.keySet());
		int keysAmt = todoKeys.size();

		List<Attempt> todo = new ArrayList<>();
		Map<String, Integer> dump = new HashMap<>();
		todo.add(Attempt.initP2(position.toVec3(0)));
		while (!todo.isEmpty()) {
			// dfs
			Attempt apt = todo.get(todo.size() - 1);
			todo.remove(todo.size() - 1);

			// look if an attempt finished,
			for (int r = 0; r < 4; r++) {
				Map<Character, Vec3> reachable = nextKeys(apt, in, r);
				for (char c : reachable.keySet()) {
//					Attempt nextApt = new Attempt(reachable.get(c), new HashSet<>(apt.getKeysCollected()));
					Attempt nextApt = apt.clone();
					nextApt.collectKeyP2(c, reachable.get(c), r);
					String naKey = nextApt.keysStr();
					if (nextApt.getStepCount() >= minSteps) {
						continue;
					}
					if (dump.containsKey(naKey) && dump.get(naKey) <= nextApt.getStepCount()) {
						continue;
					} else {
						dump.put(naKey, nextApt.getStepCount());
					}
					if (nextApt.getKeysCollected().size() == keysAmt) {
						int steps = nextApt.getSteps();
						if (steps < minSteps) {
							minSteps = steps;
						}
						continue;
					}
					if (todo.isEmpty()) {
						todo.add(nextApt);
						continue;
					}
					// check whether this attempt is redundant or makes another redundant
					for (int i = 0; i < todo.size(); i++) {
						int compare = nextApt.compare(todo.get(i));
						// compare nextAttempt with todo attempts
						// compare == -1 -> can't be compared
						// compare == 0 -> both are equal
						// compare == 1 -> nextApt perfomrs better
						// compare == 2 -> other performs better
						if (compare == -1) {
							todo.add(nextApt);
							break;
						} else if (compare == 1) { // since this todo performs better then
							todo.remove(i--); // other, remove the other
							todo.add(nextApt);
							break;
						} // if compare == 2 other performs better, so don't add this
					}
				}

			}
		}
		return minSteps;
	}

	void adaptInput(List<char[]> in, Vec2 position) {
		in.get(position.y)[position.x] = '#';
		for (Vec3 v : _DIRS_ADDER) {
			in.get(v.y + position.y)[v.x + position.x] = '#';
		}
		for (Vec2 v : _DIRS_ADDER2) {
			in.get(v.y + position.y)[v.x + position.x] = '@';
		}
	}

	int part1(List<char[]> in) {
		Map<Character, Vec2> objectMap = parseMap(in);
		Vec2 position = objectMap.get('@');
		List<Character> todoKeys = getKeys(objectMap.keySet());
		int keysAmt = todoKeys.size();

		List<Attempt> todo = new ArrayList<>();
		Map<String, Integer> dump = new HashMap<>();
		todo.add(new Attempt(position.toVec3(0)));
		int minSteps = Integer.MAX_VALUE;
		while (!todo.isEmpty()) {
			// dfs
			Attempt apt = todo.get(todo.size() - 1);
			todo.remove(todo.size() - 1);

			// bfs
//			Attempt apt = todo.get(0);
//			todo.remove(0);

			// look if an attempt finished,
			Map<Character, Vec3> reachable = nextKeys(apt, in, -1);
			for (char c : reachable.keySet()) {
				Attempt nextApt = new Attempt(reachable.get(c), new HashSet<>(apt.getKeysCollected()));
				nextApt.addKey(c);
				String naKey = nextApt.keysStr();
				if (nextApt.getStepCount() >= minSteps) {
					continue;
				}
				if (dump.containsKey(naKey) && dump.get(naKey) <= nextApt.getStepCount()) {
					continue;
				} else {
					dump.put(naKey, nextApt.getStepCount());
				}
				if (nextApt.getKeysCollected().size() == keysAmt) {
					int steps = nextApt.getStepCount();
					if (steps < minSteps) {
						minSteps = steps;
					}
					continue;
				}
				if (todo.isEmpty()) {
					todo.add(nextApt);
					continue;
				}
				// check whether this attempt is redundant or makes another redundant
				for (int i = 0; i < todo.size(); i++) {
					int compare = nextApt.compare(todo.get(i));
					// compare nextAttempt with todo attempts
					// compare == -1 -> can't be compared
					// compare == 0 -> both are equal
					// compare == 1 -> nextApt perfomrs better
					// compare == 2 -> other performs better
					if (compare == -1) {
						todo.add(nextApt);
						break;
					} else if (compare == 1) { // since this todo performs better then
						todo.remove(i--); // other, remove the other
						todo.add(nextApt);
						break;
					} // if compare == 2 other performs better, so don't add this
				}
			}
		}
		return minSteps;
	}

	List<Character> getKeys(Set<Character> set) {
		List<Character> keys = new ArrayList<>();
		for (char c : set) {
			if (isKey(c)) {
				keys.add(c);
			}
		}
		return keys;
	}

	List<Character> getGates(Set<Character> set) {
		List<Character> keys = new ArrayList<>();
		for (char c : set) {
			if (isGate(c)) {
				keys.add(c);
			}
		}
		return keys;
	}

	void printGrid(List<char[]> grid) {
//		for (char[] car : grid) {
//			for (char c : car) {
//				System.out.print(c + ",");
//			}
//			System.out.println();
//		}
		for (char[] arr : grid) {
			System.out.println(new String(arr));
		}
	}

	Map<Character, Vec3> nextKeys(Attempt attempt, List<char[]> map, int roboIdx) {
		Map<Character, Vec3> reachable = new HashMap<>();
		List<Vec3> allKeys = new ArrayList<>();
		Vec3 position = new Vec3();
		if (roboIdx == -1) {
			position = attempt.getPosition();
		} else {
			position = attempt.getPositions()[roboIdx].toVec3(attempt.getSteps());
		}
		allKeys.add(position);
		Set<Vec2> done = new HashSet<>();
		while (!allKeys.isEmpty()) {
			Vec3 pos = allKeys.get(0);
			allKeys.remove(0);
			done.add(pos.toVec2());
			char c = map.get(pos.y)[pos.x];
			if (isGate(c) && !attempt.isGateOpen(c)) {
				continue;
			} else if (isKey(c) && !attempt.hasKey(c)) {
				reachable.put(c, pos);
				continue;
			}
			for (Vec3 vDir : _DIRS_ADDER) {
				Vec3 v = pos.add(vDir);
				c = map.get(v.y)[v.x];
				if (c != '#' && !done.contains(v.toVec2())) {
					allKeys.add(v);
				}
			}
		}
		return reachable;
	}

	boolean isGate(char c) {
		return (int) 'A' <= (int) c && (int) 'Z' >= (int) c;
	}

	boolean isKey(char c) {
		return (int) 'a' <= (int) c && (int) 'z' >= (int) c;
	}

	boolean isKeyGatePair(char k, char g) {
		return k == getKeyOfGate(g);
	}

	public static char getKeyOfGate(char g) {
		return (char) ((int) 'a' + (int) g - (int) 'A');
	}

	public static char getGateOfKey(char k) {
		return (char) ((int) 'A' + (int) k - (int) 'a');
	}

	Map<Character, Vec2> parseMap(List<char[]> in) {
		Map<Character, Vec2> map = new HashMap<>();
		for (int y = 0; y < in.size(); y++) {
			char[] car = in.get(y);
			for (int x = 0; x < car.length; x++) {
				if (car[x] != '.' && car[x] != '#') {
					map.put(car[x], new Vec2(x, y));
				}
			}
		}
		return map;
	}

	List<char[]> getInput() {
		Iterator<String> it = stream.iterator();
		List<char[]> ret = new ArrayList<>();
		while (it.hasNext()) {
			ret.add(it.next().toCharArray());
		}
		return ret;
	}
}
