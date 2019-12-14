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

	
	final static Elem elem_Fuel = new Elem("FUEL", 0);
	final static Elem elem_Ore = new Elem("ORE", 0);
	int part1(List<Formular> formulars) {
		Map<Elem, Formular> map = parseFormularListToMap(formulars);
		int oreCount = 0;
		List<Elem> dque = new ArrayList<Elem>();
		dque.add(map.get(elem_Fuel).output);
		Map<Elem, Integer> waste = new HashMap<>();
		while(!dque.isEmpty()) {
			Elem e = dque.get(0);
			System.out.println("Check: " + e);
			dque.remove(0);
			int amtNeeded = e.amt;
			if(waste.containsKey(e)) {
				// look if already some previous "waste"was left over
				amtNeeded -= waste.get(e);
				if(amtNeeded >= 0) {
					// if all "waste" could be reused, and still some Elems are needed
					// remove count from waste, and continue
					waste.remove(e);
				} else {
					// some waste is still left over, no Elems are needed 
					// adjust waste count
					waste.put(e, waste.get(e) - e.amt);
					continue;
				}
			}

			if(e.equals(elem_Ore)) {
				oreCount += amtNeeded;
				continue;
			}
			Formular f = map.get(e);
			int fAmt = f.output.amt;
			int mult = amtNeeded / fAmt;
			if(amtNeeded % fAmt != 0) {
				mult++;
			}
			int producing = fAmt * mult;
			int wasteAmt = producing - amtNeeded;
			if(waste.containsKey(e)) {
				waste.put(e, waste.get(e) + wasteAmt); 
			} else {
				waste.put(e, wasteAmt);
			}
			List<Elem> needs = map.get(e).input;
			System.out.println("needs: " + needs);
			for(int i = 0; i < needs.size(); i++) {
				Elem add = needs.get(0);
				add.amt *= mult; 
				if(add.equals(elem_Ore)) {
					oreCount += add.amt;
					continue;
				}
				boolean contained = false;
				for(int n = 0; n < dque.size(); n++) {
					if(dque.get(n).equals(add)) {
						dque.get(n).amt += add.amt;
						contained = true;
						break;
					}
				}
				if(!contained) {
					dque.add(add);
				}
			}
		}
		return oreCount;
	}

	Map<Elem, Formular> parseFormularListToMap(List<Formular> formulars) {
		Map<Elem, Formular> map = new HashMap<>();
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
