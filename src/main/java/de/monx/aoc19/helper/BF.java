package de.monx.aoc19.helper;

import java.util.Scanner;

public class BF {

	public static void haltInput() {
		new Scanner(System.in).nextLine();
	}
	
	public static int str2Int(String str) {
		return Integer.valueOf(str.trim());
	}
}
