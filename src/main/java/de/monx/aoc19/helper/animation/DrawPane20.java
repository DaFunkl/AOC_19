package de.monx.aoc19.helper.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import de.monx.aoc19.daily_tasks.t20.T20;
import de.monx.aoc19.helper.Vec2;

public class DrawPane20 extends JPanel {
	List<char[]> grid = null;
	Set<Vec2> visited = null;
	Vec2 pos = null;
	int scale = 13;
	static Map<Character, Color> elementColor;
	static {
		elementColor = new HashMap<>();
		elementColor.put(' ', Color.black);
		elementColor.put('#', Color.darkGray);
		elementColor.put('.', Color.lightGray);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.size(); y++) {
			char[] car = grid.get(y);
			for (int x = 0; x < car.length; x++) {
				if (pos.equals(new Vec2(x, y))) {
					g.setColor(Color.GREEN);
				} else if (visited.contains(new Vec2(x, y))) {
					g.setColor(Color.blue);
				} else if (elementColor.containsKey(car[x])) {
					g.setColor(elementColor.get(car[x]));
				} else if (car[x] == 'A') {
					g.setColor(Color.cyan);
				} else if (car[x] == 'Z') {
					g.setColor(Color.green);
				} else if (T20.isCapitalLetter(car[x])) {
					g.setColor(Color.yellow);
				} else {
					g.setColor(Color.pink);
				}
				g.fillRect(x * scale + 5, y * scale + 5, scale, scale); // Draw on g here e.g.
			}
		}
	}

	public void draw(List<char[]> grid, Set<Vec2> visited, Vec2 pos) {
		this.grid = grid;
		this.pos = pos;
		this.visited = visited;
		revalidate();
		repaint();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
