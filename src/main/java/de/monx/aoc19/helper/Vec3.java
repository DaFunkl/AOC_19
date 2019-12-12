package de.monx.aoc19.helper;

public class Vec3 {
	public int x = 0;
	public int y = 0;
	public int z = 0;

	public Vec3() {
	}

	public Vec3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3 clone() {
		return new Vec3(x, y, z);
	}

	public Vec3 add(Vec3 v) {
		return new Vec3(x + v.x, y + v.y, z + v.z);
	}

	public void addI(Vec3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public Vec3 sub(Vec3 v) {
		return new Vec3(x - v.x, y - v.y, z - v.z);
	}

	public Vec3 div(int d) {
		return new Vec3(x / d, y / d, z / d);
	}

	public int energy() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	public Vec3 div(Vec3 v) {
		return new Vec3(x / v.x, y / v.y, z / v.z);
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
		Vec3 o = (Vec3) obj;
		return o.x == x && o.y == y && o.z == z;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(x, y, z);
	}

	public static Vec3 parseString(String str) {
		str = str.substring(1, str.length() - 1);
		String[] arr = str.split(",");
		int x = BF.str2Int(arr[0].split("=")[1]);
		int y = BF.str2Int(arr[1].split("=")[1]);
		int z = BF.str2Int(arr[2].split("=")[1]);
		return new Vec3(x, y, z);
	}
}
