package queens;

public class QueensMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GeneticSearch(8, 1000, new BoardCreator(8) ).search( Board.randomGenotype(8) );

	}

}
