package unalcol.agents.examples.rubik.grupo7;

public interface RubikHeuristic {
	/*
	 * This should compute the estimate 
	 * cost from state to the GOAL state.
	 */
	double h(RubikState state);
	
	/*
	 * Sets the goalState in order to
	 * compute correctly the h value.
	 */
	void setGoalCube(RubikCube goal);
}
