package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
	static RubikPerception initPerception = new RubikPerception(
				new RubikCube(new int[][][]{
						{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, 10 },
							{ RubikCube.YELLOW, RubikCube.BLUE, 20 },
							{ RubikCube.YELLOW, RubikCube.YELLOW, 30 } 
						},
						{ 	{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE } 
						},
						{ 	{ RubikCube.YELLOW, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.BLUE, RubikCube.RED, RubikCube.GREEN } 
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
						
				})
			);
	
	
	
	public static void main(String[] args) {
		
		System.out.println(initPerception.getCube());
		System.out.println( initPerception.getCube().moveCube( RubikAction.rightInverseAction()) );
	}

}
