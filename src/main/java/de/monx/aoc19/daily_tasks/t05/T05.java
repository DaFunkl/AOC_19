package de.monx.aoc19.daily_tasks.t05;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import de.monx.aoc19.helper.TDay;

public class T05 extends TDay {
	final static int _END = 99;
	final static int _ADD = 1;
	final static int _MUL = 2;
	final static int _INP = 3;
	final static int _OUT = 4;
	final static int _JIT = 5;
	final static int _JIF = 6;
	final static int _LTH = 7;
	final static int _EQU = 8;
	final static int _OP = 0;
	final static int _R1 = 1;
	final static int _R2 = 2;
	final static int _DE = 3;
	int inputPart1 = 1;
	int[] stack = null;
	String out = "";

	public T05(String path, String day) {
		super(path, day);
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
		System.out.println("Part: 1");
		init(1, line);
		executeStack();
		init(5, line);
		System.out.println("Part: 2");
		executeStack();
		return this;
	}

	void init(int input, String line) {
		parseLineToStack(line);
		inputPart1 = input;
	}

	void parseLineToStack(String line) {
		String[] arr = line.split(",");
		stack = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			stack[i] = Integer.valueOf(arr[i]);
		}
	}

	boolean stepper = false;

	void executeStackStepper() {
		stepper = true;
		int pointer = 0;
		while (pointer < stack.length) {
			int inc = 0;
			System.out.println("print Stack: Pointer: " + pointer);
			printStack(pointer, pointer + 4, pointer);
			if (stack[pointer] < 10) {
				inc = executeOpCode(pointer, new int[] { stack[pointer], 0, 0, 0 });
			} else {
				inc = executeOpCode(pointer, parseParameterModeOpCode(stack[pointer]));
			}
			if (inc == 1) {
				return;
			}
			pointer += inc;
			System.out.println("next Pointer: " + pointer);
//			new Scanner(System.in).nextLine();
		}
		stepper = false;
	}

	void executeStack() {
		int pointer = 0;
		while (pointer < stack.length) {
			int inc = 0;
			if (stack[pointer] < 10) {
				inc = executeOpCode(pointer, new int[] { stack[pointer], 0, 0, 0 });
			} else {
				inc = executeOpCode(pointer, parseParameterModeOpCode(stack[pointer]));
			}
			if (inc == 1) {
				return;
			}
			pointer += inc;
		}
	}

	void printStack(int start, int end, int pointer) {
		start = start < 0 ? 0 : start;
		end = end > stack.length ? stack.length - 1 : end;
		for (int i = start; i < end; i++) {
			if (i == pointer) {
				System.out.println("STACK[" + i + "] => " + stack[i]);
			} else {
				System.out.println("Stack[" + i + "] -> " + stack[i]);
			}
		}
	}

	/**
	 * executes OpCode directly on stack
	 * 
	 * @param pointer current pointer position
	 * @return increment pointer by this amount after execute
	 */
	int executeOpCode(int pointer, int[] pmOp) {
		if (pmOp[_OP] == _END) {
			System.out.println("\nEnd reached: vals[" + pointer + "]: " + stack[pointer]);
			return 1;
		}
		int arg1 = pmOp[1] == 0 ? stack[stack[pointer + _R1]] : stack[pointer + _R1];
		if (pmOp[_OP] == _OUT) {
			if (pmOp[1] == 1) {
				System.out.print(stack[pointer + 1]);
			} else {
				System.out.print(stack[stack[pointer + 1]]);
			}
			return 2;
		}
		if (pmOp[_OP] == _INP) {
			stack[stack[pointer + 1]] = getInput();
			return 2;
		}

		int arg2 = pmOp[2] == 0 ? stack[stack[pointer + _R2]] : stack[pointer + _R2];
		int dest = stack[pointer + _DE];
		switch (pmOp[_OP]) {
		case _ADD:
			stack[dest] = arg1 + arg2;
			return 4;
		case _MUL:
			stack[dest] = arg1 * arg2;
			return 4;
		case _JIT:
			return arg1 != 0 ? arg2 - pointer : 3;
		case _JIF:
			return arg1 == 0 ? arg2 - pointer : 3;
		case _LTH:
			stack[dest] = arg1 < arg2 ? 1 : 0;
			return 4;
		case _EQU:
			stack[dest] = arg1 == arg2 ? 1 : 0;
			return 4;
		default:
			System.err.println("Error: executeOpCodePM(" + pointer + ", " + Arrays.toString(pmOp) + ")");
			return -1;
		}
	}

	int[] parseParameterModeOpCode(int opcode) {
		int[] ret = new int[5];
		ret[0] = opcode % 100;
		opcode /= 100;
		for (int i = 1; i < ret.length; i++) {
			ret[i] = opcode % 10;
			if (ret[i] != 0 && ret[i] != 1) {
				System.err.println("Error: parseParameterModeOpCode(" + opcode + "): wrong Opcode");
			}
			opcode /= 10;
		}
		return ret;
	}

	int getInput() {
		return inputPart1;
	}
}
