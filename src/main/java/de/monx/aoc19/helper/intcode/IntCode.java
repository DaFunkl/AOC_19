package de.monx.aoc19.helper.intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import lombok.Data;
import lombok.Getter;

@Data
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
	final static int _ARB = 9;
	final static int _OP = 0;
	final static int _R1 = 1;
	final static int _R2 = 2;
	final static int _DE = 3;
	final static int _MODE_PARAMETER = 0;
	final static int _MODE_IMIDIATE = 1;
	final static int _MODE_RELATIVEBASE = 2;
	public final static int _STATE_HALT = 99;
	public final static int _STATE_NONE = 0;
	public final static int _STATE_INPUT_WAITING = 1;
	public final static int _STATE_INPUT_SUBMITTED = 2;
	public final static int _STATE_INPUT_RESUMED = 3;
	public final static String[] _STATE_STR = { //
			"_STATE_NONE", "_STATE_INPUT_WAITING", "_STATE_INPUT_SUBMITTED", "_STATE_INPUT_RESUMED", };

	private long relativBase = 0;
	private long[] inputOpCode3 = new long[0];
	private int inputPointer = 0;
	@Getter private int pointer = 0;
	private long[] stack = null;
	private int out = 0;
	private int state = _STATE_NONE;
	private List<Long> output = new ArrayList<>();
	private long outputReg = 0;
	private boolean outputReady = false;

	public IntCode clone() {
		IntCode clone = new IntCode();
		clone.setRelativBase(relativBase);
		clone.setInputOpCode3(inputOpCode3.clone());
		clone.setInputPointer(inputPointer);
		clone.setStack(stack.clone());
		clone.setOut(out);
		clone.setState(state);
		clone.setOutput(new ArrayList<>(output));
		clone.setOutputReg(outputReg);
		clone.setOutputReady(outputReady);
		return clone;
	}

	public long getOutput() {
		if (outputReady) {
			outputReady = false;
		} else {
			System.err.println("getOuptut called, wasn't ready yet");
			new Scanner(System.in).nextLine();
		}
		return outputReg;
	}

	public List<Long> getOutputList() {
		return output;
	}

	public List<Long> getAndResetOutput() {
		List<Long> ret = output;
		output = new ArrayList<>();
		outputReg = 0;
		return ret;
	}

	public void output(long out) {
		this.outputReg = out;
		outputReady = true;
	}

	public void init(int[] input, long[] stack) {
		output = new ArrayList<>();
		setStack(stack);
		inputOpCode3 = intArr2LongArr(input);
		inputPointer = 0;
	}

	long[] intArr2LongArr(int[] input) {
		long[] longInput = new long[input.length];
		for(int i = 0; i < input.length; i++) {
			longInput[i] = input[i];
		}
		return longInput;
	}
	
	public void setStack(long[] stack) {
		this.stack = stack;
	}

	public void setInput(int in) {
		inputOpCode3 = new long[] { in };
		inputPointer = 0;
		state = _STATE_INPUT_SUBMITTED;
	}

	public void setInput(long in) {
		inputOpCode3 = new long[] { in };
		inputPointer = 0;
		state = _STATE_INPUT_SUBMITTED;
	}
	
	public int execIO() {
		while (pointer < stack.length) {
			int inc = 0;
			if (stack[pointer] < 10) {
				inc = executeOpCode(pointer, new int[] { (int) stack[pointer], 0, 0, 0 }, true);
			} else {
				inc = executeOpCode(pointer, parseParameterModeOpCode((int) stack[pointer]), true);
			}
			if (state == _STATE_HALT || state == _STATE_INPUT_WAITING) {
				break;
			}
			pointer += inc;
		}
		return state;
	}

	public List<Long> executeStack() {
		int pointer = 0;
		while (pointer < stack.length) {
			int inc = 0;
			if (stack[pointer] < 10 || stack[pointer] == 99) {
				inc = executeOpCode(pointer, new int[] { (int) stack[pointer], 0, 0, 0 }, false);
			} else {
				inc = executeOpCode(pointer, parseParameterModeOpCode((int) stack[pointer]), false);
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
		end = end > stack.length ? stack.length : end;
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
	int executeOpCode(int pointer, int[] pmOp, boolean io) {
		// Halting: 99
		if (pmOp[_OP] == _END) {
			state = _STATE_HALT;
			return 1;
		}
		// 1 Arg Operator: Output: 4
		long arg1 = getArg(pmOp[1], pointer + _R1);
//		int arg1 = pmOp[1] == 0 ? stack[stack[pointer + _R1]] : stack[pointer + _R1];
		if (pmOp[_OP] == _OUT) {
			if (pmOp[1] == _MODE_IMIDIATE) {
				output(getStackAt(pointer + 1));
			} else if (pmOp[1] == _MODE_PARAMETER) {
				output(getStackAt((int) getStackAt(pointer + 1)));
			} else {
				output(getStackAt((int) (getStackAt(pointer + 1) + relativBase)));
			}
			output.add(this.outputReg);
			return 2;
		}
		// Input: 3
		if (pmOp[_OP] == _INP) {
			int inputDest = (int) getStackAt(pointer + 1);
			if (pmOp[1] == _MODE_PARAMETER) {
				inputDest = (int) getStackAt(pointer + 1);
			} else {
				inputDest = (int) (getStackAt(pointer + 1) + relativBase);
			}
			if (io && state != _STATE_INPUT_SUBMITTED) {
				state = _STATE_INPUT_WAITING;
				return 2;
			}
			state = _STATE_INPUT_RESUMED;
			stack[inputDest] = getInput();
			return 2;
		}
		// adjusts the relative base
		if (pmOp[_OP] == _ARB) {
			if (pmOp[1] == _MODE_PARAMETER) {
				relativBase += getStackAt((int) getStackAt(pointer + 1));
			} else if (pmOp[1] == _MODE_IMIDIATE) {
				relativBase += getStackAt(pointer + 1);
			} else {
				relativBase += getStackAt((int) (getStackAt(pointer + 1) + relativBase));
			}
			return 2;
		}

		long arg2 = getArg(pmOp[2], pointer + _R2);
		int dest = (int) getStackAt(pointer + _DE);
		if (pmOp[3] == _MODE_RELATIVEBASE) {
			dest = (int) (getStackAt(pointer + _DE) + relativBase);
		}
		// Operators using 2-3 Args
		// Add: 1, Mul: 2, LTH(LessThen): 7, EQU(Equals): 8, JIT(jump if true): 5,
		// JIF(jump if false): 6
		switch (pmOp[_OP]) {
		case _ADD:
			setStackAt(dest, arg1 + arg2);
			return 4;
		case _MUL:
			setStackAt(dest, arg1 * arg2);
			return 4;
		case _LTH:
			setStackAt(dest, arg1 < arg2 ? 1 : 0);
			return 4;
		case _EQU:
			setStackAt(dest, arg1 == arg2 ? 1 : 0);
			return 4;
		case _JIT:
			return (int) (arg1 != 0 ? arg2 - pointer : 3);
		case _JIF:
			return (int) (arg1 == 0 ? arg2 - pointer : 3);
		default:
			System.err.println("Error: executeOpCodePM(" + pointer + ", " + Arrays.toString(pmOp) + ")");
			return -1;
		}
	}

	public long getArg(int mode, int pointer) {
		long arg = getStackAt(pointer);
		if (mode == _MODE_PARAMETER) {
			arg = getStackAt((int) arg);
		} else if (mode == _MODE_RELATIVEBASE) {
			arg = getStackAt((int) (arg + relativBase));
		}
		return arg;
	}

	public long getStackAt(int pointer) {
		if (pointer >= stack.length) {
			adjustStackSize(pointer);
		}
		return stack[pointer];
	}

	public void setStackAt(int pointer, long arg) {
		if (pointer >= stack.length) {
			adjustStackSize(pointer);
		}
		stack[pointer] = arg;
	}

	public void adjustStackSize(int size) {
		long[] newStack = new long[size + 1];
		for (int i = 0; i < stack.length; i++) {
			newStack[i] = stack[i];
		}
		stack = newStack;
	}

	int[] parseParameterModeOpCode(int opcode) {
		int[] ret = new int[5];
		ret[0] = opcode % 100;
		opcode /= 100;
		for (int i = 1; i < ret.length; i++) {
			ret[i] = opcode % 10;
			if (ret[i] != 0 && ret[i] != 1 && ret[i] != 2) {
				System.err.println("Error: parseParameterModeOpCode(" + opcode + "): wrong Opcode");
			}
			opcode /= 10;
		}
		return ret;
	}

	long getInput() {
		return inputOpCode3[inputPointer++];
	}

	public static long[] parseLineToStack(String line) {
		String[] arr = line.split(",");
		long[] stack = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			stack[i] = Long.valueOf(arr[i]);
		}
		return stack;
	}

	public String stateStr() {
		if (state == _STATE_HALT) {
			return "_STATE_HALT";
		} else
			return _STATE_STR[state];
	}
}