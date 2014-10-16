package queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public class BoardCSP extends Board implements CSPState {
	
	ArrayList< List<Value> > domainValues;
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
	public List<Variable> unAssignedVariablesInOrder() {
		
		ArrayList<Variable> variables = new ArrayList<>(size);
		domainValues = new ArrayList<>( size );
		for( int i=0; i<size; i++ ) domainValues.add(null);
		
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
			
			if( legalValues == 0 ){
				//System.out.println("Lool");
				return new ArrayList<>();
			}
			QueenVariable qv = new QueenVariable(x, legalValues, countVariables());
			variables.add( qv );
			domainValues.set(x, qv.possibleValuesInOrder() );
		}
		

		Collections.sort(variables);
		return variables;
	}
	
	public void arcConsistency(){
		Queue<Integer[]> arcs = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			if( domainValues.get(i) != null )
				for (int j = i+1; j < size; j++) {				
					if( domainValues.get(j) != null )
						arcs.add( new Integer[]{i,j} );
				}
		}
		
		while( !arcs.isEmpty() ){
			Integer[] current = arcs.poll();
			if( removeInconsistent( current[0], current[1] ) )
				for (int i = 0; i < size; i++) {
					if( domainValues.get(i) != null && domainValues.get(current[0]) != null )
						arcs.add(new Integer[]{i,current[0]});
				}
		}
	}
	
	public boolean removeInconsistent(int a, int b){
		boolean removed = false;
		Queue<Integer> idx1 = new LinkedList<>();
		Queue<QueenValue> idx2 = new LinkedList<>();
		for(Value it : domainValues.get(a)){
			QueenValue qv1 = (QueenValue)it;
			boolean satisfiedOne = false;
			for(Value it2 : domainValues.get(b)){
				QueenValue qv2 = (QueenValue)it2;
				if( Math.abs(a-b) != Math.abs(qv1.row - qv2.row) )
					satisfiedOne = true;
					
			}
			if(!satisfiedOne){
				//domainValues.get(a).remove(qv1);
				idx1.add(a);
				idx2.add(qv1);
				removed = true;
			}
		}
		
		while( !idx1.isEmpty() || !idx2.isEmpty() ){
			domainValues.get( idx1.poll() ).remove( idx2.poll() );
		}
		
		return removed;
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
		public List<Value> possibleValuesInOrder() {
			
			if( domainValues.get(col) != null ) return domainValues.get(col);

			ArrayList<Value> values = new ArrayList<>(size);
			
			
			for (int possibleValue = 0; possibleValue < size; possibleValue++) {
				if( containsBit(possibleValue) ) continue;
				int i;
				for (i = 0; i < size; i++) {
					if( !(queens[i] == -1 || Math.abs( col - i ) != Math.abs( possibleValue - queens[i] )) ){
						break;
					}
				}
				if( i >= size ){
					Set<Integer> s = new HashSet<>();
					int x = possibleValue, y = col;
					//System.out.println( x + " " + y + " " + size );
					while( true ){
						x--; y--;
						if( x < 0 || y < 0 ) break;
						if( queens[y] == -1 )
							s.add(y);						
					}
					//System.out.println(s);
					x = possibleValue; y = col;
					while( true ){
						x++; y++;
						if( x >= size || y >= size ) break;
						if( queens[y] == -1 )
							s.add(y);
						
					}
					//System.out.println(s);
					
					x = possibleValue; y = col;
					while( true ){
						x--; y++;
						if( x < 0 || y >= size ) break;
						if( queens[y] == -1 )
							s.add(y);
					}
					//System.out.println(s);
					
					x = possibleValue; y = col;
					while( true ){
						x++; y--;
						if( x >= size || y < 0 ) break;
						if( queens[y] == -1 )
							s.add(y);
					}
					
					values.add( new QueenValue(possibleValue, s.size()) );
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
		BoardCSP c = BoardCSP.emptyState(6);
		c.queens = new int[]{0, -1, 1, 4, -1, -1};
		for( Variable v : c.unAssignedVariablesInOrder() ){
			System.out.println(v);
			for( Value value : v.possibleValuesInOrder() )
				System.out.println(value);
		}
	}
	
}
