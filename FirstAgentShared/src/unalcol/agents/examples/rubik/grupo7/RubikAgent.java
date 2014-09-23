package unalcol.agents.examples.rubik.grupo7;

import java.util.List;

public class RubikAgent  {

	private RubikSearch search;
	
	public RubikAgent() {
		super();
		this.search = new AStarSearch(new RudeKidHeuristic());
	}
	


	public void setSearch(RubikSearch search) {
		this.search = search;
	}


	public List<RubikAction> solve( RubikCube cube ){
		long time = System.currentTimeMillis();
		System.out.println( search.getPresentationName() );
		List<RubikAction> answer = search.search(cube);
		time = System.currentTimeMillis() - time;
		System.out.println(search.getExpandedNodes()+ " expanded nodes.");
		System.out.println( time + "ms elapsed." );
		return answer;
	}
}
