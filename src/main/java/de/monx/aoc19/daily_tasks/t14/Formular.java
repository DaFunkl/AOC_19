package de.monx.aoc19.daily_tasks.t14;

import java.util.ArrayList;
import java.util.List;

public class Formular {
	public List<Elem> input = new ArrayList<>();
	public Elem output;

	public Formular() {
	}

	public Formular(List<Elem> in, Elem out) {
		input = in;
		output = out;
	}

	public static Formular parseLine(String line) {
		String[] arr = line.split("=>");
		List<Elem> in = new ArrayList<>();
		for (String sarr : arr[0].split(",")) {
			in.add(Elem.parseElem(sarr.trim()));
		}
		Elem out = Elem.parseElem(arr[1].trim());
		return new Formular(in, out);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(output.toString());
		sb.append(" <= ");
		for (Elem e : input) {
			sb.append(e);
			sb.append(", ");
		}
		return super.toString();
	}
	
}
