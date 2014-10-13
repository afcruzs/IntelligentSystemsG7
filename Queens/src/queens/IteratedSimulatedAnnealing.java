package queens;

public class IteratedSimulatedAnnealing implements Search {
	private int maxDepth;
	private TemperatureFunction temperatureFunction;
	public IteratedSimulatedAnnealing(TemperatureFunction temperatureFunction,int maxDepth){
		this.maxDepth = maxDepth;
		this.temperatureFunction = temperatureFunction;
	}
	
	@Override
	public Board search(Board initial) {
		
		
		Board result = initial;
		do{
			result = new SimulatedAnnealingSearch(temperatureFunction,maxDepth).search(result);
		}while( !result.isPerfect() );
		
		return result;
	}
	
	
}
