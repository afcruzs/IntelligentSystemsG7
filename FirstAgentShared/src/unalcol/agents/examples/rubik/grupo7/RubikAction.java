package unalcol.agents.examples.rubik.grupo7;

public class RubikAction {
	
	public static final int UP = 0;
	public static final int UP_INVERSE = 1;
	
	public static final int FRONT = 2;
	public static final int FRONT_INVERSE = 3;
	
	public static final int RIGHT = 4;
	public static final int RIGHT_INVERSE = 5;
	
	public static final int BACK = 6;
	public static final int BACK_INVERSE = 7;
	
	public static final int LEFT = 8;
	public static final int LEFT_INVERSE = 9;
	
	public static final int DOWN = 10;
	public static final int DOWN_INVERSE = 11;
	
	protected int action;

	private RubikAction(int action) {
		this.action = action;
	}
	
	public static RubikAction upAction(){
		return new RubikAction(UP);
	}
	
	public static RubikAction upInverseAction(){
		return new RubikAction(UP_INVERSE);
	}
	
	public static RubikAction frontAction(){
		return new RubikAction(FRONT);
	}
	
	public static RubikAction frontInverseAction(){
		return new RubikAction(FRONT_INVERSE);
	}
	
	public static RubikAction backAction(){
		return new RubikAction(BACK);
	}
	
	public static RubikAction backInverseAction(){
		return new RubikAction(BACK_INVERSE);
	}
	
	public static RubikAction rightAction(){
		return new RubikAction(RIGHT);
	}
	
	public static RubikAction rightInverseAction(){
		return new RubikAction(RIGHT_INVERSE);
	}
	
	public static RubikAction leftAction(){
		return new RubikAction(LEFT);
	}
	
	public static RubikAction leftInverseAction(){
		return new RubikAction(LEFT_INVERSE);
	}
	
	public static RubikAction downAction(){
		return new RubikAction(DOWN);
	}
	
	public static RubikAction downInverseAction(){
		return new RubikAction(DOWN_INVERSE);
	}
	
	public boolean equals(Object o){
		return action == ((RubikAction)o).action;
	}
	
	public String toString(){
		switch (action) {
		case UP:
			return "U";
		case UP_INVERSE:
			return "U'";
		case LEFT:
			return "L";
		case LEFT_INVERSE:
			return "L'";
		case RIGHT:
			return "R";
		case RIGHT_INVERSE:
			return "R'";
		case DOWN:
			return "D";
		case DOWN_INVERSE:
			return "D'";
		case BACK:
			return "B";
		default: //BACK_INVERSE
			return "B'";
		}
	}
	
}
