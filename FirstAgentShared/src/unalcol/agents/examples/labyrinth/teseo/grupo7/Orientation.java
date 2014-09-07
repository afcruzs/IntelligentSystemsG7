package unalcol.agents.examples.labyrinth.teseo.grupo7;

public class Orientation {
	public static final int NORTH = 0;
	public static final int WEST = 3; //izquierda	
	public static final int SOUTH = 2;
	public static final int EAST = 1; //derecha
	
	protected int orientation;

	public Orientation() {
		super();
		this.orientation = NORTH;
	}
	
	private Orientation(int o){
		this.orientation = o;
	}
	
	public void goToWest(){
		orientation = (orientation + WEST) % 4;
		//orientation = ( 4 - (orientation - WEST) )%4;
	}
	
	public void goToEast(){
		orientation = (orientation + EAST) % 4;
		//orientation = ( 4 - (orientation - EAST) )%4;
	}
	
	public void goToNorth(){
		orientation = (orientation + NORTH) % 4;
		//orientation = ( 4 - (orientation - NORTH) )%4;
	}
	
	public void goToSouth(){
		orientation = (orientation + SOUTH) % 4;
		//orientation = ( 4 - (orientation - SOUTH) )%4;
	}
	
	public String toString(){
		return String.valueOf(orientation);
	}
	
	public Orientation clone(){
		return new Orientation(this.orientation);
	}
	
	
}	
