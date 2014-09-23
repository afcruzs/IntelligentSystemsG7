package unalcol.agents.examples.rubik.grupo7;

public class UniformCostHeuristic implements RubikHeuristic {

	@Override
	public double h(RubikState state) {
		return 0.0;
	}

	@Override
	public void setGoalCube(RubikCube goal) {

	}

	@Override
	public String getName() {
		return "Uniform Cost";
	}

}
