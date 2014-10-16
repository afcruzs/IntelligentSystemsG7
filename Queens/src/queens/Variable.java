package queens;

import java.util.List;


public interface Variable extends Comparable<Variable> {
	
	void assignValue( Value value );
	List<Value> possibleValuesInOrder();
	void deassignVariable();

}
