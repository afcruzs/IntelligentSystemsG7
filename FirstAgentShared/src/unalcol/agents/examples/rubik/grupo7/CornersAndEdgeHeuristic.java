package unalcol.agents.examples.rubik.grupo7;

import java.util.List;
import java.util.TreeMap;

public class CornersAndEdgeHeuristic implements RubikHeuristic {
	private List<Block> goalBlocks;
	private TreeMap<Block, Trio> correctBlocks;

	@Override
	public double h(RubikState state) {
		List<Block> stateBlock = state.getCube().getBlocks();
		double cubies = 0.0, edges = 0.0;
		cubies += distance3D(stateBlock.get(0));
		cubies += distance3D(stateBlock.get(2));
		cubies += distance3D(stateBlock.get(5));
		cubies += distance3D(stateBlock.get(7));
		cubies += distance3D(stateBlock.get(10));
		cubies += distance3D(stateBlock.get(12));
		cubies += distance3D(stateBlock.get(15));
		cubies += distance3D(stateBlock.get(18));
		
		edges += distance3D(stateBlock.get(1));
		edges += distance3D(stateBlock.get(3));
		edges += distance3D(stateBlock.get(4));
		edges += distance3D(stateBlock.get(6));
		edges += distance3D(stateBlock.get(8));
		edges += distance3D(stateBlock.get(9));
		edges += distance3D(stateBlock.get(11));
		edges += distance3D(stateBlock.get(13));
		edges += distance3D(stateBlock.get(14));
		edges += distance3D(stateBlock.get(16));
		edges += distance3D(stateBlock.get(17));
		edges += distance3D(stateBlock.get(18));
		edges += distance3D(stateBlock.get(19));
		
		cubies = cubies/4.0;
		edges = edges/4.0;
		
		
		return Math.max(cubies, edges);
	}
	
	

	private double distance3D(Block block) {
		int x = block.x;
		int y = block.y;
		int z = block.z;
		
		Trio block2 = this.correctBlocks.get(block);
		int x1 = block2.x;
		int y1 = block2.y;
		int z1 = block2.z;
		
		return Math.abs(x-x1) + Math.abs(y-y1) + Math.abs(z-z1);
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		this.goalBlocks = goal.getBlocks();
		correctBlocks = new TreeMap<>();
		for( Block block : this.goalBlocks ){
			correctBlocks.put(block, new Trio(block.x,block.y,block.z));
		}
	}
}
