package de.monx.aoc19.helper;

public class Vec2 {
	public int x;
	public int y;

	public Vec2() {
	}

	public Vec2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int manhattenDistance(Vec2 v) {
		return deltaAbs(x, v.x) + deltaAbs(y, v.y);
	}

	public int manhattenDistance() {
		return deltaAbs(x, 0) + deltaAbs(y, 0);
	}
	
	private int deltaAbs(int a, int b) {
		return Math.abs(a - b);
	}

	public Vec2 add(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}

	public Vec2 sub(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);
	}

	public Vec2 div(int d) {
		return new Vec2(x / d, y / d);
	}

	@Override
	public String toString() {
		return "Vec2:( " + x + " | " + y + ")";
	}
}
