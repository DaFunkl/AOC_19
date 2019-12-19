package de.monx.aoc19.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JFrame {
	public JPanel pane;

	public Animation() {
		super();
	}

	public Animation(int w, int h, int day) {
		super("AOC_19");
		if(day == 18) {
			pane = new DrawPane18();
		} else {
			pane = new DrawPane15();
		}
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


	public class DrawPane18 extends JPanel {
		List<char[]> grid = null;
		final static char _WALL = '#';
		final static char _FREE = 'a';
		final static int _OXYG = 2;
		final static int _BOTY = 3;
		final static int _NONO = 4;
		Color[] colors = { Color.gray, Color.white, Color.cyan, Color.green, Color.black };

		@Override
		public void paintComponent(Graphics g) {
			if (grid == null) {
				return;
			}
//			for (int y = 0; y < grid.length; y++) {
//				for (int x = 0; x < grid[0].length; x++) {
//					g.setColor(colors[grid[y][x]]);
//					g.fillRect(x * 10 + 5, y * 10 + 5, 10, 10); // Draw on g here e.g.
//				}
//			}

		}

		public void drawGrid(List<char[]> grid) {
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
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
