package unalcol.agents.examples.rubik.grupo7;

import java.util.ArrayList;
import java.util.List;
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
		List<RubikAction> actions = new ArrayList<>(n);
		for (int i = 0; i < n; i++){
			RubikAction ra = new RubikAction(r.nextInt(12));
			cube = cube.moveCube( ra );
			actions.add(ra);
		}
		System.out.println(actions + "\n");
		return cube;
	}
	
	public static void main(String[] args) {
		
		int n = 8;
		cube = randomCube(cube.clone(), n);
		RubikAgent agent = new RubikAgent();
		
		agent.setSearch( new AStarSearch(new RudeKidHeuristic()) );
		System.out.println( agent.solve(cube.clone()) + "\n" );
		
		agent.setSearch( new AStarSearch(new CountColoursHeuristic()) );
		System.out.println( agent.solve(cube.clone()) + "\n"  );
		
		agent.setSearch( new AStarSearch(new Manhattan3DHeuristic()) );
		System.out.println( agent.solve(cube.clone()) + "\n"  );
		
		MultiHeuristic multi = new MultiHeuristic();
		multi.addHeuristic(new RudeKidHeuristic());
		multi.addHeuristic(new CountColoursHeuristic());
		multi.addHeuristic(new Manhattan3DHeuristic());
		agent.setSearch( new AStarSearch(multi) );
		System.out.println( agent.solve(cube.clone()) + "\n"  );
		
		agent.setSearch(new IterativeDeepiningSearch(n));
		System.out.println( agent.solve(cube.clone()) + "\n"  );
		
		agent.setSearch(new UniformCostSearch());
		System.out.println( agent.solve(cube.clone()) + "\n"  );
		
		/*
		 * [F', D, R', F', D, D', F', B]

			A* - Rude kid heuristic
			The depth is: 6
			1268 expanded nodes.
			811ms elapsed.
			[F', F', B', R, D', F]
			
			A* - Count colors Heuristic
			The depth is: 6
			2737 expanded nodes.
			791ms elapsed.
			[F', B', F', R, D', F]
			
			A* - Manhattan 3D Heuristic
			The depth is: 6
			20700 expanded nodes.
			9773ms elapsed.
			[F', B', F', R, D', F]
			
			A* - MultiHeuristic
			The depth is: 6
			431 expanded nodes.
			199ms elapsed.
			[F', B', F', R, D', F]
			
			Iterative Deepining Search
			The depth is: 8
			1063279 expanded nodes.
			483123ms elapsed.
			[F, B', B', F, B, R, D', F]
			
			Uniform Cost Search
			The depth is: 6
			17435 expanded nodes.
			4490ms elapsed.
			[F', B', F', R, D', F]

		 */
		
	}

}
