package de.monx.aoc19.daily_tasks.t11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.monx.aoc19.daily_tasks.t03.Boundry;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;
import de.monx.aoc19.helper.intcode.IntCode;

public class T11 extends TDay {

	@Override
	public TDay exec() {
		long[] input = getInput();

		Map<Vec2, Integer> p1 = part1(input, 0);
		System.out.println("Part1: " + p1.size());
		p1 = part1(input, 1);
		System.out.println("Part2: ");
		part2(p1);
		return this;
	}

	void part2(Map<Vec2, Integer> in) {
		Boundry boundry = new Boundry();
		for (Vec2 v : in.keySet()) {
			boundry.refresh(v);
		}
		System.out.println(boundry);
		for (int y = boundry.getMin().y; y <= boundry.getMax().y; y++) {
			for (int x = boundry.getMax().x; x >= boundry.getMin().x; x--) {
				Vec2 v = new Vec2(x, y);
				if (in.containsKey(v) && in.get(v) == 1) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	Map<Vec2, Integer> part1(long[] in, int firstInput) {
		IntCode robo = new IntCode();
		robo.setStack(in);
		int roboState = robo.execIO();
		Map<Vec2, Integer> gridPaint = new HashMap<>();
		Vec2 pos = new Vec2(0, 0);
		Vec2 dir = new Vec2(0, -1);
		boolean start = true;
		while (roboState != IntCode._STATE_HALT) {
			int color = 0;
			if (gridPaint.containsKey(pos)) {
				color = gridPaint.get(pos);
			}
			if (start) {
				color = firstInput;
				start = false;
			}
			robo.setInput(color);
			roboState = robo.execIO();
			List<Long> out = robo.getAndResetOutput();
			if (out.get(0) == 1) {
				gridPaint.put(pos.clone(), 1);
			} else if (out.get(0) == 0) {
				gridPaint.put(pos.clone(), 0);
			} else {
				System.err.println("Paint: Wrong output: " + Arrays.toString(out.toArray()));
			}
			// move
			if (out.get(1) == 0) {
				int tx = dir.x;
				dir.x = -dir.y;
				dir.y = tx;
			} else if (out.get(1) == 1) {
				int tx = dir.x;
				dir.x = dir.y;
				dir.y = -tx;
			} else {
				System.err.println("Dir: Wrong output: " + Arrays.toString(out.toArray()));
			}
			pos.addI(dir);
		}
		return gridPaint;
	}

	long[] getInput() {
		Iterator<String> it = stream.iterator();
		String[] arr = it.next().split(",");
		long[] ret = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = Long.valueOf(arr[i]);
		}
		return ret;
	}
}
