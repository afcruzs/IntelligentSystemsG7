package queens;

public class QueensMain {
	
	public static void main(String[] args) {
		int n = 100;
		testGenetic(n);

	}

	static void testSearch(Search searchAlgorithm, Genotype initial){
		long time = System.currentTimeMillis();
		System.out.println( searchAlgorithm.search(initial) );
		System.out.println( "Elapsed Time: " + (System.currentTimeMillis() - time) + "ms." );
	}
	
	static void testGenetic(int n){
		testSearch( new GeneticSearch(n, 
				new BoardCreator(n) ), Board.randomGenotype(n) );
	}
	
	

}
