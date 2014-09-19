package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	
<<<<<<< HEAD
	static RubikCube cube = new RubikCube(new int[][][]{
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
=======
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
>>>>>>> eab6a469b1f64aa2e6854c43724bfa1e22f72c7a
	
	
	
	public static void main(String[] args) {
<<<<<<< HEAD
		
		System.out.println(cube);
		System.out.println( cube.moveCube( RubikAction.backAction() ) );
		System.out.println(cube);
		cube = cube.moveCube( RubikAction.backAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.downAction() );
		
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		/*cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.leftAction() );
		cube = cube.moveCube( RubikAction.rightInverseAction() ); 
		*/
		RubikSearch dls = new DepthLimitedSearch(2);
		
		/*dls = new AStarSearch(new RubikHeuristic() {
			
			@Override
			public double h(RubikState state) {
				
				return 0;
			}
		});*/
		
		long time = System.currentTimeMillis();
		System.out.println(dls.search(cube));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
=======
<<<<<<< HEAD
		
		System.out.println(initPerception.getCube());
		System.out.println( initPerception.getCube().moveCube( RubikAction.rightInverseAction()) );
=======
		//System.out.println(initPerception.getCube());
		DepthLimitedSearch dls = new DepthLimitedSearch(4);
		System.out.println(dls.search(initPerception.getCube().clone()));
>>>>>>> eab6a469b1f64aa2e6854c43724bfa1e22f72c7a
		//System.out.println(initPerception.getCube());
		//System.out.println( initPerception.getCube().moveCube( RubikAction.leftAction()) );
>>>>>>> 0e8420c96fdd734206abc6e93a31ca7940d3f5f3
	}

}
