package de.monx.aoc19.daily_tasks.t03;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;

public class T03 extends TDay {

	public T03(String path, String day) {
		super(path, day);
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		List<Wire> wires = new ArrayList<>();
		int i = 0;
		while (it.hasNext()) {
			System.out.println("Wire: " + i++);
			String line = it.next();
			wires.add(new Wire(line));
		}
		System.out.println("Part1: " + part1(wires));
		return this;
	}

	Boundry getBounds(List<Wire> wires) {
		Boundry ret = new Boundry();
		for (Wire wire : wires) {
			ret.refresh(wire.boundry);
		}
		return ret;
	}

	int part1(List<Wire> wires) {
		int minDis = Integer.MAX_VALUE;
		for (int i = 0; i < wires.size() - 1; i++) {
			Wire aWire = wires.get(i);
			for (int j = i + 1; j < wires.size(); j++) {
				Wire bWire = wires.get(j);
				Integer intersection = getShortesIntesection(aWire.getCoords(), bWire.getCoords());
				if (intersection != null && intersection < minDis) {
					minDis = intersection;
				}
			}
		}
		return minDis;
	}

	Integer getShortesIntesection(List<Vec2> aLV2, List<Vec2> bLV2) {
		Vec2 a1 = aLV2.get(0);
		Vec2 a2 = null;
		Vec2 b2 = null;
		int aDis = 0;
		Integer minDis = null;
		int minInterDis = Integer.MAX_VALUE;
		for (int i = 1; i < aLV2.size(); i++) {
			a2 = aLV2.get(i);
			Vec2 b1 = bLV2.get(0);
			int bDis = 0;
			Vec2 inter = null;
			for (int j = 1; j < bLV2.size(); j++) {
				if(i == 1 && j == 1) {
					continue;
				}
				b2 = bLV2.get(j);
				inter = isIntersecting(a1, a2, b1, b2);
				if (inter != null) {
					int iDis = inter.manhattenDistance();
					int sum = aDis + bDis;
					sum += a1.manhattenDistance(inter);
					sum += b1.manhattenDistance(inter);
					if (sum < minInterDis) {
						minInterDis = sum;
					}
					if (minDis == null || iDis < minDis) {
						minDis = iDis;
					}
				}
				bDis += b1.manhattenDistance(b2);
				b1 = b2;
			}
			aDis += a1.manhattenDistance(a2);
			a1 = a2;
		}
		System.out.println("Part2: " + minInterDis);
		return minDis;
	}

	Vec2 isIntersecting(Vec2 a1, Vec2 a2, Vec2 b1, Vec2 b2) {
		int aDir = vecDir(a1, a2);
		int bDir = vecDir(b1, b2);
		Vec2 ret = new Vec2();
		Vec2 xVec = new Vec2();
		Vec2 yVec = new Vec2();
		if (aDir == _XDIR && bDir == _YDIR) {
			xVec = new Vec2(Math.min(b1.x, b2.x), Math.max(b1.x, b2.x));
			yVec = new Vec2(Math.min(a1.y, a2.y), Math.max(a1.y, a2.y));
			ret.x = a1.x;
			ret.y = b1.y;
		} else if (aDir == _YDIR && bDir == _XDIR) {
			xVec = new Vec2(Math.min(a1.x, a2.x), Math.max(a1.x, a2.x));
			yVec = new Vec2(Math.min(b1.y, b2.y), Math.max(b1.y, b2.y));
			ret.x = b1.x;
			ret.y = a1.y;
		} else {
			return null;
		}
		if (ret.x >= xVec.x && ret.x <= xVec.y && ret.y >= yVec.x && ret.y <= yVec.y) {
			return ret;
		} else {
			return null;
		}
	}

	final static int _XDIR = 1;
	final static int _YDIR = 2;
	final static int _EDIR = 3;
	final static int _DDIR = 4;

	int vecDir(Vec2 a, Vec2 b) {
		if (a.x == b.x) {
			if (a.y == b.y) {
				return _EDIR;
			}
			return _XDIR;
		}
		if (a.y == b.y) {
			return _YDIR;
		}
		return _DDIR;
	}

	void printGrid(int[][] g) {
		for (int i = g.length - 1; i >= 0; i--) {
			for (int j = 0; j < g[0].length; j++) {
				System.out.print(g[i][j]);
			}
			System.out.println();
		}
	}
}
