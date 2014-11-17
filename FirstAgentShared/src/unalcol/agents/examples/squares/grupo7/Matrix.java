package unalcol.agents.examples.squares.grupo7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import unalcol.agents.examples.squares.Squares;
import unalcol.agents.examples.squares.grupo7.Matrix.Line;

public class Matrix {

	final static int LEFT = 0;

	static final int RIGHT = 1;

	static final int TOP = 2;

	static final int BOTTOM = 3;

	int n;
	Box board[][];
	public List<Line> possibleLines, aux;

	public Matrix(int n) {
		int maxLines = 2 * n * n - 2 * n;
		possibleLines = new ArrayList<>(maxLines);
		this.n = n;
		board = new Box[n][n];

		initBoardOnly();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i < n - 1 && j < n - 1) {
					possibleLines.add(new Line(i, j, BOTTOM));
					possibleLines.add(new Line(i, j, RIGHT));
				} else if (i < n - 1) {
					possibleLines.add(new Line(i, j, BOTTOM));
				} else if (j < n - 1) {
					possibleLines.add(new Line(i, j, RIGHT));
				} else {
					System.out.println(i + " " + j);
				}
			}
		}
	}

	public void initBoardOnly() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				board[i][j] = new Box();

		/* Lineas del cuadro externo */
		for (int i = 0; i < board.length; i++) {
			board[0][i].setTop(true);
			board[i][0].setLeft(true);
			board[board.length - 1][i].setBottom(true);
			board[i][board.length - 1].setRight(true);
		}
	}

	public void addLine(int i, int j, String side) {
		if (isLine(i, j, side))
			return;

		if (side.equals(Squares.LEFT)) {
			if (j > 0)
				board[i][j - 1].setRight(true);
			board[i][j].setLeft(true);
		} else if (side.equals(Squares.RIGHT)) {
			if (j < n - 1)
				board[i][j + 1].setLeft(true);
			board[i][j].setRight(true);
		} else if (side.equals(Squares.TOP)) {
			if (i > 0)
				board[i - 1][j].setBottom(true);
			board[i][j].setTop(true);
		} else {
			if (i < n - 1)
				board[i + 1][j].setTop(true);
			board[i][j].setBottom(true);
		}
	}

	public boolean isLine(int i, int j, String side) {
		return board[i][j].getSide(side);
	}

	public Line getRandomLine() {
		Random r = new Random();
		// System.out.println(possibleLines);
		while (possibleLines.size() > 0) {

			Line line = possibleLines.remove(r.nextInt(possibleLines.size()));
			if (!isDumb(line)) {
				return line;
			}
		}

		return null;
		/*
		 * for (int i = 0; i <n; i++) { for (int j = 0; j <n; j++) {
		 * System.out.println( " " + i + " " + j +" "+ board[i][j] ); } }
		 * System.out.println("---"); for(Line l : aux){ System.out.println(l);
		 * } throw new
		 * IllegalArgumentException("Se acabo la lista, criptoperrito.");
		 */

	}

	private void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + "|");
			}
			System.out.println();
		}

		System.out.println();
	}

	public boolean isDumb(Line line) {
		int i = line.i, j = line.j, side = line.side;
		Box box = board[i][j];
		Box box2 = null;

		switch (side) {
		case LEFT:
			if (box.left)
				return true;

			box2 = board[i][j - 1];
			if (box2.right)
				return true;
			break;

		case RIGHT:
			if (box.right)
				return true;

			box2 = board[i][j + 1];
			if (box2.left)
				return true;
			break;

		case TOP:
			if (box.top)
				return true;

			box2 = board[i - 1][j];
			if (box2.bottom)
				return true;
			break;

		case BOTTOM:
			if (box.bottom)
				return true;

			box2 = board[i + 1][j];
			if (box2.top)
				return true;
			break;
		}

		return box.turnedSides >= 2 || box2.turnedSides >= 2;
	}

	public void addLine(Line line) {
		switch (line.side) {
		case LEFT:
			addLine(line.i, line.j, Squares.LEFT);
			break;
		case RIGHT:
			addLine(line.i, line.j, Squares.RIGHT);
			break;
		case TOP:
			addLine(line.i, line.j, Squares.TOP);
			break;
		case BOTTOM:
			addLine(line.i, line.j, Squares.BOTTOM);
			break;
		}
	}

	private void expansion ( Box board[][], int i, int j, List<Line> lines ) {
		Queue<Box> Q = new LinkedList<>();
		Q.add(board[i][j]);
		
		Stack<Line> lines1 = new Stack<>();
		Box current = board[i][j];
		while ( !Q.isEmpty() ) {
			current = Q.poll();
			
			if ( !current.bottom ) {
				if ( board[i + 1][j].turnedSides >= 2 ) {
					Q.add(board[i + 1][j]);
					lines1.push(new Line(i, j, BOTTOM));
				}
				board[i][j].setBottom(true);
				board[i + 1][j].setTop(true);
			}
			if ( !current.right ) {
				if ( board[i][j + 1].turnedSides >= 2 ) {
					Q.add(board[i][j + 1]);
					lines1.push(new Line(i, j, RIGHT));
				}
				board[i][j].setRight(true);
				board[i][j + 1].setLeft(true);
			}
			if ( !current.left ) {
				if ( board[i][j - 1].turnedSides >= 2 ) {
					Q.add(board[i][j - 1]);
					lines1.push(new Line(i, j - 1, RIGHT));
				}
				board[i][j].setLeft(true);
				board[i][j - 1].setRight(true);
			}
			if ( !current.top ) {
				if ( board[i - 1][j].turnedSides >= 2 ) {
					Q.add(board[i - 1][j]);
					lines1.push(new Line(i - 1, j, BOTTOM));
				}
				board[i][j].setTop(true);
				board[i - 1][j].setBottom(true);
			}
		}
		
		Box board2[][] = new Box[n][n];
		for ( int k = 0; k < n; k++ )
			for ( int h = 0; h < n; h++ )
				board2[k][h] = this.board[k][h].clone();
		
		Stack<Line> lines2 = new Stack<>();
		Q.add(current);
		
		while ( !Q.isEmpty() ) {
			current = Q.poll();
			
			if ( !current.bottom ) {
				if ( board2[i + 1][j].turnedSides >= 2 ) {
					Q.add(board2[i + 1][j]);
					lines2.push(lines1.pop());
				}
				board2[i][j].setBottom(true);
				board2[i + 1][j].setTop(true);
			}
			if ( !current.right ) {
				if ( board2[i][j + 1].turnedSides >= 2 ) {
					Q.add(board2[i][j + 1]);
					lines2.push(lines1.pop());
				}
				board2[i][j].setRight(true);
				board2[i][j + 1].setLeft(true);
			}
			if ( !current.left ) {
				if ( board2[i][j - 1].turnedSides >= 2 ) {
					Q.add(board2[i][j - 1]);
					lines2.push(lines1.pop());
				}
				board2[i][j].setLeft(true);
				board2[i][j - 1].setRight(true);
			}
			if ( !current.top ) {
				if ( board2[i - 1][j].turnedSides >= 2 ) {
					Q.add(board2[i - 1][j]);
					lines2.push(lines1.pop());
				}
				board2[i][j].setTop(true);
				board2[i - 1][j].setBottom(true);
			}
		}
		
		if ( !lines1.isEmpty() ) lines.add(lines1.peek());
		lines.add(lines2.peek());
	}
	
	public List<Line> evaluationLines() {
		Box tempBoard[][] = new Box[n][n];
		for ( int i = 0; i < n; i++ )
			for ( int j = 0; j < n; j++ )
				tempBoard[i][j] = board[i][j].clone();
		
		List<Line> lines = new LinkedList<>();
		
		for ( int i = 0; i < n; i++ )
			for ( int j = 0; j < n; j++ )
				if ( tempBoard[i][j].turnedSides < 3 )
					expansion( tempBoard, i, j, lines );
					
		return lines;
	}

	
	public static class Line {
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

		public String toString() {
			return i + " " + j + " " + getStringSide();
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

	public boolean getSide(String side) {
		if (side.equals(Squares.LEFT))
			return left;
		else if (side.equals(Squares.RIGHT))
			return right;
		else if (side.equals(Squares.TOP))
			return top;
		else
			/* if( side.equals(Squares.BOTTOM) ) */
			return bottom;
	}

	public String toString() {
		return top + " " + bottom + " " + left + " " + right + " "
				+ turnedSides;
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