package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class AStarSearch extends RubikSearch {
	
	private RubikHeuristic heuristic;
	
	public AStarSearch(RubikHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public List<RubikAction> doSearch(RubikCube cube) {
		PriorityQueue<HeuristicNode> q = new PriorityQueue<>();
		TreeSet<RubikState> seen = new TreeSet<>();
		q.add(new HeuristicNode(new RubikState(cube, null, null, 0, 0.0)));
		HeuristicNode current = null;
		
		while( !q.isEmpty() ){
			current = q.poll();
			expandedNodes++;
			seen.add(current.state);			
			for( RubikState st : current.state.successorFunction() ){
				
				if( !seen.contains(st) ){
					if( testGoal(st) )
						return buildSolution(st);
					
					q.add(new HeuristicNode(st) );
					seen.add( st );
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
