package eightPuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

public class Solver {
	
	protected static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;	
	static State goalState = new State(new int[][]{ 
													{1,2,3},
													{4,5,6},
													{7,8,State.BLANK} }, 2, 2 );
	
	static State rootState = new State(new int[][]{ 
													{7,2,4},
													{5,State.BLANK,6},
													{8,3,1} }, 1, 1 );
/*
	static State rootState = new State(new int[][]{ 
													{1,2,3},
													{5,State.BLANK,6},
													{4,7,8} }, 1, 1 );
*/
	static LinkedList<ActionState> succesorFunction(State state) {
		LinkedList<ActionState> nextStates = new LinkedList<>();
		if( state.blankY > 0 )
			nextStates.add( new ActionState(UP, state.moveTile(UP)) );
		
		if( state.blankY < 2 )
			nextStates.add( new ActionState(DOWN, state.moveTile(DOWN)) );
		
		if( state.blankX > 0 )
			nextStates.add( new ActionState(LEFT, state.moveTile(LEFT)) );
		
		if( state.blankX < 2 )
			nextStates.add( new ActionState(RIGHT, state.moveTile(RIGHT)) );
		
		return nextStates;
	}

	static boolean testGoal( State state ) {
		return state.equals(goalState);
	}
	
	static List<Integer> BFS( State init ){
		Queue<Node> q = new LinkedList<>();
		TreeSet<Node> visit = new TreeSet<>();
		Node current = new Node(init, null, -1, 0, 0);
		q.add(current);
		visit.add(current);
		while(q.size() > 0){
			current = q.poll();
			//System.out.println(current);
			for( ActionState as : succesorFunction(current.state) ){
				Node n = new Node(as.nextState, current, as.action, current.depth + 1, 1);
				if( !visit.contains(n) ) {
					if( testGoal(as.nextState) )
						return solution(n);
					visit.add(n);	
					q.add(n);
				}
			}
		}
		
		return new LinkedList<>();
	}

	static List<Integer> DFS( State init ){
		Stack<Node> s = new Stack<>();
		TreeSet<Node> visit = new TreeSet<>();
		Node current = new Node(init, null, -1, 0, 0);
		s.add(current);
		visit.add(current);
		while(s.size() > 0){
			current = s.pop();
			for( ActionState as : succesorFunction(current.state) ){
				Node n = new Node(as.nextState, current, as.action, current.depth + 1, 1);
				if( !visit.contains(n) ) {
					if( testGoal(as.nextState) )
						return solution(n);
					visit.add(n);	
					s.add(n);
				}
			}
		}
		return new LinkedList<>();
	}
	
	static List<Integer> limitedDFS( State init, int l ){
		Stack<Node> s = new Stack<>();
		TreeSet<Node> visit = new TreeSet<>();
		Node current = new Node(init, null, -1, 0, 0);
		s.add(current);
		visit.add(current);
		while(s.size() > 0){
			current = s.pop();
			if( current.depth >= l ) continue;
			for( ActionState as : succesorFunction(current.state) ){
				Node n = new Node(as.nextState, current, as.action, current.depth + 1, 1);
				if( !visit.contains(n) ) {
					if( testGoal(as.nextState) )
						return solution( n );
					visit.add(n);	
					s.add(n);
				}
			}
		}
		return null;
	}
	
	static List<Integer> iterativeDeepining( State init, int max ){
		
		List<Integer> ans = null;
		for( int i=0; i<=max; i++ ){
			ans = limitedDFS(init, i);
			if( ans != null ) return ans;
		}
		
		return null;
	}
	
	private static List<Integer> solution( Node n ) {
		LinkedList<Integer> l = new LinkedList<>();
		while( n.parent != null ){
			l.add(0, n.action);
			n = n.parent;
		}
		return l;
	}
	
	private static void tester( State init, List<Integer> list ) {
		State current = init;
		for( Integer action: list ) {
			System.out.println(current);
			current = current.moveTile(action);
		}
		System.out.println(current);
	}
	/*
	 * 
	 * 7 2 4
	 * 5   6
	 * 8 3 1
	 * BFS: 4149 ms, 20 movements.
	   DFS: 11046 ms, 66056 movements.
	   Limited DFS (max depth = 28): 1007 ms, 26 movements.
	   Iterative Deepining (max depth = 28): 11951 ms, 26 movements.
	   

	   Limited DFS (max depth = 50): 2103 ms, 50 movements.
       Iterative Deepining (max depth = 50): 12035 ms, 26 movements.
	   

	 */
	public static void main(String[] args) {
		List<Integer> ans = null;
		long time = System.currentTimeMillis(), time2;
		int size = BFS(rootState).size();
		time2 = System.currentTimeMillis();
		System.out.println( "BFS: " + (time2-time) + " ms, " + size + " movements." );
		time = time2;
		size = DFS(rootState).size();
		time2 = System.currentTimeMillis();
		System.out.println( "DFS: " + (time2-time) + " ms, " + size + " movements." );
		time = time2;
		ans = limitedDFS(rootState,50);
		time2 = System.currentTimeMillis();
		if(ans != null)
			System.out.println( "Limited DFS (max depth = 28): "+(time2-time) + " ms, " + ans.size() + " movements." );
		else System.out.println("No solution found with Limited Depth Search and given depth");
		time = time2;
		ans = iterativeDeepining(rootState,50);
		time2 = System.currentTimeMillis();
		if(ans != null)
			System.out.println( "Iterative Deepining (max depth = 28): " + (time2-time) + " ms, " + ans.size() + " movements." );
		else System.out.println("No solution found with Iterative Deepining and given depth");
	}

}