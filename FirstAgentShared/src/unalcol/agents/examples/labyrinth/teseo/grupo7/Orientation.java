package unalcol.agents.examples.labyrinth.teseo.grupo7;

public class Orientation {
	public static final int NORTH = 0;
	public static final int WEST = 1; //derecha	
	public static final int SOUTH = 2;
	public static final int EAST = 3; //izquierda
	
	protected int orientation;

	public Orientation() {
		super();
		this.orientation = NORTH;
	}
	
	public void goToLeft(){
		orientation = (orientation + EAST) % 4;
	}
	
	public void goToRight(){
		orientation = (orientation + WEST) % 4;
	}
	
	public void goToNorth(){
		orientation = (orientation + NORTH) % 4;
	}
	
	public void goToSouth(){
		orientation = (orientation + SOUTH) % 4;
	}
	
	public String toString(){
		return String.valueOf(orientation);
	}
	
}	
