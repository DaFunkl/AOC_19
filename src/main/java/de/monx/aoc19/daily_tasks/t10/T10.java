package de.monx.aoc19.daily_tasks.t10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;

public class T10 extends TDay {

	@Override
	public TDay exec() {
		List<char[]> grid = getGrid();
		List<Vec2> asteroidList = initList(grid);
		Map<Vec2, Map<Float, List<Vec2_Dis>>> vMap = setup_vMap(asteroidList);
		Vec2 base = part1(vMap);
		System.out.println("Part1: " + base);
		Vec2 asteroid_200 = part2(vMap.get(base), 200);
		System.out.println("Part2: " + asteroid_200 + " -> " + (asteroid_200.x * 100 + asteroid_200.y));
		return this;
	}

	Vec2 part2(Map<Float, List<Vec2_Dis>> angleDisVecs, int bet) {
		Vec2 ret = null;
		List<Float> list = new ArrayList<Float>(angleDisVecs.keySet());
		java.util.Collections.sort(list);
		int count = 1;
		int pointer = 0;
		while(count <= bet || list.isEmpty()) {
			float angle = list.get(pointer);
			List<Vec2_Dis> vd = angleDisVecs.get(angle);
			int idx = 0;
			float minDis = vd.get(0).distance;
			for(int i = 1; i < vd.size(); i++) {
				if(minDis > vd.get(i).distance) {
					minDis = vd.get(i).distance;
					idx = i;
				}
			}
			ret = vd.get(idx).v;
			vd.remove(idx);
			count++;
			if(vd.isEmpty()) {
				angleDisVecs.remove(angle);
				list.remove(pointer);
				pointer--;
			} else {
				angleDisVecs.put(angle, vd);
			}
			if(list.isEmpty()) {
				break;
			}
			pointer = (pointer +1) % list.size();
		}
		return ret;
	}

	Map<Vec2, Map<Float, List<Vec2_Dis>>> setup_vMap(List<Vec2> asteroidList) {
		Map<Vec2, Map<Float, List<Vec2_Dis>>> vMap = new HashMap<>();
		for (int i = 0; i < asteroidList.size(); i++) {
			Vec2 iV = asteroidList.get(i);
			if (i == 0) { // init map in map
				vMap.put(iV, new HashMap<Float, List<Vec2_Dis>>());
			}
			for (int j = i + 1; j < asteroidList.size(); j++) {
				Vec2 jV = asteroidList.get(j);
				if (i == 0) { // init map in map
					vMap.put(jV, new HashMap<Float, List<Vec2_Dis>>());
				}

				float distance = iV.getDistance(jV);
				float angle = iV.getAngle(jV);
				angle = (angle + 90) % 360;
				
				Vec2_Dis iVD = new Vec2_Dis(jV, distance);
				Vec2_Dis jVD = new Vec2_Dis(iV, distance);
				if (vMap.get(iV).containsKey(angle)) {
					vMap.get(iV).get(angle).add(iVD);
				} else {
					List<Vec2_Dis> l = new ArrayList<>();
					l.add(iVD);
					vMap.get(iV).put(angle, l);
				}
				angle = (angle + 180) % 360;
				if (vMap.get(jV).containsKey(angle)) {
					vMap.get(jV).get(angle).add(jVD);
				} else {
					List<Vec2_Dis> l = new ArrayList<>();
					l.add(jVD);
					vMap.get(jV).put(angle, l);
				}
			}
		}
		return vMap;
	}

	Vec2 part1(Map<Vec2, Map<Float, List<Vec2_Dis>>> vMap) {
		Vec2 opt = null;
		int max = 0;
		for (Vec2 v : vMap.keySet()) {
			if (vMap.get(v).size() > max) {
				max = vMap.get(v).size();
				opt = v;
			}
		}
		return opt;
	}

	List<Vec2> initList(List<char[]> grid) {
		List<Vec2> detectCount = new ArrayList<>();
		for (int y = 0; y < grid.size(); y++) {
			char[] arr = grid.get(y);
			for (int x = 0; x < arr.length; x++) {
				if (arr[x] == '#') {
					detectCount.add(new Vec2(x, y));
				}
			}
		}
		return detectCount;
	}

	List<char[]> getGrid() {
		Iterator<String> it = stream.iterator();
		List<char[]> grid = new ArrayList<>();
		while (it.hasNext()) {
			grid.add(it.next().toCharArray());
		}
		return grid;
	}
}
