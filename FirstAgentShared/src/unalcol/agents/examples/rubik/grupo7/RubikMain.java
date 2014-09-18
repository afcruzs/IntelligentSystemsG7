package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
	static RubikPerception initPerception = new RubikPerception(
				new RubikCube(new int[][][]{
						{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE },
							{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE },
							{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE } 
						},
						{ 	{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE } 
						},
						{ 	{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED } 
						},
						{ 	{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN } 
						},
						{ 	{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
							{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
							{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE } 
						},
						{ 	{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.GREEN },
							{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.GREEN },
							{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.GREEN } 
						},
						
				})
			);
	
	
	
	public static void main(String[] args) {
		//System.out.println(initPerception.getCube());
		DepthLimitedSearch dls = new DepthLimitedSearch(4);
		System.out.println(dls.search(initPerception.getCube().clone()));
		//System.out.println(initPerception.getCube());
		//System.out.println( initPerception.getCube().moveCube( RubikAction.leftAction()) );
	}

}
