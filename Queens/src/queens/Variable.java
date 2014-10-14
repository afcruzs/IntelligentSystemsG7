package queens;

public interface Variable {

	/*
	 * Aca se escogen los estados consistentes
	 * que puede alcanzar esta variable,
	 * aca se puede aplicar la regla de 
	 * Least constraining value 
	 */
	Iterable<CSPState> getConsistentStates();

}
