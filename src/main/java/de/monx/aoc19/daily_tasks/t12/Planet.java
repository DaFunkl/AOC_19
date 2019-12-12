package de.monx.aoc19.daily_tasks.t12;

import de.monx.aoc19.helper.Vec3;

public class Planet {
	public Vec3 pos;
	public Vec3 vel;

	public Planet() {
	}

	public Planet(Vec3 pos) {
		this.pos = pos;
		this.vel = new Vec3();
	}

	public Planet(Vec3 pos, Vec3 vel) {
		this.pos = pos;
		this.vel = vel;
	}

	public Planet clone() {
		return new Planet(pos, vel);
	}

	public void adjustVel(Planet o) {
		Vec3 velAdj = new Vec3(adjAmt(pos.x, o.pos.x), adjAmt(pos.y, o.pos.y), adjAmt(pos.z, o.pos.z));
		vel.addI(velAdj);
	}

	public int adjAmt(int a, int b) {
		if (a == b) {
			return 0;
		}
		if (a < b) {
			return 1;
		} else {
			return -1;
		}
	}

	public void applyVel() {
		pos.addI(vel);
	}

	public int energy() {
		return pos.energy() * vel.energy();
	}
	
	@Override
	public String toString() {
		return "Pos: " + pos + ", Vel: " + vel;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Planet o = (Planet) obj;
		return o.pos.equals(pos) && o.vel.equals(vel);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(pos, vel);
	}
	
}
