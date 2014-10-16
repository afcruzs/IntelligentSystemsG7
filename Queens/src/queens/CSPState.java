package queens;

import java.util.List;


public interface CSPState  {
	/*boolean testGoalState();
	ArrayList<ArrayList<Integer>> remainingValues();
	int[] countConstraints();*/
	boolean isPerfect();

	List<Variable> unAssignedVariablesInOrder();
	
	CSPState deepClone();
	
	void arcConsistency();

}
