package queens;


public interface CSPState  {
	/*boolean testGoalState();
	ArrayList<ArrayList<Integer>> remainingValues();
	int[] countConstraints();*/
	boolean isPerfect();

	Iterable<Variable> unAssignedVariablesInOrder();
	
	CSPState deepClone();

}
