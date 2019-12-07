package de.monx.aoc19.helper.intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IntCode {
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
	int[] inputOpCode3 = new int[0];
	int inputPointer = 0;
	int[] stack = null;
	String out = "";
	int p1 = 0;
	int p2 = 0;
	List<Integer> output = new ArrayList<>();

	public void init(int[] input, int[] stack) {
		output = new ArrayList<>();
		this.stack = stack;
		inputOpCode3 = input;
		inputPointer = 0;
	}

	public List<Integer> executeStack() {
		int pointer = 0;
		while (pointer < stack.length) {
			int inc = 0;
			if (stack[pointer] < 10) {
				inc = executeOpCode(pointer, new int[] { stack[pointer], 0, 0, 0 });
			} else {
				inc = executeOpCode(pointer, parseParameterModeOpCode(stack[pointer]));
			}
			if (inc == 1) {
				break;
			}
			pointer += inc;
		}
		return output;
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
		// Halting: 99
		if (pmOp[_OP] == _END) {
			return 1;
		}
		// 1 Arg Operator: Output: 4
		int arg1 = pmOp[1] == 0 ? stack[stack[pointer + _R1]] : stack[pointer + _R1];
		if (pmOp[_OP] == _OUT) {
			if (pmOp[1] == 1) {
				output.add(stack[pointer + 1]);
			} else {
				output.add(stack[stack[pointer + 1]]);
			}
			return 2;
		}
		// Input: 3
		if (pmOp[_OP] == _INP) {
			stack[stack[pointer + 1]] = getInput();
			return 2;
		}
		int arg2 = pmOp[2] == 0 ? stack[stack[pointer + _R2]] : stack[pointer + _R2];
		int dest = stack[pointer + _DE];
		// Operators using 2-3 Args
		// Add: 1, Mul: 2, LTH(LessThen): 7, EQU(Equals): 8, JIT(jump if true): 5,
		// JIF(jump if false): 6
		switch (pmOp[_OP]) {
		case _ADD:
			stack[dest] = arg1 + arg2;
			return 4;
		case _MUL:
			stack[dest] = arg1 * arg2;
			return 4;
		case _LTH:
			stack[dest] = arg1 < arg2 ? 1 : 0;
			return 4;
		case _EQU:
			stack[dest] = arg1 == arg2 ? 1 : 0;
			return 4;
		case _JIT:
			return arg1 != 0 ? arg2 - pointer : 3;
		case _JIF:
			return arg1 == 0 ? arg2 - pointer : 3;
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
		return inputOpCode3[inputPointer++];
	}

	public static int[] parseLineToStack(String line) {
		String[] arr = line.split(",");
		int[] stack = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			stack[i] = Integer.valueOf(arr[i]);
		}
		return stack;
	}
}
