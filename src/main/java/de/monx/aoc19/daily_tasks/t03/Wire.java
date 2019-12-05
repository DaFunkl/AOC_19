package de.monx.aoc19.daily_tasks.t03;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc19.helper.Vec2;
import lombok.Getter;

@Getter
public class Wire {
	List<Vec2> coords = new ArrayList<>();
	Boundry boundry = new Boundry();

	public Wire(String in) {
		init(in);
	}

	private void init(String in) {
		String[] sArr = in.split(",");
		Vec2 cVec = new Vec2(0, 0);
		coords.add(cVec);
		for (String s : sArr) {
			cVec = cVec.add(parseDir(s));
			boundry.refresh(cVec);
			coords.add(cVec);
		}
	}

	private Vec2 parseDir(String s) {
		char dir = s.charAt(0);
		int val = parseDirVal(s);
		switch (dir) {
		case 'U':
			return new Vec2(0, val);
		case 'D':
			return new Vec2(0, -val);
		case 'R':
			return new Vec2(val, 0);
		case 'L':
			return new Vec2(-val, 0);
		default:
			System.err.println("Invalid Input: " + s);
		}
		return new Vec2(0, val);
	}

	private int parseDirVal(String s) {
		return Integer.valueOf(s.substring(1));
	}


}
