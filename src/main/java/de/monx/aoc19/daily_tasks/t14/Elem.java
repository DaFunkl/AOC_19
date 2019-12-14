package de.monx.aoc19.daily_tasks.t14;

public class Elem {
	public String name;
	public int amt;

	public Elem() {
	}

	public Elem(String name, int amt) {
		this.name = name;
		this.amt = amt;
	}

	public static Elem parseElem(String str) {
		String[] sar = str.split(" ");
		int amt = Integer.valueOf(sar[0].trim());
		String name = sar[1].trim();
		return new Elem(name, amt);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		return name.equals(((Elem) obj).name);
	}

	@Override
	public String toString() {
		return name + "(" + amt + ")";
	}
}
