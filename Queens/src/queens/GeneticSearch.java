package queens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class GeneticSearch implements Search {
	
	int populationSize, maxIterations;
	double totalFitness ;
	private GenotypeCreator creator;
	Population population;
	
	public GeneticSearch(int populationSize, int maxIterations,
			GenotypeCreator creator) {
		this.populationSize = populationSize;
		this.maxIterations = maxIterations;
		this.creator = creator;
		this.population = new Population(populationSize);
	}

	public GeneticSearch(int populationSize,
			GenotypeCreator creator) {
		this(populationSize,Integer.MAX_VALUE,creator);
	}


	@Override
	public Board search( Board init ){
		
		int iteration = 0;
		while( !population.getBestIndividual().isPerfect() &&
				iteration++ < maxIterations ) {
			
		//	System.out.println(  population.getBestIndividual().fitness() + " "+ population.getAverageFitness() );
			//System.out.println( population.getBestIndividual() );
			
		//	System.out.println("Getting good random");
			
			Genotype[] parent = population.getGoodRandomIndividuals();
			
			//System.out.println( population + "\n" );
		//	System.out.println("Getting bad random");
			Integer toReplaceIndex1 = 0, toReplaceIndex2 = 0;
			Genotype[] toReplace = population.getBadRandomIndividuals(toReplaceIndex1, toReplaceIndex2);
		
			//System.out.println("Fucking");
			Genotype[] children = parent[0].crossOver(parent[1]);
			
			//System.out.println( Arrays.toString(parent) );
			//System.out.println( Arrays.toString(children) );
			
			
		//	System.out.println("Mutating");
			children[0].mutate();
			children[1].mutate();
			
		//	System.out.println("Updating");
			population.updatePopulation(children, toReplaceIndex1, toReplaceIndex2);
			//System.out.println( population + "\n" );
		}	
		return (Board) population.getBestIndividual();
	}
	
	class Population{
		ArrayList<Genotype> population;
		ArrayList<Double> ranges;
		ArrayList<Double> inverseRanges;
		int size;
		double totalFitness, inverseTotalFitness;
		
		public Population(int size){
			this.size = size;
			population = new ArrayList<>(size);
			ranges = new ArrayList<>(size);
			inverseRanges = new ArrayList<>(size);
			totalFitness = 0;
			inverseTotalFitness = 0;
			generateRandomPopulation();
			
		}
		
		public void updatePopulation(Genotype[] children,
				Integer toReplaceIndex1, Integer toReplaceIndex2) {
			totalFitness = 0;
			inverseTotalFitness = 0;
			
			/*population.set(toReplaceIndex1, children[0]);
			population.set(toReplaceIndex2, children[1]);*/
			
			population.add(children[0]);
			population.add(children[1]);
			Collections.sort(population);
			population.remove(0);
			population.remove(0);
			
			ranges = new ArrayList<>(size);
			inverseRanges = new ArrayList<>(size);
			
			for (int i = 0; i < size; i++) {
				totalFitness += population.get(i).fitness();
				inverseTotalFitness += 
						population.get(i).getMaxFitness() - population.get(i).fitness();
				
				ranges.add(totalFitness);
				inverseRanges.add(inverseTotalFitness);
			}
		}

		private void generateRandomPopulation() {
			
			inverseTotalFitness = 0;
			for (int i = 0; i < size; i++) {
				population.add( creator.create() );
				totalFitness += population.get(i).fitness();
				inverseTotalFitness += 
						population.get(i).getMaxFitness() - population.get(i).fitness();
				ranges.add(totalFitness);
				inverseRanges.add(inverseTotalFitness);
			}
			
			//System.out.println(inverseTotalFitness + " " + totalFitness);
		}
		
		public Genotype[] getRandomIndividuals( Integer index1, Integer index2,
				double theFitness, boolean flag ) {
			
			ArrayList<Double> list = flag == true ? ranges : inverseRanges;
			double p1 = new Random().nextDouble() * theFitness, p2;
			double first = -1, last = -1;
			for( int i=0; i<size; i++ )
				if( list.get(i) >= p1 ) {
					index1 = i;
					first = list.get(i) - 
							(flag == true ? population.get(i).fitness() : population.get(i).remainingFitnessToBePerfect() ) + 1;
					last = list.get(i);
					break;
				}
				
			do {
				p2 = new Random().nextDouble() * theFitness;
			} while( (p2 >= first && p2 <= last) || p1 == p2 );

			for( int i=0; i<size; i++ )
				if( list.get(i) >= p2 ) {
					index2 = i;
					break;
				}
			
			return new Genotype[] { population.get(index1), population.get(index2) };
		}
		
		public Genotype[] getGoodRandomIndividuals() {
			return getRandomIndividuals( -1, -1, totalFitness, true );
			
		}
		
		public Genotype[] getBadRandomIndividuals(Integer index1, Integer index2) {
			return getRandomIndividuals( index1, index2, inverseTotalFitness, false );
			
		}
		
		public Genotype getBestIndividual(){
			return Collections.max(population);
		}
		
		public String toString(){
			return population.toString();
		}
		
		public double getAverageFitness(){
			double avg = 0.0;
			for( Genotype gen : population )
				avg += gen.fitness();
			return avg/( (double) population.size() );
		}
	}
	
}