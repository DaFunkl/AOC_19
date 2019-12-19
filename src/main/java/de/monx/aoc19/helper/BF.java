package de.monx.aoc19.helper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BF {

	public static void haltInput() {
		new Scanner(System.in).nextLine();
	}
	
	public static int str2Int(String str) {
		return Integer.valueOf(str.trim());
	}
	
	public static void printToFile(int[][] grid, String path) {
		File file = new File(path);
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			for(int[] g : grid) {
				for(int i : g) {
					writer.print(i+",");
				}
				writer.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
