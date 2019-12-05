package de.monx.aoc19;

import de.monx.aoc19.daily_tasks.t01.T01;
import de.monx.aoc19.daily_tasks.t02.T02;
import de.monx.aoc19.daily_tasks.t03.T03;
import de.monx.aoc19.daily_tasks.t04.T04;
import de.monx.aoc19.daily_tasks.t05.T05;
import de.monx.aoc19.helper.TDay;

public class App {
	final static String path = "Input/";
	static int currentDay = 5;

	public static void main(String[] args) {
//		executeDay();
		testInMiliSec();
	}

	static void executeDay() {
		String day = "" + currentDay;
		if (currentDay < 10) {
			day = "0" + day;
		}
		String inputFile = "input";

		execDay(day, inputFile);
	}
	
	static void testInMiliSec() {
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 5; i++) {

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
			tDay = new T05(path, day);
			break;
		default:
			System.err.println("Invalid Day");
			return;
		}
		tDay.inputFile(inputFile).exec().clean();
	}
}
