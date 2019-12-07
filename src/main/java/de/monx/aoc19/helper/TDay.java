package de.monx.aoc19.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

public class TDay {
	@Getter @Setter  private String path = "";

	public TDay init(String path, String day) {
		this.path = path + day + "/";
		System.out.println("Init: " + day);
		return this;
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
