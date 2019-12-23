package de.monx.aoc19.daily_tasks.t23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.helper.Vec3L;
import de.monx.aoc19.helper.intcode.IntCode;

public class T23 extends TDay {

	@Override
	public TDay exec() {
		long[] in = getInput();
		execBoth(in);
		return this;
	}

	void execBoth(long[] stack) {
		IntCode[] nic = initNIC(stack, 50);
		boolean received = false;
		List<Vec3L> queue = new ArrayList<>();
		Vec3L nat = null;
		Set<Long> mem = new HashSet<>();
		while (!received) {
			if (queue.isEmpty()) {
				idleFetch(nic, queue);
				if (nat != null) {
					if (mem.contains(nat.y)) {
						System.out.println("Part2: " + nat.y);
						return;
					} else {
						mem.add(nat.y);
						sendAndUpdateQueue(nic, queue, nat.add(new Vec3L(0, 0, -255)));
					}
				}
			}
			if (queue.isEmpty()) {
				break;
			}
			Vec3L v = queue.get(0);
			queue.remove(0);
			if (v.z == 255) {
				if (nat == null) {
					System.out.println("Part1: " + v.y);
				}
				nat = v;
				continue;
			}
			sendAndUpdateQueue(nic, queue, v);
		}
		System.err.println("Couldn't find the right value!");
	}

	private List<Long> sendAndUpdateQueue(IntCode[] nic, List<Vec3L> queue, Vec3L v) {
		List<Long> out = sendPacket(nic, v);
		if (!out.isEmpty()) {
			fillQueue(out, queue);
		}
		return out;
	}

	void fillQueue(List<Long> out, List<Vec3L> queue) {
		for (int i = 0; i < out.size(); i += 3) {
			long des = out.get(i);
			long x = out.get(i + 1);
			long y = out.get(i + 2);
			// destination is written in z
			// so x and y can be entered into x and y
			Vec3L v = new Vec3L(x, y, des);
			queue.add(v);
		}
	}

	List<Long> sendPacket(IntCode[] nic, Vec3L v) {
		nic[(int) v.z].setInput(v.x);
		nic[(int) v.z].execIO();
		nic[(int) v.z].setInput(v.y);
		nic[(int) v.z].execIO();
		return nic[(int) v.z].getAndResetOutput();
	}

	void idleFetch(IntCode[] nic, List<Vec3L> queue) {
		for (int i = 0; i < nic.length; i++) {
			nic[i].setInput(-1);
			nic[i].execIO();
			List<Long> out = nic[i].getAndResetOutput();
			if (out.isEmpty()) {
				continue;
			}
			fillQueue(out, queue);
			break;
		}
	}

	IntCode[] initNIC(long[] stack, int amt) {
		IntCode[] nic = new IntCode[50];
		for (int i = 0; i < nic.length; i++) {
			nic[i] = new IntCode();
			nic[i].setStack(stack.clone());
			nic[i].execIO();
			nic[i].setInput(i);
			nic[i].execIO();
		}
		return nic;
	}

	void printIC_OUT(List<Long> out, int id) {
		if (out.isEmpty()) {
			return;
		}
		System.out.println("nic[" + id + "]\t" + Arrays.toString(out.toArray()));
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
