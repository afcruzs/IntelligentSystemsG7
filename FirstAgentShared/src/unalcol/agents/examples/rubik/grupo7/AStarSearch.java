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
	public List<RubikAction> search(RubikCube cube) {
		PriorityQueue<HeuristicNode> q = new PriorityQueue<>();
		TreeSet<HeuristicNode> seen = new TreeSet<>();
		
		HeuristicNode current = null;
		while(q.size() > 0){
			current = q.poll();
			seen.add(current);			
			for( RubikState st : current.state.successorFunction() ){
				if( !seen.contains(st) ){
					if( testGoal(st) )
						return buildSolution(st);
					
					seen.add( new HeuristicNode(st) );
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
			return heuristic.cost(state) + state.getCost();
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
