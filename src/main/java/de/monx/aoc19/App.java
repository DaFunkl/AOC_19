package de.monx.aoc19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc19.daily_tasks.t01.T01;
import de.monx.aoc19.daily_tasks.t02.T02;
import de.monx.aoc19.daily_tasks.t03.T03;
import de.monx.aoc19.daily_tasks.t04.T04;
import de.monx.aoc19.daily_tasks.t05.T05;
import de.monx.aoc19.daily_tasks.t06.T06;
import de.monx.aoc19.daily_tasks.t07.T07;
import de.monx.aoc19.daily_tasks.t08.T08;
import de.monx.aoc19.daily_tasks.t09.T09;
import de.monx.aoc19.daily_tasks.t10.T10;
import de.monx.aoc19.daily_tasks.t11.T11;
import de.monx.aoc19.daily_tasks.t12.T12;
import de.monx.aoc19.daily_tasks.t13.T13;
import de.monx.aoc19.daily_tasks.t14.T14;
import de.monx.aoc19.daily_tasks.t15.T15;
import de.monx.aoc19.daily_tasks.t16.T16;
import de.monx.aoc19.daily_tasks.t17.T17;
import de.monx.aoc19.daily_tasks.t18.T18;
import de.monx.aoc19.daily_tasks.t19.T19;
import de.monx.aoc19.helper.TDay;

public class App {
	final static String path = "Input/";
	static String input = "input";
	static int currentDay = 19;

	public static void main(String[] args) {
//		executeDay();
		testInMiliSec(currentDay, currentDay, 1);
//		execWithArgs(args);
	}

	static void execWithArgs(String[] args) {
		try {
			if (args.length != 0) {
				if (args[0].equals("-t")) {
					int startDay = Integer.parseInt(args[1]);
					int endDay = Integer.parseInt(args[2]);
					int times = Integer.parseInt(args[3]);
					testInMiliSec(startDay, endDay, times);
					return;
				} else if (args[0].equals("-e")) {
					int day = Integer.parseInt(args[1]);
					currentDay = day;
					executeDay();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printHelp();
	}

	static void executeDay() {
		String day = "" + currentDay;
		if (currentDay < 10) {
			day = "0" + day;
		}
		String inputFile = input;

		execDay(day, inputFile);
	}

	static void testInMiliSec(int fromDay, int toDay, int amt) {
		Map<Integer, long[]> runs = new HashMap<>();
		for (int i = 0; i < amt; i++) {
			Map<Integer, Long> run = testInMiliSec(fromDay, toDay);
			for (int key : run.keySet()) {
				if (i == 0) {
					long[] time = new long[amt];
					time[0] = run.get(key);
					runs.put(key, time);
				} else {
					runs.get(key)[i] = run.get(key);
				}
			}
		}
		for (int key : runs.keySet()) {
			long[] times = runs.get(key);
			System.out.println("Day: " + key + " avg: " + average(times) + " -> " + Arrays.toString(times));
		}
	}

	static long average(long[] times) {
		long sum = 0;
		for (int i = 0; i < times.length; i++) {
			sum += times[i];
		}
		return sum / times.length;
	}

	static Map<Integer, Long> testInMiliSec(int fromDay, int toDay) {
		Map<Integer, Long> ret = new HashMap<>();
		for (int i = fromDay; i <= toDay; i++) {
			long start = System.currentTimeMillis();
			String day = "" + i;
			if (i < 10) {
				day = "0" + day;
			}
			String inputFile = input;

			execDay(day, inputFile);
			long end = System.currentTimeMillis();
			long delta = end - start;
			ret.put(i, delta);
			System.out.println("--------------------> Time: " + delta + " ms");
		}
		return ret;
	}

	static void execDay(String day, String inputFile) {
		TDay tDay = fetchDay(day);
		tDay.init(path, day).inputFile(inputFile).exec().clean();
	}

	static TDay fetchDay(String day) {
		switch (day) {
		case "01":
			return new T01();
		case "02":
			return new T02();
		case "03":
			return new T03();
		case "04":
			return new T04();
		case "05":
			return new T05().setupP1P2Input(1, 5);
		case "06":
			return new T06();
		case "07":
			return new T07();
		case "08":
			return new T08().setWT(25, 6);
		case "09":
			return new T09().setInputP1(1).setInputP2(2);
		case "10":
			return new T10();
		case "11":
			return new T11();
		case "12":
			return new T12().setP1Steps(1000);
		case "13":
			return new T13();
		case "14":
			return new T14();
		case "15":
			return new T15();
		case "16":
			return new T16();
		case "17":
			return new T17();
		case "18":
			return new T18();
		case "19":
			return new T19();
		default:
			System.err.println("Invalid Day: " + day);
			return new TDay();
		}
	}

	static void printHelp() {
		System.out.println("===========================================================");
		System.out.println("AOC_19 Help:");
		System.out.println("arg[0]: { \"-e\" | \"-t\" }");
		System.out.println("\t-e: use this to execute an implementation of a single day");
		System.out.println("\t\tit takes one additional argument: day which should be executed");
		System.out.println("\t\texample: -t 8 | executes Day 8");
		System.out.println(
				"\t-t: use this to execute multiple days multiple times, as bonus it's going to print hwo long each task took to run");
		System.out.println("\t\tit takes three additional arguments: startDay, endDay, times");
		System.out.println("\t\tstartDay: which is the first day being executed");
		System.out.println("\t\tendDay: which is the last day being executed");
		System.out.println("\t\ttims: how often are these days going to be executed");
		System.out.println(
				"\t\texample: -t 1 15 5 | these arguments are going to run task 1 - 15 (including 1 and 15). Each of them 5 times.");
		System.out.println("===========================================================");

	}

}
