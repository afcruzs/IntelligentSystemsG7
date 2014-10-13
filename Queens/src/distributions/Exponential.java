package distributions;

public class Exponential implements ProbabilityDistribution {

	
	//Media
	private double lambda;
	
	public Exponential(double mean){
		this.lambda = 1.0/mean;
	}
	
	@Override
	public double randomValue() {
		
		return StdRandom.exp(lambda);
	}
	
	public String toString(){
		return "Exponencial, Media: " + (1/this.lambda);
	}
	
}
