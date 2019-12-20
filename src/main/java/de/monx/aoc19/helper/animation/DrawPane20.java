package de.monx.aoc19.helper.animation;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class DrawPane20 extends JPanel {
	List<char[]> grid = null;

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
	}
}
