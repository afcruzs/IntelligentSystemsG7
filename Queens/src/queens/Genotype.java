package queens;

public interface Genotype extends Comparable<Genotype> {
	double fitness();
	Genotype randomGenotype();
	boolean isPerfect();
	Genotype[] crossOver(Genotype other);
	double getMaxFitness();
	void mutate();
	double remainingFitnessToBePerfect();
}
