package de.monx.aoc19.daily_tasks.t14;

import java.util.ArrayList;
import java.util.List;

public class Formular {
	public List<Element> input = new ArrayList<>();
	public Element output;

	public Formular() {
	}

	public Formular(List<Element> in, Element out) {
		input = in;
		output = out;
	}

	public static Formular parseLine(String line) {
		String[] arr = line.split("=>");
		List<Element> in = new ArrayList<>();
		for (String sarr : arr[0].split(",")) {
			in.add(Element.parseElem(sarr.trim()));
		}
		Element out = Element.parseElem(arr[1].trim());
		return new Formular(in, out);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(output.toString());
		sb.append(" <= ");
		for (Element e : input) {
			sb.append(e);
			sb.append(", ");
		}
		return super.toString();
	}
	
}
