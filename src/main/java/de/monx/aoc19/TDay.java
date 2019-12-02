package de.monx.aoc19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TDay {
	String path = "";

	public TDay(String path, String day) {
		this.path = path + day + "/";
		System.out.println("Executing: " + day);
	}

	protected Stream<String> stream;

	public TDay inputFile(String inputFileName) {
		String inpultFile = path + inputFileName;
		try {
			System.out.println("Creating a stream for: " + inpultFile);
			this.stream = Files.lines(Paths.get(inpultFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public TDay exec() {
		return this;
	}

	public void clean() {
		this.stream.close();
	}
}
