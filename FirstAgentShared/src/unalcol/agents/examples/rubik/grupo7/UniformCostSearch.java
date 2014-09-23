package unalcol.agents.examples.rubik.grupo7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class UniformCostSearch extends RubikSearch {
	@Override
	public List<RubikAction> doSearch(RubikCube cube) {
		PriorityQueue<Node> q = new PriorityQueue<>();
		Set<RubikState> seen = new HashSet<>();
		q.add(new Node(new RubikState(cube, null, null, 1, 0.0)));
		Node current = null;
		
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
					
					q.add(new Node(st) );
				}
			}
		}
		
		return new LinkedList<>();
	}
	
	class Node implements Comparable<Node>{
		
		RubikState state;

		public Node(RubikState state) {
			this.state = state;
		}

		@Override
		public int compareTo(Node o) {
			return (int) (state.getCost() - o.state.getCost());
		}
		
	}

	@Override
	public String getPresentationName() {
		return "Uniform Cost Search";
	}

		
}
