package unalcol.agents.examples.rubik.grupo7;

public class CountColoursHeuristic implements RubikHeuristic {
	
	private RubikCube goal;
	
	@Override
	public double h(RubikState state) {
		
		double count = 0;
		RubikCube actual = state.getCube();
		for (byte i = 0; i < 6; i++) {
			for (byte j = 0; j < 3; j++)
				for (byte k = 0; k < 3; k++)
					if(goal.getAt(i,j,k) != actual.getAt(i,j,k))
						count++;
		}
		
		//return Math.ceil(count/3.0);
		return count/24.0;
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		this.goal  = goal ;

	}

}
