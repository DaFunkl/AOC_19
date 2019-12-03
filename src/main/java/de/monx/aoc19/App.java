package de.monx.aoc19;

import de.monx.aoc19.helper.TDay;
import de.monx.aoc19.t01.T01;
import de.monx.aoc19.t02.T02;
import de.monx.aoc19.t03.T03;

public class App {
	final static String path = "Input/";
	final static int _P1 = 0;
	final static int _P2 = 1;
	final static int _PBoth = 2;
	
	
	public static void main(String[] args) {
		String day = "03";
		String inputFile = "p1ex1";
//		String inputFile = "input";
		
		execDay(day, inputFile, _PBoth);
	}

	static void execDay(String day, String inputFile, int part) {
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
		default:
			System.err.println("Invalid Day");
			return;
		}
		tDay.inputFile(inputFile).exec().clean();
	}
}
