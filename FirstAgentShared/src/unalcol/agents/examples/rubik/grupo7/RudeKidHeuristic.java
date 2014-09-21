package unalcol.agents.examples.rubik.grupo7;

import java.util.List;

public class RudeKidHeuristic implements RubikHeuristic {

	private RubikCube goal;
	
	@Override
	public double h(RubikState state) {
		List<Block> a = state.getCube().getBlocks();
		List<Block> b = goal.getBlocks();
		int badBlocks = 0;
		for(int i=0; i<a.size(); i++){
			if( !a.get(i).equals(b.get(i)) ) badBlocks++;
		}
		return Math.ceil(badBlocks/8.0);
		//return 0.0;
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		this.goal = goal;
	}

}
