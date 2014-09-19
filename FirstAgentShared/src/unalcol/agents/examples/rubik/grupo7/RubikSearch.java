package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.List;

public abstract class RubikSearch {
	protected int expandedNodes;
	public RubikCube goalState;
	
	public RubikSearch(){
		this.expandedNodes = 0;
		
	}
	
	private void updateCube( RubikCube cube ){
		goalState = new RubikCube();
		for (int i = 0; i < 6; i++) {
			int color = cube.getColorCenter(i);
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 3; k++)
					goalState.setAt(i,j,k,color);
		}
		
	}
	
	public List<RubikAction> search( RubikCube cube ){
		updateCube(cube);
		return doSearch( cube );
	}
	
	public abstract List<RubikAction> doSearch( RubikCube cube );
	
	
	protected List<RubikAction> buildSolution(RubikState finalState){
		
		LinkedList<RubikAction> l = new LinkedList<>();
		
		while( finalState.getParent() != null ){
			l.add(0, finalState.getAction());
			finalState = finalState.getParent();
		}
		return l;
	}
	
	public int getExpandedNodes(){
		return expandedNodes;
	}
	
	protected boolean testGoal(RubikState state){
		return goalState.equals(state.getCube());
	}
}
