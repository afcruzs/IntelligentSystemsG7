package unalcol.agents.examples.rubik.grupo7;

import java.util.Arrays;

public class RubikCube implements Comparable<RubikCube> {

	private int cube[][][];
	public static final int UP = 0; //Amarillo
	public static final int FRONT = 1; // Azul
	public static final int RIGHT = 2; // Rojo
	public static final int BACK = 3; // verde
	public static final int LEFT = 4; // naranja
	public static final int DOWN = 5; // blanco
	
	public static final int YELLOW = 0;
	public static final int BLUE = 1;
	public static final int RED = 2;
	public static final int GREEN = 3;
	public static final int ORANGE = 4;
	public static final int WHITE = 5;
	
	public static final int TOP = 0;
	public static final int MIDDLE = 1;
	public static final int BOTTOM = 2;
	
	
	public RubikCube(int[][][] cube) {
		this.cube = cube;
	}
	
	private void rowToCol( int row[], int face, int col, int clone[][][] ){
		for (int i = 0; i < row.length; i++) 
			clone[face][i][col] = row[i];
	}
	
	private void colToRow( int matrix[][], int col, int cloneRow[] ){
		for (int i = 0; i < matrix.length; i++) 
			cloneRow[i] = matrix[2-i][col];
	}
	
	private void colToCol( int producerMatrix[][], int producerCol, int cloneMatrix[][], int targetCol, boolean inverse){
		for (int i = 0; i < cloneMatrix.length; i++){
			if (inverse)
				cloneMatrix[i][targetCol]=producerMatrix[2-i][producerCol];
			else
				cloneMatrix[i][targetCol]=producerMatrix[i][producerCol];
		}
		
	}
	
