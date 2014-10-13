package distributions;


public class Normal implements ProbabilityDistribution {

	
	private double mean;
	private double dev;
	
	public Normal ( double mean, double dev ) {
		this.mean = mean;
		this.dev =dev;
	}
	@Override
	public double randomValue() {
		
		return StdRandom.gaussian( mean, dev );
	}
	
	public String toString(){
		return "Normal, Media: " + mean + " Desviación: " +dev;
	}

}
