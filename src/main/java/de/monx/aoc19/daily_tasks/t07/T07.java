package de.monx.aoc19.daily_tasks.t07;

import java.util.Iterator;

import de.monx.aoc19.daily_tasks.t05.T05;
import de.monx.aoc19.helper.TDay;

public class T07  extends TDay {

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		System.out.println(it.next());
		T05 t5 = new T05();
		
		
		return this;
	}
	
}
