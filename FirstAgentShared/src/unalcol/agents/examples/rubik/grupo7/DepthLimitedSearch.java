package unalcol.agents.examples.rubik.grupo7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class DepthLimitedSearch extends RubikSearch {

	private int limit;
	
	public DepthLimitedSearch(int limit){
		this.limit = limit;
	}
	
	private List<RubikAction> recSearch(RubikState state, Set<RubikState> seen){
	//	System.out.println(state.getCube());
		if( state.depth >= 0 ){
			seen.add(state);
			expandedNodes++;
			if( testGoal(state) )
				return buildSolution(state);
			for(RubikState st: state.successorFunction()){
				if( !seen.contains(st) ){
					List<RubikAction> t = recSearch(new RubikState(st.getCube(), state, st.getAction(), 
											state.depth-1, st.getCost()), seen);
					if(t.size() > 0) return t;
				}
			}
		}
		
		return new LinkedList<>();
	}
	public List<RubikAction> doSearch2(RubikCube cube) {
		return recSearch(new RubikState(cube, null, null, limit, 0.0), new HashSet<RubikState>());
	}
	
	public List<RubikAction> doSearch(RubikCube cube) {
		Stack<RubikState> q = new Stack<>();
		Set<RubikState> seen = new TreeSet<RubikState>( );
		q.add(new RubikState(cube, null, null, 1, 0.0));
		RubikState current = null;
		
		while( !q.isEmpty() ){
			current = q.pop();
			expandedNodes++;
			seen.add(current);
		//	System.out.println(current.getCube());
			if( current.depth > limit ) continue;
			for( RubikState st : current.successorFunction() ){
				
				if( !seen.contains(st) ){
					if( testGoal(st) ){
						System.out.println("The depth is: " + current.depth);
						return buildSolution(st);
					}
					
					q.add( st );
				}
			}
		}
		
		return new LinkedList<>();
	}	
	
	public void setLimit(int limit){
		this.limit = limit;
	}
}
