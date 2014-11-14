package unalcol.agents.examples.squares.grupo7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import unalcol.agents.examples.squares.Squares;

public class Matrix {

	final static int LEFT = 0;

	static final int RIGHT = 1;

	static final int TOP = 2;

	static final int BOTTOM = 3;

	int width, height;
	Box board[][];
	int maxLines;

	List<Line> possibleLines;

	static class Line {
		int i, j, side;

		public Line(int i, int j, int side) {
			this.i = i;
			this.j = j;
			this.side = side;
		}

		public String getStringSide() {
			switch (side) {
			case LEFT:
				return Squares.LEFT;

			case RIGHT:
				return Squares.RIGHT;

			case TOP:
				return Squares.TOP;

			case BOTTOM:
				return Squares.BOTTOM;

			default:
				throw new IllegalArgumentException("Bad side!");
			}
		}
	}

	public Matrix(int w, int h) {
		width = w;
		height = h;

		board = new Box[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				board[i][j] = new Box();

		maxLines = 2 * board.length * board.length - 2 * board.length;
		possibleLines = new ArrayList<>(maxLines);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (i < board.length-1 && j < board.length-1) {
					possibleLines.add(new Line(i, j, BOTTOM));
					possibleLines.add(new Line(i, j, RIGHT));
				} else if (i < board.length-1) {
					possibleLines.add(new Line(i, j, BOTTOM));
				} else if (j < board.length-1) {
					possibleLines.add(new Line(i, j, RIGHT));
				}
			}
		}
		
		//JOptionPane.showMessageDialog(null, ""+(possibleLines.size() + "   " + maxLines));

	}

	public void addLine(int i, int j, final String side) {
		if (side.equals(Squares.LEFT)) {
			if (j > 0)
				board[i][j - 1].setRight(true);
			board[i][j].setLeft(true);
		} else if (side.equals(Squares.RIGHT)) {
			if (j < width - 1)
				board[i][j + 1].setLeft(true);
			board[i][j].setRight(true);
		} else if (side.equals(Squares.TOP)) {
			if (i > 0)
				board[i - 1][j].setBottom(true);
			board[i][j].setTop(true);
		} else {
			if (i < height - 1)
				board[i + 1][j].setTop(true);
			board[i][j].setBottom(true);
		}
	}

	public void addLine(Line line) {
		int i = line.i, j = line.j, side = line.side;

		if (side == LEFT) {
			if (j > 0)
				board[i][j - 1].setRight(true);
			board[i][j].setLeft(true);
		} else if (side == RIGHT) {
			if (j < width - 1)
				board[i][j + 1].setLeft(true);
			board[i][j].setRight(true);
		} else if (side == TOP) {
			if (i > 0)
				board[i - 1][j].setBottom(true);
			board[i][j].setTop(true);
		} else {
			if (i < height - 1)
				board[i + 1][j].setTop(true);
			board[i][j].setBottom(true);
		}
	}

	public Line getRandomLine() {
		Random r = new Random();

		while (possibleLines.size() > 0) {
			
			Line line = possibleLines.remove( r.nextInt( possibleLines.size() ) );
			if( isNotDumb(line) )
				return line;
			/*int index = r.nextInt(possibleLines.size());
			Line line = possibleLines.get(index);
			if (isNotDumb(line)) {
				possibleLines.remove(index);
				return line;
			}*/
		}

		throw new IllegalArgumentException("Se acabo la lista, criptoperrito.");

	}

	public boolean isNotDumb(Line line) {
		int i = line.i, j = line.j, side = line.side;
		Box box = board[i][j].clone();

		switch (side) {
			case RIGHT:
				if (box.right)
					return false;
	
			case LEFT:
				if (box.left)
					return false;
	
			case TOP:
				if (box.top)
					return false;
	
			case BOTTOM:
				if (box.bottom)
					return false;

		}

		Box box2 = null;

		if (side == LEFT && j > 0)
			box2 = board[i][j - 1].clone();
		else if (side == RIGHT && j < board.length - 1)
			box2 = board[i][j + 1].clone();
		else if (side == TOP && i > 0)
			box2 = board[i - 1][j].clone();
		else if (side == BOTTOM && i < board.length - 1)
			box2 = board[i + 1][j].clone();

		if (side == LEFT)
			box.setLeft(true);
		else if (side == RIGHT)
			box.setRight(true);
		else if (side == TOP)
			box.setTop(true);
		else if (side == BOTTOM)
			box.setBottom(true);

		if (box2 == null) {
			return box.turnedSides <= 2;
		} else {

			switch (line.side) {
			case RIGHT:
				if (box2.right)
					return false;

			case LEFT:
				if (box2.left)
					return false;

			case TOP:
				if (box2.top)
					return false;

			case BOTTOM:
				if (box2.bottom)
					return false;

			}

			if (side == LEFT)
				box2.setRight(true);

			if (side == RIGHT)
				box2.setLeft(true);

			if (side == TOP)
				box2.setBottom(true);

			if (side == BOTTOM)
				box2.setTop(true);

			return box.turnedSides <= 2 && box2.turnedSides <= 2;
		}
	}

}

class Box {
	boolean top, bottom, left, right;
	int turnedSides;

	public Box() {
		top = false;
		bottom = false;
		right = false;
		left = false;
		turnedSides = 0;
	}

	protected Box clone() {
		Box clone = new Box();
		clone.top = top;
		clone.bottom = bottom;
		clone.left = left;
		clone.right = right;
		clone.turnedSides = turnedSides;
		return clone;
	}

	public void setTop(boolean top) {
		if (!this.top)
			turnedSides++;
		this.top = top;
	}

	public void setBottom(boolean bottom) {
		if (!this.bottom)
			turnedSides++;
		this.bottom = bottom;
	}

	public void setLeft(boolean left) {
		if (!this.left)
			turnedSides++;
		this.left = left;
	}

	public void setRight(boolean right) {
		if (!this.right)
			turnedSides++;
		this.right = right;
	}

}