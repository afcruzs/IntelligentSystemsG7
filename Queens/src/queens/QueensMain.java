package queens;

import javax.swing.SwingUtilities;

public class QueensMain {
	
	public static void main(String[] args) {
		int n = 4;
		//testCSP(n,new CSPSearch(),Board.randomGenotype(n));
		//testGenetic(n);
		testSimulatedAnnealing(n, inverseIteration);

	}

	static void testSearch(Search searchAlgorithm, Board initial){
		long time = System.currentTimeMillis();
		final Genotype ans = searchAlgorithm.search(initial);
		System.out.println( ans );
		time = (System.currentTimeMillis() - time);
		final long time2 = time;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new QueensFrame((Board)ans,"Elapsed Time: " + time2 + "ms.");
			}
		});
		
		System.out.println( "Elapsed Time: " + time + "ms." );
		
	}
	
	static void testGenetic(int n){
		testSearch( new GeneticSearch(n, 
				new BoardCreator(n) ), Board.randomGenotype(n) );
	}
	
	static void testCSP(int n, Search searchAlgorithm , Board initial){
		long time = System.currentTimeMillis();
		final Genotype ans = searchAlgorithm.search(initial);
		System.out.println( ans );
		time = (System.currentTimeMillis() - time);
		final long time2 = time;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new QueensFrame((Board)ans,"Elapsed Time: " + time2 + "ms.");
			}
		});
		
		System.out.println( "Elapsed Time: " + time + "ms." );
	}

	static void testSimulatedAnnealing(int n, TemperatureFunction temp){
		testSearch(new SimulatedAnnealingSearch(temp), Board.randomGenotype(n));
	}
	
	static void testIteratedSimulatedAnnealing(int n, TemperatureFunction temp, int maxDepth){
		testSearch(new IteratedSimulatedAnnealing(temp,maxDepth), Board.randomGenotype(n));
	}
	
	static TemperatureFunction inverseIteration = new TemperatureFunction() {
		
		@Override
		public double temperature(int iteration) {
			return 1.0/((double)iteration);
		}
	};
	
	static TemperatureFunction inversePolinomial = new TemperatureFunction() {
		
		@Override
		public double temperature(int iteration) {
			return 1.0/(Math.pow((double)iteration,5));
		}
	};
	
	static TemperatureFunction exponentialDecay = new TemperatureFunction() {
		
		@Override
		public double temperature(int iteration) {
			return Math.exp(-1.0*iteration);
		}
	};


	

}
