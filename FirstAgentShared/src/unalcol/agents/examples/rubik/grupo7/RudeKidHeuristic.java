package unalcol.agents.examples.rubik.grupo7;

import java.util.List;

public class RudeKidHeuristic implements RubikHeuristic {

	private List<Block> b;
	@Override
	public double h(RubikState state) {
		List<Block> a = state.getCube().getBlocks();
		
		double badBlocks = 0;
		for(int i=0; i<a.size(); i++){
			if( !a.get(i).equals(b.get(i)) ) badBlocks++;
		}
		return Math.ceil(badBlocks/3.0);
		//return badBlocks*8.0;
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		this.b = goal.getBlocks();
	}

	@Override
	public String getName() {
		return "Rude kid heuristic";
	}

}