	private int[][][] copyMultiDimensionalArray( int[][][] source ){
		int [][][] goal = new int[source.length][source[0].length][source[0][0].length];
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[0].length; j++) {
				for (int k = 0; k < goal[0].length; k++) {
					goal[i][j][k] = source[i][j][k];
				}
			}
		}
		
		return goal;
	}
	
	public RubikCube moveCube( RubikAction ra ){
		
		int [] frontCopy;
		int [] leftCopy;
		int [] backCopy;
		int [] rightCopy;
		int [] upCopy;
		int [] downCopy;
		int copy [][][]= copyMultiDimensionalArray(cube); 
		
		
		
		switch (ra.action) {
		case RubikAction.UP:
			
			frontCopy = Arrays.copyOf(cube[FRONT][TOP], cube[FRONT][TOP].length);
			leftCopy = Arrays.copyOf(cube[LEFT][TOP], cube[LEFT][TOP].length);
			backCopy = Arrays.copyOf(cube[BACK][TOP], cube[BACK][TOP].length);
			rightCopy = Arrays.copyOf(cube[RIGHT][TOP], cube[RIGHT][TOP].length);
			
			copy[FRONT][TOP] = rightCopy;
			copy[LEFT][TOP] = frontCopy;
			copy[BACK][TOP] = leftCopy;
			copy[RIGHT][TOP] = backCopy;
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++){ 
					copy[UP][i][j] = cube[UP][2-j][i];
					//System.out.println( i + " " + j + " "+ (2-j) + " "  + i );
				}
			/*System.out.println("asdsadsa");
			System.out.println(this);*/
			
			return new RubikCube(copy);
			
		case RubikAction.UP_INVERSE:
			
			frontCopy = Arrays.copyOf(cube[FRONT][TOP], cube[FRONT][TOP].length);
			leftCopy = Arrays.copyOf(cube[LEFT][TOP], cube[LEFT][TOP].length);
			backCopy = Arrays.copyOf(cube[BACK][TOP], cube[BACK][TOP].length);
			rightCopy = Arrays.copyOf(cube[RIGHT][TOP], cube[RIGHT][TOP].length);
			
			copy[FRONT][TOP] = leftCopy;
			copy[LEFT][TOP] = backCopy;
			copy[BACK][TOP] = rightCopy;
			copy[RIGHT][TOP] = frontCopy;
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[UP][i][j] = cube[UP][j][2-i];
			
			return new RubikCube(copy);
		
		case RubikAction.FRONT:
			
			rowToCol(cube[UP][BOTTOM], RIGHT, 0, copy);
			rowToCol(cube[DOWN][TOP], LEFT, 2, copy);
			colToRow(cube[LEFT], 2, copy[UP][BOTTOM]);
			colToRow(cube[RIGHT], 0, copy[DOWN][TOP]);
		
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[FRONT][i][j] = cube[FRONT][2-j][i];
			
			return new RubikCube(copy);
			
		case RubikAction.FRONT_INVERSE:
			
			rowToCol(cube[UP][BOTTOM], LEFT, 2, copy);
			rowToCol(cube[DOWN][TOP], RIGHT, 0, copy);
			colToRow(cube[LEFT], 2, copy[DOWN][TOP]);
			colToRow(cube[RIGHT], 0, copy[UP][BOTTOM]);
		
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[FRONT][i][j] = cube[FRONT][j][2-i];
			
			return new RubikCube(copy);
			
		case RubikAction.RIGHT:	
			
			colToCol(cube[UP],2,copy[BACK],0,true);
			colToCol(cube[BACK], 0, copy[DOWN], 2,true);
			colToCol(cube[DOWN],2,copy[FRONT],2,false);
			colToCol(cube[FRONT], 2, copy[UP], 2,false);
		
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[RIGHT][i][j] = cube[RIGHT][2-j][i];
			
			return new RubikCube(copy);
			
		case RubikAction.RIGHT_INVERSE:
			
			colToCol(cube[UP],2,copy[FRONT],2,false);
			colToCol(cube[FRONT], 2, copy[DOWN], 2,false);
			colToCol(cube[DOWN],2,copy[BACK],0,true);
			colToCol(cube[BACK], 0, copy[UP], 2,true);
		
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[RIGHT][i][j] = cube[RIGHT][j][2-i];
			
			return new RubikCube(copy);
		
		case RubikAction.BACK:
			
			rowToCol(cube[UP][TOP], LEFT, 0, copy);
			rowToCol(cube[DOWN][BOTTOM], RIGHT, 2, copy);
			colToRow(cube[LEFT], 0, copy[DOWN][BOTTOM]);
			colToRow(cube[RIGHT], 2, copy[UP][TOP]);
		
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[BACK][i][j] = cube[BACK][2-j][i];
			
			return new RubikCube(copy);
			
		case RubikAction.BACK_INVERSE:
			
			rowToCol(cube[UP][TOP], RIGHT, 2, copy);
			rowToCol(cube[DOWN][BOTTOM], LEFT, 0, copy);
			colToRow(cube[LEFT], 0, copy[UP][TOP]);
			colToRow(cube[RIGHT], 2, copy[DOWN][BOTTOM]);
		
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[BACK][i][j] = cube[BACK][j][2-i];
			
			return new RubikCube(copy);
			
		case RubikAction.LEFT:
			
			colToCol(cube[UP],0,copy[FRONT],0,false);
			colToCol(cube[FRONT], 0, copy[DOWN], 0,false);
			colToCol(cube[DOWN],0,copy[BACK],2,true);
			colToCol(cube[BACK], 2, copy[UP], 0,true);
		
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[LEFT][i][j] = cube[LEFT][2-j][i];
			
			return new RubikCube(copy);
			
		case RubikAction.LEFT_INVERSE:
			
			colToCol(cube[UP],0,copy[BACK],2,true);
			colToCol(cube[BACK], 2, copy[DOWN], 0,true);
			colToCol(cube[DOWN],0,copy[FRONT],0,false);
			colToCol(cube[FRONT], 0, copy[UP], 0,false);
		
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[LEFT][i][j] = cube[LEFT][j][2-i];
			
			return new RubikCube(copy);
			
		case RubikAction.DOWN:
			
			frontCopy = Arrays.copyOf(cube[FRONT][BOTTOM], cube[FRONT][BOTTOM].length);
			leftCopy = Arrays.copyOf(cube[LEFT][BOTTOM], cube[LEFT][BOTTOM].length);
			backCopy = Arrays.copyOf(cube[BACK][BOTTOM], cube[BACK][BOTTOM].length);
			rightCopy = Arrays.copyOf(cube[RIGHT][BOTTOM], cube[RIGHT][BOTTOM].length);
			
			copy[FRONT][BOTTOM] = leftCopy;
			copy[LEFT][BOTTOM] = backCopy;
			copy[BACK][BOTTOM] = rightCopy;
			copy[RIGHT][BOTTOM] = frontCopy;
			
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[DOWN][i][j] = cube[DOWN][2-j][i];
			
			return new RubikCube(copy);
			

		default: //RubikAction.DOWN_INVERSE
			
			frontCopy = Arrays.copyOf(cube[FRONT][BOTTOM], cube[FRONT][BOTTOM].length);
			leftCopy = Arrays.copyOf(cube[LEFT][BOTTOM], cube[LEFT][BOTTOM].length);
			backCopy = Arrays.copyOf(cube[BACK][BOTTOM], cube[BACK][BOTTOM].length);
			rightCopy = Arrays.copyOf(cube[RIGHT][BOTTOM], cube[RIGHT][BOTTOM].length);
			
			copy[FRONT][BOTTOM] = rightCopy;
			copy[LEFT][BOTTOM] = frontCopy;
			copy[BACK][BOTTOM] = leftCopy;
			copy[RIGHT][BOTTOM] = backCopy;

			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					copy[DOWN][i][j] = cube[DOWN][j][2-i];
			
			return new RubikCube(copy);
			
		}
	}
	
	public String toString(){
		StringBuilder out = new StringBuilder();
		
		int currentFace = UP;
		
		for (int i = 0; i < cube[currentFace].length; i++) {
			for (int j = 0; j < cube[currentFace][i].length; j++) {
				out.append( cube[currentFace][i][j] + " " );
			}
			out.append("\n");
		}
		out.append("\n");
		int n = cube[currentFace].length;
		for (int i = 0; i < n; i++) {
			
			for ( int j = 0; currentFace != DOWN; ) {
				if( j % n == 0 ){
					currentFace++;
					j = 0;
					if( currentFace != FRONT ) out.append("   ");
				}
				if( currentFace != DOWN )
					out.append( cube[currentFace][i][j] + " " );
				j++;
			}
			currentFace = UP;
			out.append("\n");
		}
		
		out.append("\n");
		
		currentFace = DOWN;
		for (int i = 0; i < cube[currentFace].length; i++) {
			for (int j = 0; j < cube[currentFace][i].length; j++) {
				out.append( cube[currentFace][i][j] + " " );
			}
			out.append("\n");
		}
		out.append("\n\n");
		
		return out.toString();
		
	}
	
	public boolean equals(Object o){
		return compareTo( (RubikCube) o ) == 0;
	}

	@Override
	public int compareTo(RubikCube a) {
		return toString().compareTo(a.toString());
	}
	
	public RubikCube clone(){
		return new RubikCube(copyMultiDimensionalArray(cube));	
	}
	
		
	
}
