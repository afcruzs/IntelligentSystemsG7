package unalcol.agents.examples.rubik.grupo7;

public class Manhattan3DHeuristic implements RubikHeuristic {
	
	private RubikCube goal;
	public Trio coordTo3D( int face, int x, int y ){
		switch (face) {
		case RubikCube.FRONT:
			return new Trio(y,0,2-x);
		
		case RubikCube.BACK:
			return new Trio(2-y,2,2-x);
		
		case RubikCube.RIGHT:
			return new Trio(2,y,2-x);
			
		case RubikCube.LEFT:
			return new Trio(0,2-y,2-x);
			
		case RubikCube.UP:
			return new Trio(2-y,2-x,2);
		
		case RubikCube.DOWN:
			return new Trio(y,x,0);

		default:
			throw new IllegalArgumentException("Bad cube face");
		}
	}

	@Override
	public double h(RubikState state) {
		// TODO Auto-generated method stub plz
		return 0;
	}

	@Override
	public void setGoalCube(RubikCube goal) {
		this.goal = goal;
	}

}
