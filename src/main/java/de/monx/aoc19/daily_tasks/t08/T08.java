package de.monx.aoc19.daily_tasks.t08;

import java.util.Iterator;

import de.monx.aoc19.helper.TDay;

public class T08 extends TDay {
	int width = 0;
	int height = 0;
	int layerSize = 0;

	public T08 setWT(int w, int t) {
		width = w;
		height = t;
		layerSize = w * t;
		return this;
	}

	@Override
	public TDay exec() {
		Iterator<String> it = stream.iterator();
		String line = it.next();
		System.out.println("Part1: " + part1(line));
		System.out.println("Part2:");
		int[][] arr = part2(line);
		printPart2(arr);
		return this;
	}

	int[][] initIntArrays(int in) {
		int[][] ret = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ret[y][x] = in;
			}
		}
		return ret;
	}

	void printPart2(int[][] arr) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
//				System.out.print(arr[y][x]);
				if(arr[y][x] == 1) {
					System.out.print("X");
				}
				if(arr[y][x] == 0) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	int[][] part2(String line) {
		int[][] ret = initIntArrays(-1);
		char[] arr = line.toCharArray();
		for (int i = 0; i < arr.length; i += layerSize) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (ret[y][x] == -1 || ret[y][x] == 2) {
						ret[y][x] = intChar2Int(arr[i + (y * width) + x]);
					}
				}
			}
		}
		return ret;
	}

	int part1(String line) {
		char[] arr = line.toCharArray();
		int ret = 0;
		int fewestZeros = layerSize + 1;
		for (int i = 0; i < arr.length; i += layerSize) {
			int oneDigits = 0;
			int twoDigits = 0;
			int zeroCount = 0;
			for (int j = 0; j < layerSize; j++) {
				switch (arr[i + j]) {
				case '0':
					zeroCount++;
					break;
				case '1':
					oneDigits++;
					break;
				case '2':
					twoDigits++;
					break;
				default:
					break;
				}
			}
			int prod = oneDigits * twoDigits;
			if (zeroCount < fewestZeros) {
				fewestZeros = zeroCount;
				ret = prod;
			}
		}
		return ret;
	}

	int intChar2Int(char c) {
		return (int) c - (int) '0';
	}
}
