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

	public float getAngle(Vec2 target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	public Vec2 clone() {
		return new Vec2(x, y);
	}

	public float getDistance(Vec2 target) {
		int x = target.x - this.x;
		int y = target.y - this.y;
		return (float) Math.sqrt(x * x + y * y);
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

	public void addI(Vec2 v) {
		x += v.x;
		y += v.y;
	}

	public Vec2 sub(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);
	}

	public Vec2 div(int d) {
		return new Vec2(x / d, y / d);
	}

	public Vec2 div(Vec2 v) {
		return new Vec2(x / v.x, y / v.y);
	}

	@Override
	public String toString() {
		return "Vec2:( " + x + " | " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Vec2 o = (Vec2) obj;
		return o.x == x && o.y == y;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(x, y);
	}
}
