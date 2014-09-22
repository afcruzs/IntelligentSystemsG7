package unalcol.agents.examples.rubik.grupo7;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

public class RubikCube implements Comparable<RubikCube> {

	private byte cube[][][];
	public static final byte UP = 0; //Amarillo
	public static final byte FRONT = 1; // Azul
	public static final byte RIGHT = 2; // Rojo
	public static final byte BACK = 3; // verde
	public static final byte LEFT = 4; // naranja
	public static final byte DOWN = 5; // blanco
	
	public static final byte YELLOW = 0;
	public static final byte BLUE = 1;
	public static final byte RED = 2;
	public static final byte GREEN = 3;
	public static final byte ORANGE = 4;
	public static final byte WHITE = 5;
	
	public static final byte TOP = 0;
	public static final byte MIDDLE = 1;
	public static final byte BOTTOM = 2;
	
	public RubikCube(byte[][][] cube) {
		this.cube = cube;
	}
	
	public RubikCube(){
		this.cube = new byte[6][3][3]; 
	}
	
	public List<Block> getBlocks(){
		LinkedList<Block> blocks = new LinkedList<>();
		byte NO_COLOR = Block.NO_COLOR;
		
		//UP BlOCKS
		blocks.add( new Block(blocks.size(), cube[UP][TOP][0], NO_COLOR, NO_COLOR, cube[BACK][TOP][2], cube[LEFT][TOP][0], NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][TOP][1], NO_COLOR, NO_COLOR, cube[BACK][TOP][1], NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][TOP][2], NO_COLOR, cube[RIGHT][TOP][2], cube[BACK][TOP][0], NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][MIDDLE][0], NO_COLOR, NO_COLOR, NO_COLOR, cube[LEFT][TOP][1], NO_COLOR) );
			
		blocks.add( new Block(blocks.size(), cube[UP][MIDDLE][2], NO_COLOR, cube[RIGHT][TOP][1], NO_COLOR, NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][BOTTOM][0], cube[FRONT][TOP][0], NO_COLOR, NO_COLOR, cube[LEFT][TOP][2], NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][BOTTOM][1], cube[FRONT][TOP][1], NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), cube[UP][BOTTOM][2], cube[FRONT][TOP][2], cube[RIGHT][TOP][0], NO_COLOR, NO_COLOR, NO_COLOR) );
		
		//FRONT BLOCKS
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][MIDDLE][0], NO_COLOR, NO_COLOR, cube[LEFT][MIDDLE][2], NO_COLOR) );
				
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][MIDDLE][2], cube[RIGHT][MIDDLE][0], NO_COLOR, NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][BOTTOM][0], NO_COLOR, NO_COLOR, cube[LEFT][BOTTOM][2], cube[DOWN][TOP][0]) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][BOTTOM][1], NO_COLOR, NO_COLOR, NO_COLOR, cube[DOWN][TOP][1]) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][BOTTOM][2], cube[RIGHT][BOTTOM][0], NO_COLOR, NO_COLOR, cube[DOWN][TOP][1] ) );
		
		//RIGHT BLOCKS
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[RIGHT][MIDDLE][2], cube[BACK][MIDDLE][0], NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[RIGHT][BOTTOM][1], NO_COLOR, NO_COLOR, cube[DOWN][MIDDLE][2]) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[RIGHT][BOTTOM][2], cube[BACK][BOTTOM][0], NO_COLOR, cube[DOWN][BOTTOM][2]) );
		
		//BACK BLOCKS
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[BACK][MIDDLE][2], cube[LEFT][MIDDLE][0], NO_COLOR, NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[BACK][BOTTOM][1], NO_COLOR, NO_COLOR, cube[DOWN][BOTTOM][1] ) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, cube[BACK][BOTTOM][2], cube[LEFT][BOTTOM][0], NO_COLOR, cube[DOWN][BOTTOM][0] ) );
		
		//lEFT BLOCKS
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][MIDDLE][0], NO_COLOR, NO_COLOR, cube[LEFT][MIDDLE][2], NO_COLOR) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR, cube[LEFT][BOTTOM][1], cube[DOWN][MIDDLE][0] ) );
		
		blocks.add( new Block(blocks.size(), NO_COLOR, cube[FRONT][BOTTOM][0], NO_COLOR, NO_COLOR, cube[LEFT][BOTTOM][2], cube[DOWN][TOP][0]) );
		
		//DOWN BLOCKS (There is no down blocks, because the other faces already covered them :)

		
		return blocks;
	}
	
	private void rowToCol( byte row[], byte face, byte col, 
			byte clone[][][], boolean inverse ){
		if( !inverse )
			for (byte i = 0; i < row.length; i++) 
				clone[face][i][col] = row[i];
		else
			for (byte i = 0; i < row.length; i++) 
				clone[face][i][col] = row[2-i];
	}
	
	private void colToRow( byte matrix[][], byte col, 
			byte cloneRow[], boolean inverse ){
		if( !inverse )
			for (byte i = 0; i < matrix.length; i++) 
				cloneRow[i] = matrix[i][col];
		else
			for (byte i = 0; i < matrix.length; i++) 
				cloneRow[i] = matrix[2-i][col];
	}
	

	
	private void colToCol( byte matrix[][], byte col1, 
			byte clone[][], byte col2, boolean inverse ){
		if( !inverse )
			for (byte i = 0; i < matrix.length; i++) 
				clone[i][col2] = matrix[i][col1];
		else
			for (byte i = 0; i < matrix.length; i++) 
				clone[i][col2] = matrix[2-i][col1];
	}
	
	
	
	private byte[][][] copyMultiDimensionalArray( byte[][][] source ){
		byte [][][] goal = new byte[source.length][source[0].length][source[0][0].length];
		for (byte i = 0; i < goal.length; i++) {
			for (byte j = 0; j < goal[0].length; j++) {
				for (byte k = 0; k < goal[0].length; k++) {
					goal[i][j][k] = source[i][j][k];
				}
			}
		}
		
		return goal;
	}
	
	private void rotateFace(byte matrix[][], byte copy[][], boolean inverse){
		if(!inverse)
			for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[i][j] = matrix[2-j][i];
		else
			for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[i][j] = matrix[j][2-i];
	}
	
	public RubikCube moveCube( RubikAction ra ){
		
		byte [] frontCopy;
		byte [] leftCopy;
		byte [] backCopy;
		byte [] rightCopy;
		byte [] upCopy;
		byte [] downCopy;
		byte copy [][][]= copyMultiDimensionalArray(cube); 
		
		
		
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
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++){ 
					copy[UP][i][j] = cube[UP][2-j][i];
				}*/
			rotateFace(cube[UP], copy[UP], false);
			
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
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[UP][i][j] = cube[UP][j][2-i];*/
			
			rotateFace(cube[UP], copy[UP], true);
			
			return new RubikCube(copy);
		
		case RubikAction.FRONT:
			
			rowToCol(cube[UP][BOTTOM], RIGHT, (byte) 0, copy, false);
			rowToCol(cube[DOWN][TOP], LEFT, (byte) 2, copy, false);
			colToRow(cube[LEFT], (byte) 2, copy[UP][BOTTOM], true);
			colToRow(cube[RIGHT], (byte) 0, copy[DOWN][TOP], true);
		
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[FRONT][i][j] = cube[FRONT][2-j][i];*/
			
			
			rotateFace(cube[FRONT], copy[FRONT], false);
			
			return new RubikCube(copy);
			
		case RubikAction.FRONT_INVERSE:
			
			rowToCol(cube[UP][BOTTOM], LEFT, (byte) 2, copy, true);
			rowToCol(cube[DOWN][TOP], RIGHT, (byte) 0, copy, true);
			colToRow(cube[LEFT], (byte) 2, copy[DOWN][TOP], false);
			colToRow(cube[RIGHT], (byte) 0, copy[UP][BOTTOM], false);
		
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[FRONT][i][j] = cube[FRONT][j][2-i];*/
			
			rotateFace(cube[FRONT], copy[FRONT], true);
			
			return new RubikCube(copy);
			
		case RubikAction.RIGHT:	
			colToCol(cube[FRONT], (byte)2, copy[UP], (byte)2, false);
			colToCol(cube[UP], (byte)2, copy[BACK], (byte)0, true);
			colToCol(cube[BACK], (byte)0, copy[DOWN], (byte)2, true);
			colToCol(cube[DOWN], (byte)2, copy[FRONT], (byte)2, false);
			
			rotateFace(cube[RIGHT], copy[RIGHT], false);
			
			return new RubikCube(copy);
			
		case RubikAction.RIGHT_INVERSE:
			

			colToCol(cube[UP], (byte)2, copy[FRONT], (byte)2, false);
			colToCol(cube[FRONT], (byte)2, copy[DOWN], (byte)2, false);
			colToCol(cube[DOWN], (byte)2, copy[BACK], (byte)0, true);
			colToCol(cube[BACK], (byte)0, copy[UP], (byte)2, true);
			
			rotateFace(cube[RIGHT], copy[RIGHT], true);
			return new RubikCube(copy);
		
		case RubikAction.BACK:
			
			rowToCol(cube[UP][TOP], LEFT, (byte)0, copy, true);
			rowToCol(cube[DOWN][BOTTOM], RIGHT, (byte)2, copy, true);
			colToRow(cube[LEFT], (byte)0, copy[DOWN][BOTTOM], false);
			colToRow(cube[RIGHT], (byte)2, copy[UP][TOP], false);
		
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[BACK][i][j] = cube[BACK][2-j][i];*/
			
			rotateFace(cube[BACK], copy[BACK], false);
			
			return new RubikCube(copy);
			
		case RubikAction.BACK_INVERSE:
			
			rowToCol(cube[UP][TOP], RIGHT, (byte)2, copy, false);
			rowToCol(cube[DOWN][BOTTOM], LEFT, (byte)0, copy, false);
			colToRow(cube[LEFT], (byte)0, copy[UP][TOP], true);
			colToRow(cube[RIGHT], (byte)2, copy[DOWN][BOTTOM], true);
		
			
			/*for (byte i = 0; i < 3; i++) 
				for (byte j = 0; j < 3; j++) 
					copy[BACK][i][j] = cube[BACK][j][2-i];*/
			
			rotateFace(cube[BACK], copy[BACK], true);
			
			return new RubikCube(copy);
			
		case RubikAction.LEFT:
			

			colToCol(cube[UP], (byte)0, copy[FRONT], (byte)0, false);
			colToCol(cube[FRONT], (byte)0, copy[DOWN], (byte)0, false);
			colToCol(cube[DOWN], (byte)0, copy[BACK], (byte)2, true);
			colToCol(cube[BACK], (byte)2, copy[UP], (byte)0, true);
			
			rotateFace(cube[LEFT], copy[LEFT], false);
			
			return new RubikCube(copy);
			
		case RubikAction.LEFT_INVERSE:
			colToCol(cube[FRONT], (byte)0, copy[UP], (byte)0, false);
			colToCol(cube[UP], (byte)0, copy[BACK], (byte)2, true);
			colToCol(cube[BACK], (byte)2, copy[DOWN], (byte)0, true);
			colToCol(cube[DOWN], (byte)0, copy[FRONT], (byte)0, false);
			
			rotateFace(cube[LEFT], copy[LEFT], true);
			
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
			
			
			rotateFace(cube[DOWN], copy[DOWN], false);
			
			return new RubikCube(copy);
			
			

		case RubikAction.DOWN_INVERSE: //RubikAction.DOWN_INVERSE
			
			frontCopy = Arrays.copyOf(cube[FRONT][BOTTOM], cube[FRONT][BOTTOM].length);
			leftCopy = Arrays.copyOf(cube[LEFT][BOTTOM], cube[LEFT][BOTTOM].length);
			backCopy = Arrays.copyOf(cube[BACK][BOTTOM], cube[BACK][BOTTOM].length);
			rightCopy = Arrays.copyOf(cube[RIGHT][BOTTOM], cube[RIGHT][BOTTOM].length);
			
			copy[FRONT][BOTTOM] = rightCopy;
			copy[LEFT][BOTTOM] = frontCopy;
			copy[BACK][BOTTOM] = leftCopy;
			copy[RIGHT][BOTTOM] = backCopy;

			
			rotateFace(cube[DOWN], copy[DOWN], true);
			
			return new RubikCube(copy);
		
			default:
				throw new IllegalArgumentException();
			
		}
	}
	
	
	public String toString(){
		StringBuilder out = new StringBuilder();
		
		byte currentFace = UP;
		out.append("Up\n");
		for (byte i = 0; i < cube[currentFace].length; i++) {
			for (byte j = 0; j < cube[currentFace][i].length; j++) {
				out.append( cube[currentFace][i][j] + " " );
			}
			out.append("\n");
		}
		out.append("\n");
		int n = cube[currentFace].length;
		out.append("Front    Right    Back     Left\n"  );
		for (byte i = 0; i < n; i++) {
			for ( byte j = 0; currentFace != DOWN; ) {
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
		out.append("Down\n");
		currentFace = DOWN;
		for (byte i = 0; i < cube[currentFace].length; i++) {
			for (byte j = 0; j < cube[currentFace][i].length; j++) {
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

	public byte getColorCenter(byte i) {
		return cube[i][1][1];
	}

	public void setAt(byte face, byte i, byte j, byte color) {
		cube[face][i][j] = color;
	}

	public byte getAt(byte face, byte i, byte j) {
		return cube[face][i][j];
	}
	
		
	
}
