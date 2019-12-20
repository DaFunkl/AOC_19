package de.monx.aoc19.helper.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JFrame {
	public JPanel pane;

	public Animation() {
		super();
	}

	public Animation(int w, int h, int day) {
		super("AOC_19");
		if (day == 18) {
			pane = new DrawPane18();
		} else if (day == 20) {
			pane = new DrawPane20();
		} else {
			pane = new DrawPane15();
		}
		setSize(w, h);
		setContentPane(pane);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		setBackground(Color.darkGray);
		setTitle("AOC_19");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
