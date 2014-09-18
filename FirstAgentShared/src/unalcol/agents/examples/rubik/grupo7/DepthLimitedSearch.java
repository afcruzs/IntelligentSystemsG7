package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;

public class DepthLimitedSearch extends RubikSearch {

	private int limit;
	
	public DepthLimitedSearch(int limit){
		this.limit = limit;
	}
	
	@Override
	public List<RubikAction> search(RubikCube cube) {
		Stack<RubikState> stack = new Stack<>();
		stack.add( new  RubikState( cube, null, null, 0, 0.0 ) );
		
		TreeSet<RubikState> seen = new TreeSet<>();
		
		RubikState current = null;
		while(!stack.isEmpty()){
			current = stack.pop();
			if( current.depth == limit ) continue;
			seen.add(current);
			for( RubikState state : current.successorFunction() ){
				if( !seen.contains(state) ){
					if( testGoal(state) )
						return buildSolution(state);
					
					stack.add(state);
				}
			}
		}
		
		return new LinkedList<>();
	}	
	
	public void setLimit(int limit){
		this.limit = limit;
	}
}
