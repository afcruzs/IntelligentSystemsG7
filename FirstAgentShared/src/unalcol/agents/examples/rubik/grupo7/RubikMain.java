package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
	static RubikPerception initPerception = new RubikPerception(
				new RubikCube(new int[][][]{
<<<<<<< HEAD
						{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, 10 },
							{ RubikCube.YELLOW, RubikCube.BLUE, 20 },
							{ RubikCube.YELLOW, RubikCube.YELLOW, 30 } 
						},
						{ 	{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.BLUE } 
=======
						{ 	{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE },
							{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE },
							{ RubikCube.YELLOW, RubikCube.YELLOW, RubikCube.BLUE } 
						},
						{ 	{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE },
							{ RubikCube.BLUE, RubikCube.BLUE, RubikCube.WHITE } 
>>>>>>> 0e8420c96fdd734206abc6e93a31ca7940d3f5f3
						},
						{ 	{ RubikCube.YELLOW, RubikCube.RED, RubikCube.RED },
							{ RubikCube.RED, RubikCube.RED, RubikCube.RED },
							{ RubikCube.BLUE, RubikCube.RED, RubikCube.GREEN } 
						},
<<<<<<< HEAD
						{ 	{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.GREEN, RubikCube.GREEN, RubikCube.GREEN } 
=======
						{ 	{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN },
							{ RubikCube.YELLOW, RubikCube.GREEN, RubikCube.GREEN } 
>>>>>>> 0e8420c96fdd734206abc6e93a31ca7940d3f5f3
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
<<<<<<< HEAD
		
		System.out.println(initPerception.getCube());
		System.out.println( initPerception.getCube().moveCube( RubikAction.rightInverseAction()) );
=======
		//System.out.println(initPerception.getCube());
		DepthLimitedSearch dls = new DepthLimitedSearch(4);
		System.out.println(dls.search(initPerception.getCube().clone()));
		//System.out.println(initPerception.getCube());
		//System.out.println( initPerception.getCube().moveCube( RubikAction.leftAction()) );
>>>>>>> 0e8420c96fdd734206abc6e93a31ca7940d3f5f3
	}

}
