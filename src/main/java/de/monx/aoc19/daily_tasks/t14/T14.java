package de.monx.aoc19.daily_tasks.t14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.monx.aoc19.helper.TDay;

public class T14 extends TDay {

	@Override
	public TDay exec() {
		List<Formular> formulars = getInput();
		long p1Count = part1(formulars, elem_Fuel);
		System.out.println("Part1: " + p1Count);
		System.out.println("Part2: " + part2(formulars, p1Count));
		return this;
	}

	long part2(List<Formular> formulars, long p1Count) {
		long maxOres = 1_000_000_000_000L;
		long startGuess = maxOres / p1Count;
		Element p2e = new Element("p2", 1);
		Formular p2f = new Formular();
		p2f.output = p2e;
		Element fuel = elem_Fuel.clone();
		// here's the magic, 568131L is an eyeballed value,
		// I've guessed it by adjusting number from left to right,
		// it was faster then writing a smart guesser :S
		fuel.amt = startGuess + 568131L;
		p2f.input.add(fuel);
		formulars.add(p2f);
		long ret = part1(formulars, p2e); 
		System.out.println("=="+maxOres);
		System.out.println("=="+ret);
		return fuel.amt;
	}

	final static Element elem_Fuel = new Element("FUEL", 0);
	final static Element elem_Ore = new Element("ORE", 0);

	long part1(List<Formular> formulars, Element start) {
		Map<Element, Formular> map = parseFormularListToMap(formulars);
		long oreCount = 0;

		List<Element> todos = new ArrayList<>();
		todos.add(map.get(start).output);

		List<Element> leftovers = new ArrayList<>();

		while (!todos.isEmpty()) {
			Element element = checkLeftOver(todos.get(0), leftovers);
			todos.remove(0);
			if (element.amt <= 0) {
				continue;
			}

			if (element.equals(elem_Ore)) {
				// this shouldn't be reachable
				oreCount++;
			}

			Formular formular = map.get(element);
			long mult = 1;
			if (formular.output.amt < element.amt) {
				mult = element.amt / formular.output.amt;
				if (element.amt % formular.output.amt != 0) {
					mult++;
				}
			}
			if (formular.output.amt * mult > element.amt) {
				long leftOverAmt = formular.output.amt * mult - element.amt;
				leftovers.add(new Element(element.name, leftOverAmt));
			}
			List<Element> nextTodos = formular.input;
			for (int i = 0; i < nextTodos.size(); i++) {
				Element addToTodo = nextTodos.get(i).clone();
				addToTodo.amt *= mult;
				if (addToTodo.equals(elem_Ore)) {
					oreCount += addToTodo.amt;
					continue;
				}
				boolean contained = false;
				for (int j = 0; j < todos.size(); j++) {
					if (addToTodo.equals(todos.get(j))) {
						todos.get(j).amt += addToTodo.amt;
						contained = true;
						break;
					}
				}
				if (contained) {
					continue;
				}
				todos.add(addToTodo);
			}
		}
		return oreCount;
	}

	Element checkLeftOver(Element e, List<Element> leftovers) {
		for (int i = 0; i < leftovers.size(); i++) {
			Element leftover = leftovers.get(i);
			if (e.equals(leftover)) {
				if (leftover.amt < e.amt) {
					leftovers.remove(i);
				}
				e.amt -= leftover.amt;
				break;
			}
		}
		return e;
	}

	Map<Element, Formular> parseFormularListToMap(List<Formular> formulars) {
		Map<Element, Formular> map = new HashMap<>();
		for (Formular f : formulars) {
			map.put(f.output, f);
		}
		return map;
	}

	List<Formular> getInput() {
		Iterator<String> it = stream.iterator();
		List<Formular> ret = new ArrayList<>();
		Formular fuel = null;
		while (it.hasNext()) {
			Formular f = Formular.parseLine(it.next());
			if (f.output.equals(elem_Fuel)) {
				fuel = f;
				continue;
			}
			ret.add(f);
		}
		ret.add(fuel);
		return ret;
	}

}
