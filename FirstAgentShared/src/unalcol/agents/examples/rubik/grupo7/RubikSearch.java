package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.List;

public abstract class RubikSearch {
	
	private static RubikCube goalState = new RubikCube(new int[][][]{
			{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.YELLOW },
				{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.YELLOW },
				{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.YELLOW } 
			},
			{ 	{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
				{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
				{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE } 
			},
			{ 	{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
				{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
				{ RubikCube.RED, RubikCube.RED, RubikCube.RED } 
			},
			{ 	{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN },
				{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN },
				{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN } 
			},
			{ 	{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
				{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
				{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE } 
			},
			{ 	{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE },
				{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE },
				{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE } 
			},
			
	} );
	public abstract List<RubikAction> search( RubikCube cube );
	
	protected List<RubikAction> buildSolution(RubikState finalState){
		
		LinkedList<RubikAction> l = new LinkedList<>();
		
		while( finalState.getParent() != null ){
			l.add(0, finalState.getAction());
			finalState = finalState.getParent();
		}
		return l;
	}
	
	protected boolean testGoal(RubikState state){
		return goalState.equals(state.getCube());
	}
}
