package unalcol.agents.examples.rubik.grupo7;


public class RubikMain {
	

	static RubikCube cube = new RubikCube(
			new byte[][][]{

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
					
					}
				);
	
	
	
	public static void main(String[] args) {
		
		//System.out.println(cube);
		//cube = cube.moveCube( RubikAction.backAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
	//	cube = cube.moveCube( RubikAction.upAction() );
	//	cube = cube.moveCube( RubikAction.downAction() );
	//	cube = cube.moveCube( RubikAction.rightAction() );		
	//	cube = cube.moveCube( RubikAction.downAction() );
		
		//cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		/*cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.leftAction() );
		cube = cube.moveCube( RubikAction.rightInverseAction() ); 
		*/
		
		System.out.println(cube);
		RubikSearch dls = new DepthLimitedSearch(5);
	
		dls = new AStarSearch(new RudeKidHeuristic());
		//dls = new AStarSearch(new CountColoursHeuristic());
		//dls = new AStarSearch(new UniformCostHeuristic());
		
		//dls = new IterativeDeepiningSearch(7);
		
		long time = System.currentTimeMillis();
		System.out.println(dls.search(cube));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
	}

}
