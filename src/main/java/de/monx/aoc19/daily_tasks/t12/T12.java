package de.monx.aoc19.daily_tasks.t12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.Vec3;

public class T12 extends TDay {
	int p1Steps = 1000;

	public T12 setP1Steps(int steps) {
		p1Steps = steps;
		return this;
	}

	@Override
	public TDay exec() {
		List<Planet> planets = getInput();
		part1(planets, p1Steps);
		return this;
	}

	void part1(List<Planet> planets, int steps) {
		boolean p2Found = false;
		boolean p1Reached = false;
		int step = 1;

		Set<List<Vec2>> xLL = new HashSet<>();
		xLL.add(getDimList(planets, 'x'));
		int xFound = -1;
		Set<List<Vec2>> yLL = new HashSet<>();
		yLL.add(getDimList(planets, 'y'));
		int yFound = -1;
		Set<List<Vec2>> zLL = new HashSet<>();
		zLL.add(getDimList(planets, 'z'));
		int zFound = -1;

		while (!(p2Found && p1Reached)) {
			List<Planet> planetsTemp = new ArrayList<>();
//			if(step % 10 == 0) {
//				System.out.println("Step: " + step);
//				printPlanetsWithVelo(planets);
//			}
			for (int i = 0; i < planets.size(); i++) {
				if (i == 0) {
					planetsTemp.add(planets.get(0).clone());
				}
				for (int j = i + 1; j < planets.size(); j++) {
					if (i == 0) {
						planetsTemp.add(planets.get(j).clone());
					}
					planetsTemp.get(i).adjustVel(planetsTemp.get(j));
					planetsTemp.get(j).adjustVel(planetsTemp.get(i));
				}
				planetsTemp.get(i).applyVel();
			}
			planets = planetsTemp;

			int energy = sumEnergy(planetsTemp);

			if (xFound == -1) {
				List<Vec2> l = getDimList(planets, 'x');
				if (xLL.contains(l)) {
					xFound = step;
				} else {
					xLL.add(l);
				}
			}
			if (yFound == -1) {
				List<Vec2> l = getDimList(planets, 'y');
				if (yLL.contains(l)) {
					yFound = step;
				} else {
					yLL.add(l);
				}
			}
			if (zFound == -1) {
				List<Vec2> l = getDimList(planets, 'z');
				if (zLL.contains(l)) {
					zFound = step;
				} else {
					zLL.add(l);
				}
			}

			if (step++ == steps) {
				System.out.println("Part1: " + energy);
				p1Reached = true;
			}

			if (xFound != -1 && yFound != -1 && zFound != -1) {
				p2Found = true;
			}
		}
		System.out.println("Part2: " + lcm(lcm(xFound, yFound), zFound) + " ->xFound: " + xFound + ", yFound: " + yFound
				+ ", zFound: " + zFound);
	}

	/**
	 * Calculate Lowest Common Multiplier
	 */
	public static long lcm(long a, long b) {
		return (a * b) / gcf(a, b);
	}

	/**
	 * Calculate Greatest Common Factor
	 */
	public static long gcf(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return (gcf(b, a % b));
		}
	}

	int containsList(List<List<Vec2>> ll, List<Vec2> l) {
		for (int i = 0; i < ll.size(); i++) {
			if (isEQ(ll.get(i), l)) {
				return i;
			}
		}
		return -1;
	}

	boolean isEQ(List<Vec2> l1, List<Vec2> l2) {
		for (int i = 0; i < l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))) {
				return false;
			}
		}
		return true;
	}

	List<Vec2> getDimList(List<Planet> planets, char dim) {
		List<Vec2> dimList = new ArrayList<>();
		for (int i = 0; i < planets.size(); i++) {
			Planet p = planets.get(i);
			switch (dim) {
			case 'x':
				dimList.add(new Vec2(p.pos.x, p.vel.x));
				break;
			case 'y':
				dimList.add(new Vec2(p.pos.y, p.vel.y));
				break;
			case 'z':
				dimList.add(new Vec2(p.pos.z, p.vel.z));
				break;
			default:
				break;
			}
		}
		return dimList;
	}

	int sumEnergy(List<Planet> planets) {
		int sum = 0;
		for (Planet planet : planets) {
			sum += planet.energy();
		}
		return sum;
	}

	int gravityImpact(int a, int b) {
		if (a < b) {
			return 1;
		} else if (a > b) {
			return -1;
		} else {
			return 0;
		}
	}

	void printPlanetsWithVelo(List<Planet> planets) {
		for (int i = 0; i < planets.size(); i++) {
			System.out.println(planets.get(i));
		}
	}

	List<Planet> getInput() {
		Iterator<String> it = stream.iterator();
		List<Planet> ret = new ArrayList<>();
		while (it.hasNext()) {
			ret.add(new Planet(Vec3.parseString(it.next())));
		}
		return ret;
	}
}
