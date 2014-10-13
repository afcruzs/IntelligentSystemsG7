package queens;

import java.util.ArrayList;

public interface CSPState {
	boolean testGoalState();
	ArrayList<ArrayList<Integer>> remainingValues();
	int[] countConstraints();
}
