package de.monx.aoc19.daily_tasks.t05;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T05 extends TDay {
	int p1 = 0;
	int p2 = 0;
	int execP = 3;
	public final static int _EXEC_P1 = 1;
	public final static int _EXEC_P2 = 2;
	public final static int _EXEC_BOTH = 3;

	public T05 setupP1P2Input(int p1, int p2) {
		this.p1 = p1;
		this.p2 = p2;
		return this;
	}

	public void setExecPart(int part) {
		if (part >= _EXEC_P1 && part <= _EXEC_BOTH) {
			execP = part;
		}
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
		IntCode intCode = new IntCode();
		int[] stack = intCode.parseLineToStack(line);
		if (execP == _EXEC_P1 || execP == _EXEC_BOTH) {
			System.out.println("Part: 1");
			intCode.init(p1, stack.clone());
			List<Integer> out = intCode.executeStack();
			System.out.println(Arrays.deepToString(out.toArray()));
		}
		if (execP == _EXEC_P2 || execP == _EXEC_BOTH) {
			intCode.init(p2, stack.clone());
			System.out.println("Part: 2");
			List<Integer> out = intCode.executeStack();
			System.out.println(Arrays.deepToString(out.toArray()));
		}
		return this;
	}
}
