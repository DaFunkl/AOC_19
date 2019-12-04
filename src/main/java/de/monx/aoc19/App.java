package de.monx.aoc19;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.t01.T01;
import de.monx.aoc19.t02.T02;
import de.monx.aoc19.t03.T03;
import de.monx.aoc19.t04.T04;

public class App {
	final static String path = "Input/";
	
	
	public static void main(String[] args) {
		String day = "04";
		String inputFile = "input";
		
		execDay(day, inputFile);
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
		default:
			System.err.println("Invalid Day");
			return;
		}
		tDay.inputFile(inputFile).exec().clean();
	}
}
