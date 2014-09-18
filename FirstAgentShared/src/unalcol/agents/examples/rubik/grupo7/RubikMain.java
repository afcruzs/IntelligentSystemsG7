package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
	static RubikPerception initPerception = new RubikPerception(
				new RubikCube(new int[][][]{
						{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.YELLOW },
							{ RubikCube.YELLOW, RubikCube.BLUE, RubikCube.YELLOW },
							{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.YELLOW } 
						},
						{ 	{ RubikCube.YELLOW, RubikCube.BLUE, RubikCube.ORANGE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
							{ RubikCube.RED, RubikCube.BLUE, RubikCube.GREEN } 
						},
						{ 	{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED } 
						},
						{ 	{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.RED, RubikCube.GREEN, RubikCube.ORANGE } 
						},
						{ 	{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
							{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE },
							{ RubikCube.ORANGE, RubikCube.ORANGE, RubikCube.ORANGE } 
						},
						{ 	{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE },
							{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE },
							{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE } 
						},
						
				})
			);
	
	
	
	public static void main(String[] args) {
		
		System.out.println(initPerception.getCube());
		System.out.println( initPerception.getCube().moveCube( RubikAction.backInverseAction()) );
	}

}
