package eightPuzzle;

public class ActionState {
	protected int action;
	protected State nextState;
	
	public ActionState(int action, State nextState) {
		super();
		this.action = action;
		this.nextState = nextState;
	}
}
