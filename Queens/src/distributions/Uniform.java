package distributions;

import main.Main;

public class Uniform implements ProbabilityDistribution {

	double min;
	double max;
	
	public Uniform(double min, double max){
		this.min = min;
		this.max = max;
	}
	
	@Override
	public double randomValue() {
		//Un numero aleatorio de distribucion uniforme comprendido entre a y b. Intervalo: [a;b)
		return StdRandom.uniform(min, max);
	}
	
	public String toString(){
		return "Uniforme, minimo: " + min + " máximo: " +max;
	}
	
}
