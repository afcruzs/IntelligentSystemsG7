package unalcol.agents.examples.squares.grupo7;

import java.util.Random;

import javax.swing.JOptionPane;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;
import unalcol.agents.examples.squares.grupo7.Matrix.Line;

public class Grupo7BoxesAgent implements AgentProgram {

	Matrix matrix;
	String color;

	public Grupo7BoxesAgent(String color) {
		this.color = color;
	}

	@Override
	public Action compute(Percept p) {
		if (matrix == null) {
			matrix = new Matrix(Integer.parseInt(p.getAttribute(Squares.SIZE)
					.toString()), Integer.parseInt(p.getAttribute(Squares.SIZE)
					.toString()));
		}
		/*
		 * try{ Thread.sleep(1000); }catch(Exception e){}
		 */
		if (p.getAttribute(Squares.TURN).equals(color)) {
			for (int i = 0; i < matrix.height; i++)
				for (int j = 0; j < matrix.width; j++) {
					if (((String) p.getAttribute(i + ":" + j + ":"
							+ Squares.LEFT)).equals(Squares.TRUE))
						matrix.addLine(i, j, Squares.LEFT);
					if (((String) p.getAttribute(i + ":" + j + ":"
							+ Squares.TOP)).equals(Squares.TRUE))
						matrix.addLine(i, j, Squares.TOP);
					if (((String) p.getAttribute(i + ":" + j + ":"
							+ Squares.BOTTOM)).equals(Squares.TRUE))
						matrix.addLine(i, j, Squares.BOTTOM);
					if (((String) p.getAttribute(i + ":" + j + ":"
							+ Squares.RIGHT)).equals(Squares.TRUE))
						matrix.addLine(i, j, Squares.RIGHT);
				}
		}

		Random r = new Random();
		if (matrix.possibleLines.size() > 0) {
			Line line = matrix.getRandomLine();
			matrix.addLine(line);
			return new Action(line.i + ":" + line.j + ":"
					+ line.getStringSide());
		} else {
			JOptionPane.showMessageDialog(null, "lolol");
			// TODO: Minimax
			int i = r.nextInt(matrix.height);
			int j = r.nextInt(matrix.width);
			return new Action(i + ":" + j + ":left");

		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
