package queens;

import java.util.ArrayList;
import java.util.TreeSet;

public class BoardCSP extends Board implements CSPState {
	
	//private ArrayList<ArrayList<Integer>> possibleValues;
	private VariablesSet variables;

	public BoardCSP(int size, boolean doRandom) {

		super(size, doRandom);
		
		//TODO PONER DATOS DE VARIAbLES
		initVariables(); //Esta llamada PROBABLEMENTE deba hacerse FUERA del constructuor
		//O con una variable booleana
		
		/*
		
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
		}*/
	
	}
	
	@Override
	public Variable nextVariable() {
		return new QueenVariable(nextQueenToEvaluate());
	}
	
	private void initVariables(){
		variables = new VariablesSet();
		//TODO PONER DATOS DE VARIAbLES
	}
	
	private int nextQueenToEvaluate(){
		return variables.nextVariableToChange().queenIndex;
	}
	
	class VariablesSet{
		TreeSet<QueenTile> tiles;
		public VariablesSet(){
			tiles = new TreeSet<>();
		}
		
		public void addTile( QueenTile tile ){
			tiles.add(tile);
		}
		
		public QueenTile nextVariableToChange(){
			return tiles.first();
		}
	}
	
	
	
	class QueenTile implements Comparable<QueenTile>{
		private int queenIndex, legalMoves, constraints;

		public QueenTile(int queenIndex, int legalMoves, int constraints) {
			this.queenIndex = queenIndex;
			this.legalMoves = legalMoves;
			this.constraints = constraints;
		}

		@Override
		public int compareTo(QueenTile o) {
			if( legalMoves == o.legalMoves && constraints == o.constraints ) //Multiset?
				return -1;
			if( legalMoves != o.legalMoves )
				//choose the variable with the fewest legal values
				return legalMoves - o.legalMoves;
			//Tie-breaker among MRV variables
			//choose the variable with the most constraints on remaining variables
			return o.constraints - o.constraints;
		}
		
	}
	
	class QueenVariable implements Variable {
		private int index;
		public QueenVariable(int index) {
			this.index = index;
		}
		
		@Override
		public Iterable<CSPState> getConsistentStates() {
			// TODO: Hacer los estados, aplicar Least constraining value!!!
			return null;
	    }
	}


	public static BoardCSP randomState(int size) {
		return new BoardCSP(size, true);
	}
}
