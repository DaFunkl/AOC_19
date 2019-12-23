package de.monx.aoc19.helper;

public class Vec3L {
	public long x = 0;
	public long y = 0;
	public long z = 0;

	public Vec3L() {
	}

	public Vec3L(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3L clone() {
		return new Vec3L(x, y, z);
	}

	public Vec3L add(Vec3L v) {
		return new Vec3L(x + v.x, y + v.y, z + v.z);
	}

	public void addI(Vec3L v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public Vec3L sub(Vec3L v) {
		return new Vec3L(x - v.x, y - v.y, z - v.z);
	}

	public Vec3L div(long d) {
		return new Vec3L(x / d, y / d, z / d);
	}

	public long energy() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	public Vec3L div(Vec3L v) {
		return new Vec3L(x / v.x, y / v.y, z / v.z);
	}

	@Override
	public String toString() {
		return "Vec3:( " + x + " | " + y + " | " + z + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Vec3L o = (Vec3L) obj;
		return o.x == x && o.y == y && o.z == z;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(x, y, z);
	}

}
