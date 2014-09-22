package unalcol.agents.examples.rubik.grupo7;

import java.util.Random;


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
	
	
	static RubikCube randomCube(RubikCube root, int n){
		RubikCube cube = root.clone();
		Random r = new Random();
		for (int i = 0; i < n; i++){
			RubikAction ra = new RubikAction(r.nextInt(12));
			System.out.println(ra);
			cube = cube.moveCube( ra );
			//System.out.println(cube);
		}
		
		return cube;
	}
	
	public static void main(String[] args) {
		
		/*
		 * R
			F
		 */
	//	System.out.println(cube);
	//	cube = cube.moveCube( RubikAction.rightAction() );
	//	cube = cube.moveCube( RubikAction.frontAction() );		
		
		//cube = cube.moveCube( RubikAction.rightInverseAction() );
		//cube = cube.moveCube( RubikAction.frontAction() );
		
	//	System.out.println(cube);
	//	cube = cube.moveCube( RubikAction.frontAction() );
		
		//System.out.println(cube);
	//	cube = cube.moveCube( RubikAction.rightAction() );
		
		/*cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.rightAction() );	*/
		
		
	//	cube = cube.moveCube( RubikAction.downAction() );
		
		//cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		/*cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.leftAction() );
		cube = cube.moveCube( RubikAction.rightInverseAction() ); 
		*/
		
		
		/*
		 * F
			R
			U'
			R'
		 */
		
		cube = cube.moveCube( RubikAction.frontAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upInverseAction() );
		cube = cube.moveCube( RubikAction.rightInverseAction() );
		
		System.out.println(cube);
		
		RubikSearch dls = new DepthLimitedSearch(4);
	
		dls = new AStarSearch(new RudeKidHeuristic());
		dls = new AStarSearch(new UniformCostHeuristic());
		
		//dls = new IterativeDeepiningSearch(7);	
	//	cube = randomCube(cube, 4);
	//	System.out.println(cube);
		//System.out.println(cube);
		//dls = new DepthLimitedSearch(4);
		
		long time = System.currentTimeMillis();
		System.out.println(dls.search(cube));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
	}

}
