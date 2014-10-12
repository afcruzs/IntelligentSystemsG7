package queens;

public class QueensMain {
	
	public static void main(String[] args) {
		int n = 10;
		testGenetic(n);

	}

	static void testSearch(Search searchAlgorithm, Genotype initial){
		long time = System.currentTimeMillis();
		Genotype ans = searchAlgorithm.search(initial);
		System.out.println( ans );
		time = (System.currentTimeMillis() - time);
		new QueensFrame((Board)ans,"Elapsed Time: " + time + "ms.");
		System.out.println( "Elapsed Time: " + time + "ms." );
	}
	
	static void testGenetic(int n){
		testSearch( new GeneticSearch(n, 
				new BoardCreator(n) ), Board.randomGenotype(n) );
	}
	
	

}
