package de.monx.aoc19.daily_tasks.t20;

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
import de.monx.aoc19.helper.Vec3Step;
import de.monx.aoc19.helper.animation.Animation;
import de.monx.aoc19.helper.animation.DrawPane20;

public class T20 extends TDay {
	final static Vec2[] _DIRS = { //
			new Vec2(0, -1), // up
			new Vec2(0, 1), // down
			new Vec2(-1, 0), // left
			new Vec2(1, 0) // right
	};
	final Vec3[] _DIRS_ADDER = { //
			new Vec3(-1, 0, 1), // up
			new Vec3(1, 0, 1), // down
			new Vec3(0, -1, 1), // left
			new Vec3(0, 1, 1) // right
	};
	final static Vec2 _AA = new Vec2(-1, 0);
	final static Vec2 _ZZ = new Vec2(0, -1);

	Animation anim = null;

	void draw(List<char[]> input, Set<Vec2> visited, Vec2 pos) {
		if (allowDraw) {
			((DrawPane20) anim.pane).draw(input, visited, pos);
		}
	}

	boolean allowDraw = false;

	@Override
	public TDay exec() {
		if (allowDraw) {
			anim = new Animation(600, 650, 20);
		}
		List<char[]> input = getInput();
		closeDeadEnds(input);
		Map<Vec2, Vec2> portals = fetchPortals(input);

		if (allowDraw) {
			draw(input, new HashSet<>(), _AA);
			BF.haltInput();
		}
		System.out.println("Part1: " + part1(input, portals));
		System.out.println("Part2: " + part2(input, portals));
		return this;
	}

	int part1(List<char[]> input, Map<Vec2, Vec2> portals) {
		Map<Vec2, Integer> done = new HashMap<>();
		List<Vec3> todos = new ArrayList<>();
		todos.add(portals.get(_AA).toVec3(0));
		int steps = Integer.MAX_VALUE;
		while (!todos.isEmpty()) {
			Vec3 pos = todos.get(0);
			todos.remove(0);
			done.put(pos.toVec2(), pos.z);
			draw(input, done.keySet(), pos.toVec2());
//			BF.haltInput();
			for (Vec3 vAdd : _DIRS_ADDER) {
				Vec3 nextPos = pos.add(vAdd);
				char c = input.get(nextPos.y)[nextPos.x];
				if (!(c == '.' || isCapitalLetter(c))) {
					continue;
				}
				if (portals.get(_ZZ).equals(nextPos.toVec2())) {
					draw(input, done.keySet(), nextPos.toVec2());
					return nextPos.z;
				}
				if (isCapitalLetter(c)) {
					if (c == 'A' && portals.get(_AA).equals(pos.toVec2())) {
						continue;
					}

					Vec2 port = portals.get(pos.toVec2());
					nextPos.x = port.x;
					nextPos.y = port.y;
					c = input.get(nextPos.y)[nextPos.x];
				}
				Vec2 nPv2 = nextPos.toVec2();
				if (done.containsKey(nPv2)) {
					if (done.get(nPv2) > nextPos.z) {
						done.put(nPv2, nextPos.z);
					} else {
						continue;
					}
				}
				todos.add(nextPos);
			}

		}
		return steps;
	}

	int part2(List<char[]> input, Map<Vec2, Vec2> portals) {
		Map<Vec3, Integer> done = new HashMap<>();
		List<Vec3Step> todos = new ArrayList<>();
		todos.add(new Vec3Step(portals.get(_AA).toVec3(0), 0));
		int steps = Integer.MAX_VALUE;
		while (!todos.isEmpty()) {
			Vec3Step v3s = todos.get(0);
			todos.remove(0);
			done.put(v3s.pos, v3s.step);
			for (Vec2 vAdd : _DIRS) {
				Vec3Step nextV3S = v3s.clone();
				nextV3S.pos.addI(vAdd.toVec3(0));
				nextV3S.step++;
				char c = input.get(nextV3S.pos.y)[nextV3S.pos.x];
				if (!(c == '.' || isCapitalLetter(c))) {
					continue;
				}
				if (portals.get(_ZZ).equals(nextV3S.pos.toVec2())) {
					if(nextV3S.pos.z == 0) {
						return nextV3S.step;
					} else {
						continue;
					}
				}
				if (isCapitalLetter(c)) {
					if (c == 'A' && portals.get(_AA).equals(v3s.pos.toVec2())) {
						continue;
					}
					int levelAdj = isOuterPortal(v3s.pos, input) ? -1 : 1;
					if (nextV3S.pos.z <= 0 && levelAdj == -1) {
						continue;
					}
					Vec2 port = portals.get(v3s.pos.toVec2());

					nextV3S.pos.x = port.x;
					nextV3S.pos.y = port.y;
					nextV3S.pos.z += levelAdj;
					c = input.get(nextV3S.pos.y)[nextV3S.pos.x];
				}
				Vec3 nPv3 = nextV3S.pos;
				if (done.containsKey(nPv3)) {
					if (done.get(nPv3) > nextV3S.step) {
						done.put(nPv3, nextV3S.step);
					} else {
						continue;
					}
				}
				todos.add(nextV3S);
			}

		}
		return steps;
	}

