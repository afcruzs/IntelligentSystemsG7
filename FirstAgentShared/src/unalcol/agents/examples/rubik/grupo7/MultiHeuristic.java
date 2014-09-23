package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;

public class MultiHeuristic implements RubikHeuristic {

	LinkedList<RubikHeuristic> heuristics;
	
	public MultiHeuristic(){
		this.heuristics = new LinkedList<>();
	}

	public void addHeuristic(RubikHeuristic heuristic){
		this.heuristics.add(heuristic);
	}
	
	@Override
	public double h(RubikState state) {
		double max = 0.0;
		for(RubikHeuristic rh : heuristics){
			max = Math.max( max, rh.h(state) );
		}
		return max;
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		for(RubikHeuristic rh : heuristics)
			rh.setGoalCube(goal);
	}

	@Override
	public String getName() {
		return "MultiHeuristic";
	}

}
