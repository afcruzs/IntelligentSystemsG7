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
<<<<<<< HEAD
	//	cube = cube.moveCube( RubikAction.upAction() );
	//	cube = cube.moveCube( RubikAction.downAction() );
	//	cube = cube.moveCube( RubikAction.rightAction() );		
=======
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
		
		
		RubikSearch dls = new DepthLimitedSearch(4);
	
		dls = new AStarSearch(new RudeKidHeuristic());
		//dls = new AStarSearch(new CountColoursHeuristic());
		//dls = new AStarSearch(new UniformCostHeuristic());

		
		//dls = new IterativeDeepiningSearch(7);	
	//	cube = randomCube(cube, 4);
	//	System.out.println(cube);
		//System.out.println(cube);
		//dls = new DepthLimitedSearch(4);

		int n = 5;
		cube = randomCube(cube.clone(), n);
		long time;
		/*dls = new AStarSearch(new UniformCostHeuristic());
		
		long time = System.currentTimeMillis();	
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");*/
		
		System.out.println("\nMulti heuristic");
		MultiHeuristic multiHeuristic = new MultiHeuristic();
		multiHeuristic.addHeuristic( new CornersAndEdgeHeuristic() );
		multiHeuristic.addHeuristic( new CountColoursHeuristic() );
		multiHeuristic.addHeuristic( new RudeKidHeuristic() );
		multiHeuristic.addHeuristic( new Manhattan3DHeuristic() );
		dls = new AStarSearch(multiHeuristic);
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		RubikHeuristic bestHeuristic = multiHeuristic;
		int minExapanded = dls.getExpandedNodes();
		long time2 = time;
		
		System.out.println("\nCorners and Edges");
		dls = new AStarSearch(new CornersAndEdgeHeuristic());
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		if(minExapanded > dls.getExpandedNodes()){
			bestHeuristic = new CornersAndEdgeHeuristic();
			minExapanded = dls.getExpandedNodes();
			time2 = time;
		}
		
		System.out.println("\nManhattan");
		dls = new AStarSearch(new Manhattan3DHeuristic());
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		if(minExapanded > dls.getExpandedNodes()){
			bestHeuristic = new Manhattan3DHeuristic();
			minExapanded = dls.getExpandedNodes();
			time2 = time;
		}
		
		System.out.println("\nRude kid");
		dls = new AStarSearch(new RudeKidHeuristic());
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		if(minExapanded > dls.getExpandedNodes()){
			bestHeuristic = new RudeKidHeuristic();
			minExapanded = dls.getExpandedNodes();
			time2 = time;
		}
		
		System.out.println("\nCount Color");
		dls = new AStarSearch(new CountColoursHeuristic());
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		if(minExapanded > dls.getExpandedNodes()){
			bestHeuristic = new CountColoursHeuristic();
			minExapanded = dls.getExpandedNodes();
			time2 = time;
		}
		
		System.out.println("\nIterative A*");
		System.out.println(bestHeuristic.getClass().getName());
		dls = new IterativeAStar(bestHeuristic,n);
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");
		
		
		
		/*System.out.println("\nUniform Cost");
		dls = new AStarSearch(new UniformCostHeuristic());
		time = System.currentTimeMillis();
		System.out.println(dls.search(cube.clone()));
		System.out.println( dls.getExpandedNodes() + " expanded nodes." );
		time = System.currentTimeMillis() - time;
		System.out.println(time + " ms.");*/
		
		
	}

}
