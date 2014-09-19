package unalcol.agents.examples.rubik.grupo7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthLimitedSearch extends RubikSearch {

	private int limit;
	
	public DepthLimitedSearch(int limit){
		this.limit = limit;
	}
	
	@Override
	public List<RubikAction> doSearch(RubikCube cube) {
		Stack<RubikState> stack = new Stack<>();
		stack.add( new  RubikState( cube, null, null, 0, 0.0 ) );
		
		Set<RubikState> seen = new HashSet<>();
		
		RubikState current = null;
		int it = 0;
		while(!stack.isEmpty()){
			it++;
			current = stack.pop();
			System.out.println(current.depth);
			expandedNodes++;
			//if( current.depth < limit ){
				seen.add(current);
				for( RubikState state : current.successorFunction() ){
					if( !seen.contains(state) ){
						if( testGoal(state) ){
							System.out.println(it);
							return buildSolution(state);
						}
						
						if( state.depth <= limit )
							stack.add(state);
					}
				}
			//}
		}
		
		System.out.println(it);
		return new LinkedList<>();
	}	
	
	public void setLimit(int limit){
		this.limit = limit;
	}
}
