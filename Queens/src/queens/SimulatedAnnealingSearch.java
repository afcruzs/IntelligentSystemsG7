package queens;

import java.util.Random;


public class SimulatedAnnealingSearch implements Search {
	private int maxIterations;
	private TemperatureFunction temperatureFunction;
	
	public SimulatedAnnealingSearch(TemperatureFunction temperatureFunction,int maxIterations){
		this.maxIterations = maxIterations;
		this.temperatureFunction = temperatureFunction;
	}
	
	public SimulatedAnnealingSearch(TemperatureFunction temperatureFunction){
		this.maxIterations = Integer.MAX_VALUE;
		this.temperatureFunction = temperatureFunction;
	}
	
	
	@Override
	public Board search(final Board initial) {
		
		int c = 0;
		SAState current = initial;
		SAState next = null;
		double deltaE,prob,T;
		
		do{
		//	System.out.println(current.value());
			T = temperatureFunction.temperature(c);
			//System.out.println(T);
			next = current.randomSuccessor();
			deltaE = next.value() - current.value();
			if( deltaE > 0 )
				current = next;
			else{
				prob = Math.exp(deltaE/T);
				Random r = new Random();
				if( r.nextDouble()*100.0 < prob )
					current = next;
			}
		}while( !current.isPerfect() && c++ < maxIterations );
		
		return (Board) current;
	}

}
