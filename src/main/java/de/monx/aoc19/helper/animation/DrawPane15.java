package de.monx.aoc19.helper.animation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPane15 extends JPanel {
	int[][] grid = null;
	final static int _WALL = 0;
	final static int _FREE = 1;
	final static int _OXYG = 2;
	final static int _BOTY = 3;
	final static int _NONO = 4;
	Color[] colors = { Color.gray, Color.white, Color.cyan, Color.green, Color.black };

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				g.setColor(colors[grid[y][x]]);
				g.fillRect(x * 10 + 5, y * 10 + 5, 10, 10); // Draw on g here e.g.
			}
		}

	}

	public void drawGrid(int[][] grid) {
		this.grid = grid;
		revalidate();
		repaint();
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}