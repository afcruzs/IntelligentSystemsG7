package unalcol.agents.examples.rubik.grupo7;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RubikState implements Comparable<RubikState> {
	
	private RubikCube cube;
	private RubikState parent;
	private RubikAction action;
	private double cost;
	protected int depth;
	private int hash;
	
	public RubikState(RubikCube cube, RubikState parent, RubikAction action,
			int depth, double cost) {
		this.cube = cube;
		this.parent = parent;
		this.action = action;
		this.depth = depth;
		this.cost = cost;
		this.hash = cube.toString().hashCode();
	}

	public boolean equals(Object o){
		return compareTo( (RubikState) o ) == 0;
	}
	
	public RubikCube getCube(){
		return this.cube;
	}
	
	@Override
	public int compareTo(RubikState o) {
		return cube.compareTo(o.cube);
	}

	public Iterable<RubikState> successorFunction(){
		List<RubikState> q = new LinkedList<>();
		q.add( new RubikState(cube.clone().moveCube(RubikAction.backAction()), 
				this, RubikAction.backAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.backInverseAction()), 
				this, RubikAction.backInverseAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.downAction()), 
				this, RubikAction.downAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.downInverseAction()), 
				this, RubikAction.downInverseAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.frontAction()), 
				this, RubikAction.frontAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.frontInverseAction()), 
				this, RubikAction.frontInverseAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.leftAction()), 
				this, RubikAction.leftAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.leftInverseAction()), 
				this, RubikAction.leftInverseAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.rightAction()), 
				this, RubikAction.rightAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.rightInverseAction()), 
				this, RubikAction.rightInverseAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.upAction()), 
				this, RubikAction.upAction(), depth+1, cost+1) );
		
		q.add( new RubikState(cube.clone().moveCube(RubikAction.upInverseAction()), 
				this, RubikAction.upInverseAction(), depth+1, cost+1) );
		
		
		Collections.shuffle(q);
		return q;
	}

	public RubikState getParent() {
		return parent;
	}


	public RubikAction getAction() {
		return action;
	}

	public double getCost() {
		return cost;
	}
	
	public int hashCode(){
		return hash;
	}

	
	
}
