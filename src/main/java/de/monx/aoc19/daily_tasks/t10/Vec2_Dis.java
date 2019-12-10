package de.monx.aoc19.daily_tasks.t10;

import de.monx.aoc19.helper.Vec2;

public class Vec2_Dis {
	public Vec2 v;
	public float distance;

	public Vec2_Dis() {
	}

	public Vec2_Dis(Vec2 v, float d) {
		this.v = v;
		distance = d;
	}

	@Override
	public String toString() {
		return "V: " + v + ", dis: " + distance;
	}
}
