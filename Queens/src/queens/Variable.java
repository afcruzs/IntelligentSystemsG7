package queens;


public interface Variable extends Comparable<Variable> {
	
	void assignValue( Value value );
	Iterable<Value> possibleValuesInOrder();
	void deassignVariable();

}
