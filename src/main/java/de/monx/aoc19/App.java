package de.monx.aoc19;

import de.monx.aoc19.daily_tasks.t01.T01;
import de.monx.aoc19.daily_tasks.t02.T02;
import de.monx.aoc19.daily_tasks.t03.T03;
import de.monx.aoc19.daily_tasks.t04.T04;
import de.monx.aoc19.daily_tasks.t05.T05;
import de.monx.aoc19.daily_tasks.t06.T06;
import de.monx.aoc19.daily_tasks.t07.T07;
import de.monx.aoc19.helper.TDay;

public class App {
	final static String path = "Input/";
	static int currentDay = 7;

	public static void main(String[] args) {
		executeDay();
//		testInMiliSec(6,6);
	}

	static void executeDay() {
		String day = "" + currentDay;
		if (currentDay < 10) {
			day = "0" + day;
		}
		String inputFile = "input";

		execDay(day, inputFile);
	}

	static void testInMiliSec(int fromDay, int toDay) {
		long start = System.currentTimeMillis();
		for (int i = fromDay; i <= toDay; i++) {

			String day = "0" + i;
			String inputFile = "input";

			execDay(day, inputFile);
			long end = System.currentTimeMillis();
			System.out.println("--------------------> Time: " + (end - start) + " ms");
			start = end;
		}
	}

	static void execDay(String day, String inputFile) {
		TDay tDay = fetchDay(day);
		tDay.init(path, day).inputFile(inputFile).exec().clean();
	}

	// @formatter:off
	static TDay fetchDay(String day) {
		switch (day) {
		case "01": return new T01();
		case "02": return new T02();
		case "03": return new T03();
		case "04": return new T04();
		case "05": return new T05().setupP1P2Input(1, 5);
		case "06": return new T06();
		case "07": return new T07();
		default:
			System.err.println("Invalid Day");
			return new TDay();
		}
	}
	// @formatter:on
}
