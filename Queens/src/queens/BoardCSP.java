package queens;

import java.util.ArrayList;

public class BoardCSP extends Board {
	
	private ArrayList<ArrayList<Integer>> possibleValues;

	public BoardCSP(int size, boolean doRandom) {
		super(size, doRandom);
		possibleValues = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			possibleValues.add(new ArrayList<Integer>());
		}
		queens = new int[]{ 3,0,1,2 };
		
		ArrayList<Integer> colOptions ; 
			
		for (int x = 0; x < size; x++) {
			colOptions = new ArrayList<>();
			for (int y = 0; y < size; y++) {
				for(int i = x+1; i<size ;i++){
					if ( y != queens[i] && 
							Math.abs( y - queens[i] ) != Math.abs( x - i ) ){
						System.out.println("y:"+y+ " q[i]:"+queens[i]+ " x:"+x+" i:"+i);
						colOptions.add(y);
					}
				}
			}
			possibleValues.add(colOptions);
		}
		
		
		/*for (int x = 0; x < size; x++) {
			int y = queens[x];
			for( int i=0; i < size; i++ ){
				if( x == i ) continue;
				
			}
		}*/
		
	}
	
	public static void main(String[] args) {
		BoardCSP test = new BoardCSP(4, false);
		test.queens = new int[]{ 3,0,2,1 };
		System.out.println(test);
		System.out.println(test.possibleValues);
	}

}
