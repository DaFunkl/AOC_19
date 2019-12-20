package de.monx.aoc19.daily_tasks.t20;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;

public class T20 extends TDay {
	final static Vec2[] _DIRS = { //
			new Vec2(0, -1), // up
			new Vec2(0, 1), // down
			new Vec2(-1, 0), // left
			new Vec2(1, 0) // right
	};

	@Override
	public TDay exec() {
		List<char[]> input = getInput();
		printGrid(input);
		closeDeadEnds(input);
		printGrid(input);
		return this;
	}

	void closeDeadEnds(List<char[]> input) {
		boolean allClosed = false;
		while (!allClosed) {
			allClosed = true;
			for (int y = 1; y < input.size() - 1; y++) {
				for (int x = 1; x < input.get(y).length - 1; x++) {
					Vec2 cp = new Vec2(x,y);
					if(input.get(cp.y)[cp.x] != '.') {
						continue;
					}
					int wallCount = 0;
					for(Vec2 d : _DIRS) {
						Vec2 cv = cp.add(d);
						if(input.get(cv.y)[cv.x] == '#') {
							wallCount++;
						}
					}
					if(wallCount == 3) {
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
