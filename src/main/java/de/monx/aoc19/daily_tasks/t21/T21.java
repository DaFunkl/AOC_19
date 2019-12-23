package de.monx.aoc19.daily_tasks.t21;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc19.helper.BF;
import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.intcode.IntCode;

public class T21 extends TDay {

	@Override
	public TDay exec() {
		long[] in = getInput();
//		System.out.println("Part1: " + part1(in));
		System.out.println("Part2: " + part2(in));
		return this;
	}

	long part2(long[] opCode) {
		IntCode ic = new IntCode();
		ic.setStack(opCode);
		ic.execIO();
		printOut(ic.getAndResetOutput());
		String input = ""//
				+ "OR A T\n" //
				+ "AND B T\n" //
				+ "AND C T\n" //
				+ "NOT T T\n" //
				+ "OR E J\n" //
				+ "OR H J\n" //
				+ "AND T J\n" //
				+ "AND D J\n" //
				+ "RUN\n";//
		printOut(ic.getAndResetOutput());
		for (char c : input.toCharArray()) {
			System.out.print(c);
			ic.setInput((int) c);
			ic.execIO();
		}
		List<Long> ll = ic.getOutputList();
		printOut(ll);
		System.out.println(Arrays.toString(ll.toArray()));
		return ll.get(ll.size() - 1);
	}

//P A B C D E F G H I J Name
//  @        		  1 Z
// @  @        
//@    @       
//# . X X # # # # # #  
//  @        		  0 Y
// @  @        
//@    @       
//# X X X . # # # # #  
//  @        		  0 X
// @  @        
//@    @       
//# # X . # . # # # #  
//
//	A	B	C	D	J	Name
//	0	X	X	1	1	Z
//	X	X	X	0	0	Y
//	1	X	0	1	1	X
	long part1(long[] opCode) {
		IntCode ic = new IntCode();
		ic.setStack(opCode);
		ic.execIO();
		printOut(ic.getAndResetOutput());
		String input = ""//
				+ "NOT C J\n" // Rule X
				+ "AND A J\n" //
				+ "NOT A T\n" // Rule Y
				+ "OR T J\n" //
				+ "AND D J\n" // Rule Z
				+ "WALK\n";
		printOut(ic.getAndResetOutput());
		for (char c : input.toCharArray()) {
			System.out.print(c);
			ic.setInput((int) c);
			ic.execIO();
		}
		List<Long> ll = ic.getOutputList();
		printOut(ll);
		System.out.println(Arrays.toString(ll.toArray()));
		return ll.get(ll.size() - 1);
	}

	void printOut(List<Long> out) {
//		System.out.println("LongList: " + Arrays.toString(out.toArray()));
		StringBuilder sb = new StringBuilder();
		for (long l : out) {
			if (l < Integer.MAX_VALUE && l > Integer.MIN_VALUE) {
				sb.append((char) l);
			} else {
				return;
			}
		}
		System.out.println(sb.toString());
	}

	long[] getInput() {
		String[] arr = stream.iterator().next().split(",");
		long[] ret = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = Long.valueOf(arr[i]);
		}
		return ret;
	}
}
