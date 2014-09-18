package unalcol.agents.examples.rubik.grupo7;

public interface RubikHeuristic {
	/*
	 * This should compute the estimate 
	 * cost from state to the GOAL state.
	 */
	double cost(RubikState state);
}
