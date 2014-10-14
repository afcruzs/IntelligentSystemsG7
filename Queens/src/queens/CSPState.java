package queens;

public interface CSPState {
	/*boolean testGoalState();
	ArrayList<ArrayList<Integer>> remainingValues();
	int[] countConstraints();*/
	boolean isPerfect();
	
	//Saca la mejor variable para asignar segun la heuristica
	Variable nextVariable();
	
}
