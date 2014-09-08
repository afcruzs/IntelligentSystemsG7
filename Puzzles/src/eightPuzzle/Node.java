package eightPuzzle;

public class Node implements Comparable<Node> {
	
	State state;
	Node parent;
	int action, depth, pathCost;
	
	public Node(State state, Node parent, int action, int depth, int pathCost) {
		super();
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.depth = depth;
		this.pathCost = pathCost;
	}
	
	@Override
	public boolean equals(Object justInCase){
		return state.equals( ((Node)(justInCase)).state );
	}

	@Override
	public int compareTo(Node o) {
		return state.compareTo(o.state);
	}
	
	public String toString(){
		return "\n"+state.toString();
	}
	
}
