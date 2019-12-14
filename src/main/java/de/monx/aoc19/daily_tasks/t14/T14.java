package de.monx.aoc19.daily_tasks.t14;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc19.helper.TDay;

public class T14 extends TDay {

	@Override
	public TDay exec() {
		List<Formular> formulars = getInput();

		System.out.println("Part1: " + part1(formulars));

		return this;
	}

	final static Element elem_Fuel = new Element("FUEL", 0);
	final static Element elem_Ore = new Element("ORE", 0);

	int part1(List<Formular> formulars) {
		Map<Element, Formular> map = parseFormularListToMap(formulars);
		int oreCount = 0;

		List<Element> todos = new ArrayList<>();
		todos.add(map.get(elem_Fuel).output);

		List<Element> leftovers = new ArrayList<>();

		while (!todos.isEmpty()) {
			System.out.println("Leftovers: " + leftovers);
			Element element = checkLeftOver(todos.get(0), leftovers);
			System.out.println("check: " + element);
			todos.remove(0);
			if (element.amt <= 0) {
				continue;
			}
			if (element.equals(elem_Ore)) {
				oreCount++;
			}

			Formular formular = map.get(element);
			int mult = 1;
			if (formular.output.amt < element.amt) {
				mult = element.amt / formular.output.amt;
				if (element.amt % formular.output.amt != 0) {
					mult++;
				}
			}
			if(formular.output.amt * mult > element.amt) {
				int leftOverAmt = formular.output.amt * mult - element.amt;
				leftovers.add(new Element(element.name, leftOverAmt));
			}

			List<Element> nextTodos = formular.input;
			System.out.println("nextTodos: " + nextTodos);
			for (int i = 0; i < nextTodos.size(); i++) {
				Element addToTodo = nextTodos.get(i);
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
			System.out.println("oreCount: " + oreCount);
			System.out.println("todos: " + todos);

		}

		return oreCount;
	}

	int[] adjustFormular(Element e, Formular f) {
		int outNeeded = e.amt;
		int fProducing = f.output.amt;
		if (fProducing > outNeeded) {

		}

		return null;
	}

	Element checkLeftOver(Element e, List<Element> leftovers) {
		for (int i = 0; i < leftovers.size(); i++) {
			Element leftover = leftovers.get(i);
			if (e.equals(leftover)) {
				if (leftover.amt < e.amt) {
					leftovers.remove(i);
				} else {
					leftovers.get(i).amt -= e.amt;
				}
				e.amt -= leftover.amt;
				break;
			}
		}
		return e;
	}

	int part1_bak(List<Formular> formulars) {
		Map<Element, Formular> map = parseFormularListToMap(formulars);
		int oreCount = 0;
		List<Element> dque = new ArrayList<Element>();
		dque.add(map.get(elem_Fuel).output);
		Map<Element, Integer> waste = new HashMap<>();
		while (!dque.isEmpty()) {
			Element e = dque.get(0);
			dque.remove(0);
			System.out.println("Check: " + e);
			int amtNeeded = e.amt;
			if (e.equals(elem_Ore)) {
				oreCount += amtNeeded;
				continue;
			}
			if (waste.containsKey(e)) {
				// look if already some previous "waste"was left over
				amtNeeded -= waste.get(e);
				if (amtNeeded >= 0) {
					// if all "waste" could be reused, and still some Elements are needed
					// remove count from waste, and continue
					waste.remove(e);
				} else {
					// some waste is still left over, no Elements are needed
					// adjust waste count
					waste.put(e, waste.get(e) - e.amt);
					continue;
				}
			}
			Formular f = map.get(e);
			int fAmt = f.output.amt;
			int mult = 1;
			if (fAmt < amtNeeded) {
				mult = amtNeeded / fAmt;
				if (amtNeeded % fAmt != 0) {
					mult++;
				}
			}
			int producing = fAmt * mult;
			int wasteAmt = producing - amtNeeded;
			if (waste.containsKey(e)) {
				waste.put(e, waste.get(e) + wasteAmt);
			} else {
				waste.put(e, wasteAmt);
			}
			List<Element> needs = map.get(e).input;
			System.out.println("needs: " + needs);
			for (int i = 0; i < needs.size(); i++) {
				Element add = needs.get(0);
				add.amt *= mult;
				if (add.equals(elem_Ore)) {
					oreCount += add.amt;
					continue;
				}
				boolean contained = false;
				for (int n = 0; n < dque.size(); n++) {
					if (dque.get(n).equals(add)) {
						dque.get(n).amt += add.amt;
						contained = true;
						break;
					}
				}
				if (!contained) {
					dque.add(add);
				}
			}
		}
		return oreCount;
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
		while (it.hasNext()) {
			ret.add(Formular.parseLine(it.next()));
		}
		return ret;
	}

}
