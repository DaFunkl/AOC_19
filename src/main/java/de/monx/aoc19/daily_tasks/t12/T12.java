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
	int steps = 1000;

	public T12 setP1Steps(int steps) {
		this.steps = steps;
		return this;
	}

	@Override
	public TDay exec() {
		List<Planet> planets = getInput();
		boolean p2Found = false;
		boolean p1Reached = false;
		int step = 1;

		Vec3 dimRep = new Vec3(-1, -1, -1);
		Set<List<Vec2>> xLL = new HashSet<>();
		xLL.add(getDimList(planets, 'x'));
		Set<List<Vec2>> yLL = new HashSet<>();
		yLL.add(getDimList(planets, 'y'));
		Set<List<Vec2>> zLL = new HashSet<>();
		zLL.add(getDimList(planets, 'z'));

		while (!(p2Found && p1Reached)) {
			// planet Simulation ===========================================
			List<Planet> planetsTemp = new ArrayList<>();
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

			// part 2 ======================================================
			dimRep.x = didDimSeqReappear(planets, step, xLL, dimRep.x, 'x');
			dimRep.y = didDimSeqReappear(planets, step, yLL, dimRep.y, 'y');
			dimRep.z = didDimSeqReappear(planets, step, zLL, dimRep.z, 'z');
			if (dimRep.x != -1 && dimRep.y != -1 && dimRep.z != -1) {
				System.out.println("Part2: " + lcm(lcm(dimRep.x, dimRep.y), dimRep.z));
				p2Found = true;
			}
			// part 1 ======================================================
			if (step++ == steps) {
				System.out.println("Part1: " + sumEnergy(planetsTemp));
				p1Reached = true;
			}
		}

		return this;

	}

	private int didDimSeqReappear(List<Planet> planets, int step, Set<List<Vec2>> xLL, int xFound, char dim) {
		if (xFound == -1) {
			List<Vec2> l = getDimList(planets, dim);
			if (xLL.contains(l)) {
				xFound = step;
			} else {
				xLL.add(l);
			}
		}
		return xFound;
	}

	public static long lcm(long a, long b) {
		return (a * b) / gcf(a, b);
	}

	public static long gcf(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return (gcf(b, a % b));
		}
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

	List<Planet> getInput() {
		Iterator<String> it = stream.iterator();
		List<Planet> ret = new ArrayList<>();
		while (it.hasNext()) {
			ret.add(new Planet(Vec3.parseString(it.next())));
		}
		return ret;
	}
}
