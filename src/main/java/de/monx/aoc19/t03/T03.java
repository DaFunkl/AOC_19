package de.monx.aoc19.t03;

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

	List<Vec2> getIntersections(List<Wire> wires) {

		return null;
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
		for (int i = 0; i < wires.size(); i++) {
			for (int j = i + 1; j < wires.size(); j++) {

			}
		}
		return minDis;
	}

	List<Vec2> getIntersections(List<Vec2> w1, List<Vec2> w2) {
		List<Vec2> ret = new ArrayList<>();
		for (int i = 0; i < w1.size() - 1; i++) {
			for (int j = 0; j < w2.size() - 1; j++) {

			}
		}
		return ret;
	}

	Vec2 isIntersecting(Vec2 a1) {
		
		return null;
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
