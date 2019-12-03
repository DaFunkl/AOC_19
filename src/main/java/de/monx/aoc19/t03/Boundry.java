package de.monx.aoc19.t03;

import de.monx.aoc19.helper.Vec2;
import lombok.Getter;

@Getter
public class Boundry {
	private Vec2 max = new Vec2(Integer.MIN_VALUE, Integer.MIN_VALUE);
	private Vec2 min = new Vec2(Integer.MAX_VALUE, Integer.MAX_VALUE);

	public void refresh(Boundry v) {
		refreshMin(v.min);
		refreshMax(v.max);
	}

	public void refresh(Vec2 v) {
		refreshMin(v);
		refreshMax(v);
	}

	private void refreshMin(Vec2 v) {
		if (v.x < min.x) {
			min.x = v.x;
		}
		if (v.y < min.y) {
			min.y = v.y;
		}
	}

	private void refreshMax(Vec2 v) {
		if (v.x > max.x) {
			max.x = v.x;
		}
		if (v.y > max.y) {
			max.y = v.y;
		}
	}

	public Vec2 getCenter() {
		return max.sub(min).div(2);
	}

	@Override
	public String toString() {
		return "Boundry: Min: " + min + ", Max: " + max;
	}
}
