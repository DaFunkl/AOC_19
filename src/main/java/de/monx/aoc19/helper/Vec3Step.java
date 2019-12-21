package de.monx.aoc19.helper;

import lombok.Data;

@Data
public class Vec3Step {
	public Vec3 pos;
	public int step;

	public Vec3Step() {
	}

	public Vec3Step(Vec3 pos, int step) {
		this.pos = pos;
		this.step = step;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Vec3Step o = (Vec3Step) obj;
		return o.step == step && pos.equals(o.pos);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(pos, step);
	}

	@Override
	public Vec3Step clone() {
		return new Vec3Step(pos.clone(), step);
	}
}