	boolean isOuterPortal(Vec3 pos, List<char[]> in) {
		return pos.x == 2 || pos.y == 2 || pos.y == in.size() - 3 || pos.x == in.get(0).length - 3;
	}

	Map<Vec2, Vec2> fetchPortals(List<char[]> input) {
		Map<Vec2, Vec2> portals = new HashMap<>();
		Map<String, Vec2> tags = new HashMap<>();
		for (int y = 0; y < input.size() - 1; y++) {
			char[] car = input.get(y);
			for (int x = 0; x < car.length - 1; x++) {
				char c = car[x];
				if (!isCapitalLetter(c)) {
					continue;
				}
				String tag = null;
				Vec2 pos = new Vec2(x, y);
				if (pos.y + 1 < input.size() && isCapitalLetter(input.get(pos.y + 1)[pos.x])) {// if is down
					tag = "" + c + input.get(pos.y + 1)[pos.x];
					if (pos.y + 2 < input.size() && input.get(pos.y + 2)[pos.x] == '.') {
						pos = new Vec2(pos.x, pos.y + 2);
					} else {
						pos = new Vec2(pos.x, pos.y - 1);
					}
				} else if (pos.x + 1 < car.length && isCapitalLetter(input.get(pos.y)[pos.x + 1])) {// if is right
					tag = "" + c + input.get(pos.y)[pos.x + 1];
					if (pos.x + 2 < car.length && input.get(pos.y)[pos.x + 2] == '.') {
						pos = new Vec2(pos.x + 2, pos.y);
					} else {
						pos = new Vec2(pos.x - 1, pos.y);
					}
				}
				if (tag == null) {
					continue;
				}
				if (tag.equals("AA") && !portals.containsKey(_AA)) {
					portals.put(_AA, pos);
				} else if (tag.equals("ZZ") && !portals.containsKey(_ZZ)) {
					portals.put(_ZZ, pos);
				} else if (tags.containsKey(tag)) {
					portals.put(pos, tags.get(tag));
					portals.put(tags.get(tag), pos);
					tags.remove(tag);
				} else {
					tags.put(tag, pos);
				}
			}
		}
		return portals;
	}

	public static boolean isCapitalLetter(char c) {
		final int _A = (int) 'A';
		final int _Z = (int) 'Z';
		final int _c = (int) c;
		return _A <= _c && c <= _Z;
	}

	void closeDeadEnds(List<char[]> input) {
		boolean allClosed = false;
		while (!allClosed) {
			allClosed = true;
			for (int y = 1; y < input.size() - 1; y++) {
				for (int x = 1; x < input.get(y).length - 1; x++) {
					Vec2 cp = new Vec2(x, y);
					if (input.get(cp.y)[cp.x] != '.') {
						continue;
					}
					int wallCount = 0;
					for (Vec2 d : _DIRS) {
						Vec2 cv = cp.add(d);
						if (input.get(cv.y)[cv.x] == '#') {
							wallCount++;
						}
					}
					if (wallCount == 3) {
						input.get(cp.y)[cp.x] = '#';
						allClosed = false;
					}
				}
			}
		}
	}

	List<char[]> getInput() {
		Iterator<String> it = stream.iterator();
		List<char[]> ret = new ArrayList<>();
		while (it.hasNext()) {
			ret.add(it.next().toCharArray());
		}
		return ret;
	}

	void printGrid(List<char[]> grid) {
		for (char[] arr : grid) {
			System.out.println(new String(arr));
		}
	}
}
