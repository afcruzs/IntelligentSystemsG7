package queens;

public class BoardCreator implements GenotypeCreator {

	int size;
	public BoardCreator(int size){
		this.size = size;
	}
	@Override
	public Genotype create() {
		return new Board(size, true);
	}


}
