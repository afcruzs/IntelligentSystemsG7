package unalcol.agents.examples.rubik.grupo7;

import java.util.LinkedList;
import java.util.Queue;

public class RubikState implements Comparable<RubikState> {
	
	private RubikCube cube;
	private RubikState parent;
	private RubikAction action;
	protected int depth;
	
	public RubikState(RubikCube cube, RubikState parent, RubikAction action,
			int depth) {
		this.cube = cube;
		this.parent = parent;
		this.action = action;
		this.depth = depth;
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
		Queue<RubikState> q = new LinkedList<>();
		//TODO as,dmnasd
		return q;
	}

	public RubikState getParent() {
		return parent;
	}


	public RubikAction getAction() {
		return action;
	}

	
	
}
