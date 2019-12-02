package de.monx.aoc19;

import de.monx.aoc19.t01.T01;
import de.monx.aoc19.t02.T02;

public class App {
	final static String path = "Input/";
	final static int _P1 = 0;
	final static int _P2 = 1;
	final static int _PBoth = 2;
	
	
	public static void main(String[] args) {
		String day = "02";
		String inputFile = "input";
		
		execDay(day, inputFile, _PBoth);
	}

	static void execDay(String day, String inputFile, int part) {
		switch (day) {
		case "01":
			new T01(path, day).inputFile(inputFile).exec().clean();
			break;
		case "02":
			new T02(path, day).inputFile(inputFile).exec().clean();
			break;
		default:
			break;
		}
	}
}
