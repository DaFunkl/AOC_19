package de.monx.aoc19.daily_tasks.t22;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec2;

public class T22 extends TDay {
	final static int _INSTR_DINS = 0; // deal into new stack
	final static int _INSTR_DWIX = 1; // deal with increment xyz
	final static int _INSTR_CUTX = 2; // cut xyz
	final static String[] _INSTR_STR = { //
			"deal into new stack", //
			"deal with increment", //
			"cut" //
	};

	int amt = 10;

	@Override
	public TDay exec() {
		List<Vec2> input = getInput();

		System.out.println("Part2: " + part2(input, new BigInteger("119315717514047"),
				new BigInteger("101741582076661"), new BigInteger("2020")));
		return this;
	}

	BigInteger negmod(BigInteger val, BigInteger mod) {
		if (val.compareTo(new BigInteger("0")) < 0) {
			val = val.add(mod);
		}
		return val.mod(mod);
	}

	BigInteger invmod(BigInteger val, BigInteger deckSize) {
		return val.modPow(deckSize.subtract(new BigInteger("2")), deckSize);
	}

	String part2(List<Vec2> in, BigInteger b_decksize, BigInteger b_repeats, BigInteger b_pos) {
		BigInteger increment = new BigInteger("1");
		BigInteger offset = new BigInteger("0");
		BigInteger one = new BigInteger("1");

		for (Vec2 v : in) {

			switch (v.x) {
			case _INSTR_DINS:
				increment = increment.multiply(new BigInteger("-1"));
				increment = negmod(increment, b_decksize);
				offset = offset.add(increment);
				offset = negmod(offset, b_decksize);
				break;
			case _INSTR_DWIX:
				increment = increment.multiply(invmod(new BigInteger(v.y + ""), b_decksize));
				increment = negmod(increment, b_decksize);
				break;
			case _INSTR_CUTX:
				offset = offset.add(new BigInteger(v.y + "").multiply(increment));
				offset = negmod(offset, b_decksize);
				break;
			default:
				break;
			}
		}

		BigInteger finalincrement = increment.modPow(b_repeats, b_decksize);
		BigInteger finaloffset = offset.multiply(one.subtract(finalincrement))
				.multiply(invmod(one.subtract(increment).mod(b_decksize), b_decksize));
		finaloffset = negmod(finaloffset, b_decksize);
		return finaloffset.add(b_pos.multiply(finalincrement)).mod(b_decksize).toString();
	}

	long execIsntr_P2(Vec2 instr, long idx, long size) {
		switch (instr.x) {
		case _INSTR_CUTX:
			return cutx_P2(idx, size, instr.y);
		case _INSTR_DINS:
			return dins_P2(idx, size);
		case _INSTR_DWIX:
			return dwix_P2(idx, size, instr.y);
		default:
			System.err.println("Invalid Instruction: " + instr);
			return idx;
		}
	}

	long cutx_P2(long idx, long size, int x) {
		return (idx + x + size) % size;
	}

	long dins_P2(long idx, long size) {
		return (size - 1 - idx) % size;
	}

	long dwix_P2(long idx, long size, int x) {
		return (idx * (size - x)) % size;
	}

	boolean debugprint = false;

	int part1(List<Vec2> in, int amt) {
		Deque<Integer> dq = initDQ(amt);
		for (Vec2 instr : in) {
			if (debugprint) {
				printDQ(dq);
				System.out.println("--------------------------------");
				printInstr(instr);
				System.out.println("Before:");
//				BF.haltInput();
			}

			dq = execIsntr(instr, dq);

			if (debugprint) {
				System.out.println("After:");
				printDQ(dq);
//				BF.haltInput();
			}
		}
		printDQ(dq);
		int ret = 0;
		Iterator<Integer> it = dq.iterator();
		while (it.hasNext()) {
			if (it.next() == 2019) {
				return ret;
			}
			ret++;
		}
		return ret;
	}

	Deque<Integer> execIsntr(Vec2 instr, Deque<Integer> dq) {
		switch (instr.x) {
		case _INSTR_CUTX:
			return cutx(dq, instr.y);
		case _INSTR_DINS:
			return dins(dq);
		case _INSTR_DWIX:
			return dwix(dq, instr.y);
		default:
			System.err.println("Invalid Instruction: " + instr);
			return dq;
		}
	}

	Deque<Integer> cutx(Deque<Integer> dq, int x) {
		if (x > 0) {
			for (int i = 0; i < x; i++) {
				dq.addLast(dq.removeFirst());
			}
		} else if (x < 0) {
			for (int i = 0; i > x; i--) {
				dq.addFirst(dq.removeLast());
			}
		}
		return dq;
	}

	Deque<Integer> dwix(Deque<Integer> dq, int x) {
		int[] arr = makeIntArrWithDefaultValue(dq.size(), -1);
		int dealIdx = 0;
		while (!dq.isEmpty()) {
			arr[dealIdx] = dq.removeFirst();
			dealIdx = (dealIdx + x) % arr.length;
		}
		Deque<Integer> newDQ = new ArrayDeque<>();
		for (int i = 0; i < arr.length; i++) {
			newDQ.add(arr[i]);
		}
		return newDQ;
	}

	int[] makeIntArrWithDefaultValue(int size, int val) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = val;
		}
		return arr;
	}

	Deque<Integer> dins(Deque<Integer> dq) {
		Deque<Integer> newDQ = new ArrayDeque<>();
		while (!dq.isEmpty()) {
			newDQ.push(dq.pop());
		}
		return newDQ;
	}

	void printInstr(Vec2 v) {
		System.out.println(v + " -> " + _INSTR_STR[v.x] + " " + v.y);
	}

	void printDQ(Deque<Integer> dq) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (int i : dq) {
			sb.append(i);
			sb.append(", ");
			if (count++ == 100) {
				sb.append("\n");
				count = 0;
			}
		}
		System.out.println(sb.toString());
	}

	public static Deque<Integer> initDQ(int amt) {
		Deque<Integer> dq = new ArrayDeque<Integer>();
		for (int i = 0; i < amt; i++) {
			dq.addLast(i);
		}
		return dq;
	}

	List<Vec2> getInput() {
		List<Vec2> input = new ArrayList<>();
		Iterator<String> it = stream.iterator();
		while (it.hasNext()) {
			input.add(parseInstr(it.next()));
		}
		return input;
	}

	Vec2 parseInstr(String isntr) {
		String sar[] = isntr.split(" ");
		if (isntr.charAt(0) == 'c') {
			return new Vec2(_INSTR_CUTX, Integer.valueOf(sar[sar.length - 1]));
		} else {
			if (sar[1].charAt(0) == 'i') {
				return new Vec2(_INSTR_DINS, 0);
			} else {
				return new Vec2(_INSTR_DWIX, Integer.valueOf(sar[sar.length - 1]));
			}
		}
	}

}
