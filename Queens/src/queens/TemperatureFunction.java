package queens;

public interface TemperatureFunction {
	/*
	 * Esta funcion de temperatura va reduciendo
	 * su valor hasta 0 a medida que aumentan las 
	 * iteraciones. Cuando iteration = 0 la funcion
	 * debe retornar 1.
	 */
	double temperature(int iteration);
}	
