package queens;

import java.util.*;
public class Board implements Genotype{
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
		computeFitness();
	}
	private void randomBoard(){
		Random r = new Random();
		for (int i = 0; i < size; i++) 
			queens[i] = r.nextInt( size );
		
		
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
		
		for (int i = middle; i < size; i++) {
			first.queens[i] = other.queens[i];
			second.queens[i] = queens[i];
		}
		first.computeFitness();
		second.computeFitness();
		return new Genotype[]{ first, second };
	}
	@Override
	public void mutate() {
		Random r = new Random();
		int bit = r.nextInt( size );
		queens[bit] = r.nextInt( size );
	}
	@Override
	public double remainingFitnessToBePerfect() {
		return maxConflicts - fitness;
	}
	
	public String toString(){
		return fitness() + " " + Arrays.toString(queens) + "\n";
	}
}
