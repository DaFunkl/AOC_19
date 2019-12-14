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
import de.monx.aoc19.helper.TDay;

public class App {
	final static String path = "Input/";
	static int currentDay = 13;

	public static void main(String[] args) {
		executeDay();
//		testInMiliSec(12, 12, 1);
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
		for(int key : runs.keySet()) {
			long[] times = runs.get(key);
			System.out.println("Day: " + key + " avg: " + average(times) + " -> " + Arrays.toString(times));
		}
	}

	static long average(long[] times) {
		long sum = 0;
		for(int i = 0; i < times.length; i++) {
			sum += times[i];
		}
		return sum/times.length;
	}
	
	static void executeDay() {
		String day = "" + currentDay;
		if (currentDay < 10) {
			day = "0" + day;
		}
		String inputFile = "input";

		execDay(day, inputFile);
	}

	static Map<Integer, Long> testInMiliSec(int fromDay, int toDay) {
		Map<Integer, Long> ret = new HashMap<>();
		for (int i = fromDay; i <= toDay; i++) {
			long start = System.currentTimeMillis();
			String day = "" + i;
			if (i < 10) {
				day = "0" + day;
			}
			String inputFile = "input";

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

		default:
			System.err.println("Invalid Day");
			return new TDay();
		}
	}
}
