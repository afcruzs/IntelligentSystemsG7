package queens;

import javax.swing.SwingUtilities;

public class QueensMain {
	
	public static void main(String[] args) {
		int n = 50;
		testGenetic(n);

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
	
	

}
