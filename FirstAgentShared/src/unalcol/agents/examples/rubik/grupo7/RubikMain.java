package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
	static RubikPerception initPerception = new RubikPerception(
				new RubikCube(new int[][][]{
						{ 	{ RubikCube.RED, RubikCube.YELLOW, RubikCube.GREEN },
							{ RubikCube.YELLOW, RubikCube.BLUE, RubikCube.YELLOW },
							{ RubikCube.ORANGE, RubikCube.YELLOW, RubikCube.WHITE } 
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
						{ 	{ RubikCube.YELLOW, RubikCube.WHITE, RubikCube.WHITE },
							{ RubikCube.WHITE, RubikCube.WHITE, RubikCube.WHITE },
							{ RubikCube.RED, RubikCube.WHITE, RubikCube.GREEN } 
						},
						
				})
			);
	
	
	
	public static void main(String[] args) {
		
		System.out.println(initPerception.getCube());
		System.out.println( initPerception.getCube().moveCube( RubikAction.downInverseAction() ) );
	}

}
