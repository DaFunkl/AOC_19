package de.monx.aoc19.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JFrame {
	public DrawPane pane = new DrawPane();

	public Animation() {
		super();
	}

	public Animation(int w, int h) {
		super("AOC_19");
		setSize(w, h);
		setContentPane(pane);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		setBackground(Color.black);
		setTitle("AOC_19");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public class DrawPane extends JPanel {
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
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
