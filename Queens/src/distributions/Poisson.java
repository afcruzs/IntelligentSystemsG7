package distributions;

public class Poisson implements ProbabilityDistribution {

	
	private double lambda;
	
	public Poisson(double lambda){
		this.lambda = lambda;
	}
	
	@Override
	public double randomValue() {
		return  StdRandom.poisson(lambda);
		
	}

}
