package unalcol.agents.examples.rubik.grupo7;

import java.util.List;
import java.util.TreeMap;

public class Manhattan3DHeuristic implements RubikHeuristic {
	
	private List<Block> goalBlocks;

	private TreeMap<Block, Trio> correctBlocks;
	

	@Override
	public double h(RubikState state) {
		List<Block> stateBlock = state.getCube().getBlocks();
		double manhattan = 0.0;
		for(int i=0; i<stateBlock.size(); i++){
				manhattan += distance3D(stateBlock.get(i));
		}
		return manhattan/8.0;
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
