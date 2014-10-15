package queens;

import java.util.ArrayList;
import java.util.Collections;


public class BoardCSP extends Board implements CSPState {
	

	public BoardCSP(int size) {
		super(size, false);
		
	}
	
	public boolean isPerfect(){
		if( countVariables() != size )
			return false;
		computeFitness();
		return super.isPerfect();
	}
	
	private BoardCSP(){ super(); }
	
	private BoardCSP(int size,boolean f){
		super(size,f);
	}
	
	protected boolean isBitUsed(int bit){
		return queens[bit] != -1;
	}
	
	@Override
	public Iterable<Variable> unAssignedVariablesInOrder() {
		
		ArrayList<Variable> variables = new ArrayList<>(size);
		for (int x = 0; x < size; x++) {
			if( queens[x] != -1 ) continue;
			int legalValues = 0;

			for (int possibleValue = 0; possibleValue < size; possibleValue++) {
				if( containsBit(possibleValue) ) continue;
				int i;
				for (i = 0; i < size; i++) {
					if( !(queens[i] == -1 || Math.abs( x - i ) != Math.abs( possibleValue - queens[i] )) )
						break;
				}
				
				if( i >= size ) legalValues++;
			}
			
			
			variables.add(new QueenVariable(x, legalValues, countVariables()) );
		}
		Collections.sort(variables);
		return variables;
	}
	
	protected int countVariables(){
		int c = 0;
		for (int i = 0; i < size; i++) {
			if( queens[i] != -1 ) c++;
		}
		return c;
	}
	
	
	public static BoardCSP emptyState(int size){
		return new BoardCSP(size,false);
	}
	
	public static Board randomBoard(int size){
		return new BoardCSP(size, true);
	}
	
	class QueenValue implements Value{
		int row;
		int ruledOut;
		public QueenValue(int row,int ruledOut){
			this.row = row;
			this.ruledOut = ruledOut;
		}
		
		@Override
		public int compareTo(Value o) {
			return ruledOut - ((QueenValue) o).ruledOut;
		}

		@Override
		public String toString() {
			return "QueenValue [row=" + row + ", ruledOut=" + ruledOut + "]";
		}
		
		
	}
	
	class QueenVariable implements Variable{
		private int col; //Queen index
		private int legalValues;
		private int constraints;
		
		
		public QueenVariable(int col, int legalValues, int constraints) {
			this.col = col;
			this.legalValues = legalValues;
			this.constraints = constraints;
		}

		@Override
		public void assignValue(Value value) {
			QueenValue qv = (QueenValue)value;
			queens[col] = qv.row;
		}


		@Override
		public Iterable<Value> possibleValuesInOrder() {

			ArrayList<Value> values = new ArrayList<>(size);
			
			for (int possibleValue = 0; possibleValue < size; possibleValue++) {
				if( containsBit(possibleValue) ) continue;
				int i;
				for (i = 0; i < size; i++) {
					if( !(queens[i] == -1 || Math.abs( col - i ) != Math.abs( possibleValue - queens[i] )) )
						break;
				}
				if( i >= size ){
					/*int ruleOut = 0;
					i = queens[col];
					int j = col;
					ruleOut += Math.abs( i -( Math.min(i,j)-i ) ); // ( min(i,j)-i, 0 )
					ruleOut += Math.abs( i -( Math.min(i,j)-i ) ); // ( min(i,j)-i, n-1 )
					ruleOut += Math.abs( i -( Math.min(i+j,size-1) ) ); // ( min(i+j,n-1), (i+j) % n )
					*/
					values.add( new QueenValue(possibleValue, 0) );
				}
			}
			
			Collections.sort(values);
			return values;
		}

		@Override
		public int compareTo(Variable v) {
			QueenVariable o = (QueenVariable)v;
			if( legalValues != o.legalValues )
				return legalValues - o.legalValues;
			else
				return constraints - o.constraints;
		}

		@Override
		public void deassignVariable() {
			queens[col] = -1;
		}

		@Override
		public String toString() {
			return "QueenVariable [col=" + col + ", legalValues=" + legalValues
					+ ", constraints=" + constraints + "]";
		}
		
		
	}

	@Override
	public CSPState deepClone() {
		BoardCSP copy = new BoardCSP();
		copy.size = size;
		copy.maxConflicts = maxConflicts;
		copy.fitness = fitness;
		copy.queens = new int[queens.length];
		for (int i = 0; i < queens.length; i++) {
			copy.queens[i] = queens[i];
		}
		return copy;
	}
	
	public static void main(String[] args) {
		BoardCSP c = BoardCSP.emptyState(4);
		c.queens = new int[]{0, 3, -1, -1};
		for( Variable v : c.unAssignedVariablesInOrder() ){
			System.out.println(v);
			for( Value value : v.possibleValuesInOrder() )
				System.out.println(value);
		}
	}
	
}
