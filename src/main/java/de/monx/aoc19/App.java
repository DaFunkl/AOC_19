package de.monx.aoc19;

import de.monx.aoc19.daily_tasks.t01.T01;
import de.monx.aoc19.daily_tasks.t02.T02;
import de.monx.aoc19.daily_tasks.t03.T03;
import de.monx.aoc19.daily_tasks.t04.T04;
import de.monx.aoc19.daily_tasks.t05.T05;
import de.monx.aoc19.daily_tasks.t06.T06;
import de.monx.aoc19.helper.TDay;

public class App {
	final static String path = "Input/";
	static int currentDay = 6;

	public static void main(String[] args) {
//		executeDay();
		testInMiliSec(6,6);
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
		TDay tDay = null;
		switch (day) {
		case "01":
			tDay = new T01(path, day);
			break;
		case "02":
			tDay = new T02(path, day);
			break;
		case "03":
			tDay = new T03(path, day);
			break;
		case "04":
			tDay = new T04(path, day);
			break;
		case "05":
			tDay = new T05(path, day).setupP1P2Input(1, 5);
			break;
		case "06":
			tDay = new T06(path, day);
			break;
		default:
			System.err.println("Invalid Day");
			return;
		}
		tDay.inputFile(inputFile).exec().clean();
	}
}
