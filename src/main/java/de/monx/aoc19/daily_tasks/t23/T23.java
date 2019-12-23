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
		System.out.println("Part1: " + part1(in));
		System.out.println("Part2: " + part2(in));
		return this;
	}

	long part2(long[] stack) {
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
						return nat.y;
					} else {
						mem.add(nat.y);
						Vec3L vc = nat.clone();
						vc.z = 0;
						List<Long> out = sendPacket(nic, vc);
						if (!out.isEmpty()) {
							fillQueue(out, queue);
						}
					}
				}
			}
			if (queue.isEmpty()) {
				break;
			}
			Vec3L v = queue.get(0);
			queue.remove(0);
			if (v.z == 255) {
				nat = v;
				continue;
			}
			List<Long> out = sendPacket(nic, v);
			if (!out.isEmpty()) {
				fillQueue(out, queue);
			}
		}
		System.err.println("Couldn't find the right value!");
		return -1;
	}

	long part1(long[] stack) {
		IntCode[] nic = initNIC(stack, 50);
		boolean received = false;
		List<Vec3L> queue = new ArrayList<>();
		while (!received) {
			if (queue.isEmpty()) {
				idleFetch(nic, queue);
			}
			if (queue.isEmpty()) {
				break;
			}
			Vec3L v = queue.get(0);
			queue.remove(0);
			if (v.z == 255) {
				return v.y;
			}
			List<Long> out = sendPacket(nic, v);
			if (!out.isEmpty()) {
				fillQueue(out, queue);
			}
		}
		System.err.println("Didn't reach 255!");
		return -1;
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
