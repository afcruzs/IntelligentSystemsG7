package unalcol.agents.examples.labyrinth.teseo.grupo7;

public class Coordinate implements Comparable<Coordinate> {
	protected int x,y;

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
	}
	
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}
	
	
	@Override
	public boolean equals(Object o){
		Coordinate c = (Coordinate)o;
		return x == c.x && y == c.y;
	}
	

	@Override
	public int compareTo(Coordinate c) {
		if( x == c.x && y == c.y ) return 0;
		else if( x == c.x ) return y - c.y;
		else return x - c.x;
	}
	
	public Coordinate clone(){
		return new Coordinate(x,y);
	}
	
	
	public Coordinate coordinateTo(int orientation) {
		switch (orientation) {
		case Orientation.NORTH:
			return new Coordinate(this.x, this.y+1);
		
		case Orientation.SOUTH:
			return new Coordinate(this.x, this.y-1);
		
		case Orientation.WEST:
			return new Coordinate(this.x-1, this.y);
			
		case Orientation.EAST:
			return new Coordinate(this.x+1, this.y);	

		}
		
		return null;
	}

	public Coordinate coordToUp(Orientation orientation) {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			return new Coordinate(this.x, this.y+1);
		
		case Orientation.SOUTH:
			return new Coordinate(this.x, this.y-1);
		
		case Orientation.WEST:
			return new Coordinate(this.x-1, this.y);
			
		case Orientation.EAST:
			return new Coordinate(this.x+1, this.y);	

		}
		
		return null;
	}

	public Coordinate coordToLeft(Orientation orientation) {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			return new Coordinate(this.x-1, this.y);
		
		case Orientation.SOUTH:
			return new Coordinate(this.x+1, this.y);
		
		case Orientation.WEST:
			return new Coordinate(this.x, this.y-1);
			
		case Orientation.EAST:
			return new Coordinate(this.x, this.y+1);	

		}
		
		return null;
	}
	
	public Coordinate coordToRight(Orientation orientation) {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			return new Coordinate(this.x+1, this.y);
		
		case Orientation.SOUTH:
			return new Coordinate(this.x-1, this.y);
		
		case Orientation.WEST:
			return new Coordinate(this.x, this.y+1);
			
		case Orientation.EAST:
			return new Coordinate(this.x, this.y-1);	

		}		
		return null;
	}
	
	public Coordinate coordToDown(Orientation orientation) {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			return new Coordinate(this.x, this.y-1);
		
		case Orientation.SOUTH:
			return new Coordinate(this.x, this.y+1);
		
		case Orientation.WEST:
			return new Coordinate(this.x+1, this.y);
			
		case Orientation.EAST:
			return new Coordinate(this.x-1, this.y);	

		}		
	return null;
	}
	
	public String toString(){
		return "(x: " + x + ", y: " + y+")";
	}
}
