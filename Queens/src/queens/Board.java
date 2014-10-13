package queens;

import java.util.*;
public class Board implements Genotype, SAState{
	protected int[] queens; //elemento -> fila, indice -> columna
	protected int size;
	protected int maxConflicts;
	private double fitness;
	public Board(int size,boolean doRandom){
		this.queens = new int[size];
		this.size = size;
		this.maxConflicts = size*(size-1)/2;
		if(doRandom)
			randomBoard();
		else
			emptyBoardInit();
		computeFitness();
	}
	
	private Board(){}
	
	public Iterable<QueenPiece> getPairs(){
		
		Set<QueenPiece> p = new HashSet<>();
		for(int i=0; i<queens.length; i++){
			p.add( new QueenPiece(i, queens[i]) );
		}
		return p;
	}
	
	class QueenPiece{
		int col, row;
		public QueenPiece(int col, int row){
			this.col = col;
			this.row = row;
		}
	}
	
	/**
	 * Inits the array with non permitted values
	 * @author Felipe
	 * 
	 */
	private void emptyBoardInit(){
		for (int i = 0; i < size; i++) {
			queens[i] = -1;
		}
	}
	
	private void randomBoard(){
		Random r = new Random();
		/*
		for (int i = 0; i < size; i++) 
			queens[i] = r.nextInt( size );
		*/
		
		emptyBoardInit();
		//Modified to obatin a 0-(size-1) permutation
		int t = -1;
		for (int i = 0; i < size; i++) {
			while( containsBit(t) )
				t = r.nextInt( size );
			queens[i] = t;
		}
		
		
	}
	
	public double getMaxFitness(){
		return maxConflicts;
	}

	public static Board randomGenotype(int size) {
		return new Board(size,true);
	}
	
	private void computeFitness(){
		double conflicts = 0.0;
		for (int x = 0; x < size; x++) {
			int y = queens[x];
			
			for ( int i = x+1; i < size; i++ ) {
				if ( y == queens[i] || 
					Math.abs( y - queens[i] ) == Math.abs( x - i ) )
					conflicts++;
			}
		}
		this.fitness = maxConflicts - conflicts;
	}
	
	/**
	 * Returns size - number of conflicts.
	 * The greater the conflicts the smaller the fitness.
	 * @see queens.Genotype#fitness()
	 */
	@Override
	public double fitness() {
		return fitness;
	}
	
	@Override
	public int compareTo(Genotype arg0) {
		return (int)( fitness - ((Board)arg0).fitness() );
	}
	@Override
	public Genotype randomGenotype() {
		return new Board(size,true);
	}
	@Override
	public boolean isPerfect() {
		return maxConflicts == fitness();
	}
	
	/*@Override
	public Genotype[] crossOver(Genotype o) {
		Random r = new Random();
		Board other = (Board)o;
		int middle = r.nextInt(size);
		Board first = new Board(size,false);
		Board second = new Board(size,false);
		for (int i = 0; i < middle; i++) {
			first.queens[i] = queens[i];
			second.queens[i] = other.queens[i];
		}
		for (int i = middle; i < size; i++) {
			first.queens[i] = other.queens[i];
			second.queens[i] = queens[i];
		}
		first.computeFitness();
		second.computeFitness();
		return new Genotype[]{ first, second };
	}*/
	/**
	 * 
	 * Hace crossover con un pivote aleatorio
	 * pero evita que se repitan elementos.
	 */
	@Override
	public Genotype[] crossOver(Genotype o) {
		Random r = new Random();
		Board other = (Board)o;
		int middle = r.nextInt(size);
		
		Board first = new Board(size,false);
		Board second = new Board(size,false);
		
		for (int i = 0; i < middle; i++) {
			first.queens[i] = queens[i];
			second.queens[i] = other.queens[i];
		}
		
		/*System.out.println( Arrays.toString(queens) );
		System.out.println( Arrays.toString(other.queens) );
		System.out.println( middle );*/
		
		
		int idx = middle;
		
		for (int i = middle; i < size; i++) {
			//System.out.println(i);
			while( first.containsBit( other.queens[idx] ) ){
				idx = (idx+1) % size;
				//System.out.println( Arrays.toString(first.queens) + " " + other.queens[idx] );				
			}
			first.queens[i] = other.queens[idx];
		}
		//System.out.println("First one ");
		idx = middle;
		for (int i = middle; i < size; i++) {
			while( second.containsBit( queens[idx] ) ){
				idx = (idx+1) % size;
			}
			second.queens[i] = queens[idx];
		}
		/*System.out.println("Second one ");	
		System.out.println(Arrays.toString(first.queens));
		System.out.println(Arrays.toString(second.queens));*/
		
		
		first.computeFitness();
		second.computeFitness();
		return new Genotype[]{ first, second };
	}
	
	private boolean containsBit(int bit){
		for (int i = 0; i < size; i++) {
			if( queens[i] == bit ) return true;
		}
		
		return false;
	}
	
	/**
	 * Hace mutaciones haciendo swap
	 * de dos posiciones.
	 */
	@Override
	public void mutate() {
		Random r = new Random();
		int bit1 = r.nextInt( size );
		int bit2 = bit1;
		while(bit2 == bit1)
			bit2 = r.nextInt(size);
		
		swapBits(bit1,bit2);
		computeFitness();
		
	}
	
	public void swapBits(int bit1, int bit2){
		int t = queens[bit1];
		queens[bit1] = queens[bit2];
		queens[bit2] = t;
	}
	
	
	@Override
	public double remainingFitnessToBePerfect() {
		return maxConflicts - fitness;
	}
	
	public String toString(){
		return fitness() + " " + Arrays.toString(queens) + "\n";
	}

	public Board clone(){
		Board copy = new Board();
		copy.size = size;
		copy.maxConflicts = maxConflicts;
		copy.fitness = fitness;
		copy.queens = new int[queens.length];
		for (int i = 0; i < queens.length; i++) {
			copy.queens[i] = queens[i];
		}
		return copy;
	}

	@Override
	public SAState randomSuccessor() {
		Board successor = clone();
		successor.mutate();
		//successor.mutate();
		return successor;
	}


	@Override
	public double value() {
		return fitness();
	}
}
