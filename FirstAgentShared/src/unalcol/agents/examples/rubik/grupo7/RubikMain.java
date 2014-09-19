package unalcol.agents.examples.rubik.grupo7;

import java.util.Iterator;

public class RubikMain {
	

	static RubikCube cube = new RubikCube(
			new int[][][]{

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
		
		System.out.println(cube);
		System.out.println( cube.moveCube( RubikAction.backAction() ) );
		System.out.println(cube);
		cube = cube.moveCube( RubikAction.backAction() );
		//cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.rightAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.downAction() );
		
		//cube = cube.moveCube( RubikAction.rightAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		//cube = cube.moveCube( RubikAction.upAction() );
		/*cube = cube.moveCube( RubikAction.downAction() );
		cube = cube.moveCube( RubikAction.upAction() );
		cube = cube.moveCube( RubikAction.leftAction() );
		cube = cube.moveCube( RubikAction.rightInverseAction() ); 
		*/
		RubikSearch dls = new DepthLimitedSearch(3);
		
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
	}

}
