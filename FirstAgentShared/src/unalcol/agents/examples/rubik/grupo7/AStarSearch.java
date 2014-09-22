package unalcol.agents.examples.rubik.grupo7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch extends RubikSearch {
	
	private RubikHeuristic heuristic;
	
	public AStarSearch(RubikHeuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	public List<RubikAction> search( RubikCube cube ){
		super.updateCube(cube);
		if( goalState.equals(cube)  ) return new LinkedList<>();
		heuristic.setGoalCube(super.goalState);
		return doSearch( cube );
	}
	

	@Override
	public List<RubikAction> doSearch(RubikCube cube) {
		PriorityQueue<HeuristicNode> q = new PriorityQueue<>();
		Set<RubikState> seen = new HashSet<>();
		q.add(new HeuristicNode(new RubikState(cube, null, null, 1, 0.0)));
		HeuristicNode current = null;
		
		while( !q.isEmpty() ){
			current = q.poll();
			expandedNodes++;
			seen.add(current.state);			
			for( RubikState st : current.state.successorFunction() ){
				
				if( !seen.contains(st) ){
					if( testGoal(st) ){
						System.out.println("The depth is: " + current.state.depth);
						return buildSolution(st);
					}
					
					q.add(new HeuristicNode(st) );
				}
			}
		}
		
		return new LinkedList<>();
	}
	
	class HeuristicNode implements Comparable<HeuristicNode>{
		
		RubikState state;

		public HeuristicNode(RubikState state) {
			this.state = state;
		}
		
		private double f(){
			return heuristic.h(state) + state.getCost();
		}

		@Override
		public int compareTo(HeuristicNode o) {
			return (int) (f() - o.f()) ;
		}
		
		public boolean equals(Object o){
			return compareTo((HeuristicNode)o) == 0;
		}
		
	}

}
