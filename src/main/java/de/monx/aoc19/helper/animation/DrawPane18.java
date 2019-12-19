package de.monx.aoc19.helper.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import de.monx.aoc19.daily_tasks.t18.T18;

public class DrawPane18 extends JPanel {
	List<char[]> grid = null;
	Set<Character> keysCollected = null;
	final int scale = 8;

	@Override
	public void paintComponent(Graphics g) {
		if (grid == null) {
			return;
		}
		for (int y = 0; y < grid.size(); y++) {
			char[] car = grid.get(y);
			for (int x = 0; x < car.length; x++) {
				char c = car[x];
				g.setColor(elementColor.get(_FREE));
				g.fillRect(x * scale + 5, y * scale + 5, scale, scale); // Draw on g here e.g.
				
				if (keysCollected.contains(c) || (T18.isGate(c) && keysCollected.contains(T18.getKeyOfGate(c)))) {
					g.setColor(Color.orange);
				} else if (elementColor.containsKey(car[x])) {
					g.setColor(elementColor.get(car[x]));
				} else {
					g.setColor(Color.white);
				}

				if (T18.isKey(c)) {
					g.fillOval(x * scale + 5, y * scale + 5, scale, scale);
				} else {
					g.fillRect(x * scale + 5, y * scale + 5, scale, scale); // Draw on g here e.g.
				}
			}
		}

	}

	public void drawGrid(List<char[]> grid, Set<Character> keysCollected) {
		this.grid = grid;
		this.keysCollected = keysCollected;
		revalidate();
		repaint();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	final static char _WALL = '#';
	final static char _FREE = '.';
	final static char _ENTRANCE = '@';
	final static char _CURRENTPOS = '$';
	final static char _a = 'a';
	final static char _A = 'A';
	final static char _b = 'b';
	final static char _B = 'B';
	final static char _c = 'c';
	final static char _C = 'C';
	final static char _d = 'd';
	final static char _D = 'D';
	final static char _e = 'e';
	final static char _E = 'E';
	final static char _f = 'f';
	final static char _F = 'F';
	final static char _g = 'g';
	final static char _G = 'G';
	final static char _h = 'h';
	final static char _H = 'H';
	final static char _i = 'i';
	final static char _I = 'I';
	final static char _j = 'j';
	final static char _J = 'J';
	final static char _k = 'k';
	final static char _K = 'K';
	final static char _l = 'l';
	final static char _L = 'L';
	final static char _m = 'm';
	final static char _M = 'M';
	final static char _n = 'n';
	final static char _N = 'N';
	final static char _o = 'o';
	final static char _O = 'O';
	final static char _p = 'p';
	final static char _P = 'P';
	final static char _q = 'q';
	final static char _Q = 'Q';
	final static char _r = 'r';
	final static char _R = 'R';
	final static char _s = 's';
	final static char _S = 'S';
	final static char _t = 't';
	final static char _T = 'T';
	final static char _u = 'u';
	final static char _U = 'U';
	final static char _v = 'v';
	final static char _V = 'V';
	final static char _w = 'w';
	final static char _W = 'W';
	final static char _x = 'x';
	final static char _X = 'X';
	final static char _y = 'y';
	final static char _Y = 'Y';
	final static char _z = 'z';
	final static char _Z = 'Z';
	static Map<Character, Color> elementColor;
	static {
		elementColor = new HashMap<>();
		elementColor.put(_WALL, Color.black);
		elementColor.put(_FREE, Color.lightGray);
		elementColor.put(_ENTRANCE, Color.CYAN);
		elementColor.put(_CURRENTPOS, Color.GREEN);

		elementColor.put(_a, new Color(100, 20, 40));
		elementColor.put(_A, new Color(100, 20, 40));
		elementColor.put(_b, new Color(120, 20, 40));
		elementColor.put(_B, new Color(120, 20, 40));
		elementColor.put(_c, new Color(140, 20, 40));
		elementColor.put(_C, new Color(140, 20, 40));
		elementColor.put(_d, new Color(160, 20, 40));
		elementColor.put(_D, new Color(160, 20, 40));
		elementColor.put(_e, new Color(180, 20, 40));
		elementColor.put(_E, new Color(180, 20, 40));
		elementColor.put(_f, new Color(200, 20, 40));
		elementColor.put(_F, new Color(200, 20, 40));
		elementColor.put(_g, new Color(120, 0, 80));
		elementColor.put(_G, new Color(120, 0, 80));
		elementColor.put(_h, new Color(140, 0, 80));
		elementColor.put(_H, new Color(140, 0, 80));
		elementColor.put(_i, new Color(160, 0, 80));
		elementColor.put(_I, new Color(160, 0, 80));
		elementColor.put(_j, new Color(180, 0, 80));
		elementColor.put(_J, new Color(180, 0, 80));
		elementColor.put(_k, new Color(200, 0, 80));
		elementColor.put(_K, new Color(200, 0, 80));
		elementColor.put(_l, new Color(120, 60, 100));
		elementColor.put(_L, new Color(120, 60, 100));
		elementColor.put(_m, new Color(140, 60, 100));
		elementColor.put(_M, new Color(140, 60, 100));
		elementColor.put(_n, new Color(160, 60, 100));
		elementColor.put(_N, new Color(160, 60, 100));
		elementColor.put(_o, new Color(180, 60, 100));
		elementColor.put(_O, new Color(180, 60, 100));
		elementColor.put(_p, new Color(200, 60, 100));
		elementColor.put(_P, new Color(200, 60, 100));
		elementColor.put(_q, new Color(120, 0, 140));
		elementColor.put(_Q, new Color(120, 0, 140));
		elementColor.put(_r, new Color(140, 0, 140));
		elementColor.put(_R, new Color(140, 0, 140));
		elementColor.put(_s, new Color(160, 0, 140));
		elementColor.put(_S, new Color(160, 0, 140));
		elementColor.put(_t, new Color(180, 0, 180));
		elementColor.put(_T, new Color(180, 0, 180));
		elementColor.put(_u, new Color(200, 0, 140));
		elementColor.put(_U, new Color(200, 0, 140));
		elementColor.put(_v, new Color(120, 80, 180));
		elementColor.put(_V, new Color(120, 80, 180));
		elementColor.put(_w, new Color(140, 80, 180));
		elementColor.put(_W, new Color(140, 80, 180));
		elementColor.put(_x, new Color(160, 80, 180));
		elementColor.put(_X, new Color(160, 80, 180));
		elementColor.put(_y, new Color(180, 80, 180));
		elementColor.put(_Y, new Color(180, 80, 180));
		elementColor.put(_z, new Color(200, 80, 180));
		elementColor.put(_Z, new Color(200, 80, 180));

	}

}
