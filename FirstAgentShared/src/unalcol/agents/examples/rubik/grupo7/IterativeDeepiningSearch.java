package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.List;

public class IterativeDeepiningSearch  extends RubikSearch {
	
	private int limit;
	
	public IterativeDeepiningSearch(int limit) {
		this.limit = limit;
	}



	@Override
	public List<RubikAction> doSearch(RubikCube cube) {
		List<RubikAction> t = null;
		DepthLimitedSearch depthLimited;
		for(int i=1; i<=limit; i++){
			System.out.println(i);
			depthLimited = new DepthLimitedSearch(i);
			t = depthLimited.search(cube.clone());
			expandedNodes += depthLimited.expandedNodes;
			if(t.size() > 0)
				return t;
		}
		return new LinkedList<>();
	}

}
