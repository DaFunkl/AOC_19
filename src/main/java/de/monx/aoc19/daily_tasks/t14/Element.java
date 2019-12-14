package de.monx.aoc19.daily_tasks.t14;

public class Element {
	public String name;
	public int amt;

	public Element() {
	}

	public Element(String name, int amt) {
		this.name = name;
		this.amt = amt;
	}

	public static Element parseElem(String str) {
		String[] sar = str.split(" ");
		int amt = Integer.valueOf(sar[0].trim());
		String name = sar[1].trim();
		return new Element(name, amt);
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
		return name.equals(((Element) obj).name);
	}

	@Override
	public String toString() {
		return name + "(" + amt + ")";
	}
}
