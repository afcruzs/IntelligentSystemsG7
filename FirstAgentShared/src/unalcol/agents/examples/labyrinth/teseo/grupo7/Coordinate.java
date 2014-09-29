package unalcol.agents.examples.labyrinth.teseo.grupo7;

public class Coordinate implements Comparable<Coordinate> {
	protected int x, y, amount;
	protected boolean FW, RW, BW, LW;
	protected Orientation orientation;
	
	public Coordinate(int x, int y, int amount,
			boolean FW, boolean RW, boolean BW, boolean LW, 
			Orientation orientation) {
		super();
		this.x = x;
		this.y = y;
		this.amount = amount;
		this.FW = FW;
		this.RW = RW;
		this.BW = BW;
		this.LW = LW;
		this.orientation = orientation;
	}
	
	public void updateInfo(int amount, boolean FW, boolean RW, 
			boolean BW, boolean LW, Orientation orientation){
			
		this.amount = amount;
		this.FW = FW;
		this.RW = RW;
		this.BW = BW;
		this.LW = LW;
		this.orientation = orientation;
	}
	
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
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
	

	public int getAmount() {
		return amount;
	}
	
	public void updateAmount( int amount ) {
		this.amount = amount;
	}
	
	public Coordinate clone(){
		return new Coordinate(x,y,amount,FW, RW, BW, LW, orientation);
	}
	
	private boolean genericCheck(int tempOrientation){
		switch (tempOrientation) {
		case Orientation.EAST:
			return RW;
			
		case Orientation.WEST:
			return LW;
		
		case Orientation.NORTH:
			return FW;
		
		default: //South
			return BW;
		
		}
		
	}
	
	public boolean verifyLeftWall( int orientation ){
		//return genericCheck(( orientation.orientation + Orientation.WEST ) % 4);
		switch (orientation) {
		case Orientation.WEST:
				switch (this.orientation.orientation) {
				
				case Orientation.NORTH:
					return BW;
				
				case Orientation.WEST:
						return LW;	
						
				case Orientation.EAST:
						return RW;
					//break;
				default: //South
					return FW;
				}
		
		case Orientation.EAST:
				switch (this.orientation.orientation) {
				
				case Orientation.NORTH:
					return FW;
				
				case Orientation.WEST:
						return RW;	
						
				case Orientation.EAST:
						return LW;
					//break;
				default: //South
					return BW;
				}
				
		case Orientation.NORTH:
				switch (this.orientation.orientation) {
				
					case Orientation.NORTH:
						return LW;
					
					case Orientation.WEST:
							return FW;	
							
					case Orientation.EAST:
							return BW;
						//break;
					default: //South
						return RW;
				}
		default:  //South
			
			switch (this.orientation.orientation) {
			
				case Orientation.NORTH:
					return RW;
				
				case Orientation.WEST:
						return BW;	
						
				case Orientation.EAST:
						return FW;
					//break;
				default: //South
					return LW;
			}
		}
	}
	
	public boolean verifyRightWall( int orientation ){
		//return genericCheck((orientation.orientation + Orientation.EAST ) % 4);
		switch (orientation) {
		case Orientation.NORTH:
			
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return BW;
			
			case Orientation.EAST:
					return FW;
			
			case Orientation.NORTH:
					return RW;

			default: //South
					return LW;
			
			}

		case Orientation.WEST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return RW;
			
			case Orientation.EAST:
					return LW;
			
			case Orientation.NORTH:
					return FW;

			default: //South
					return BW;
			
			}
		
		case Orientation.EAST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return LW;
			
			case Orientation.EAST:
					return RW;
			
			case Orientation.NORTH:
					return BW;

			default: //South
					return FW;
			
			}
			
		default:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return FW;
			
			case Orientation.EAST:
					return BW;
			
			case Orientation.NORTH:
					return LW;

			default: //South
					return RW;
			
			}
		}
	}
	
	public boolean verifyBackWall( int orientation ){
		//return genericCheck((orientation.orientation + Orientation.SOUTH ) % 4);
		switch (orientation) {
		case Orientation.NORTH:
			
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return LW;
			
			case Orientation.EAST:
					return RW;
			
			case Orientation.NORTH:
					return BW;

			default: //South
					return FW;
			
			}

		case Orientation.WEST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return BW;
			
			case Orientation.EAST:
					return FW;
			
			case Orientation.NORTH:
					return RW;

			default: //South
					return LW;
			
			}
		
		case Orientation.EAST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return FW;
			
			case Orientation.EAST:
					return BW;
			
			case Orientation.NORTH:
					return LW;

			default: //South
					return RW;
			
			}
			
		default:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return RW;
			
			case Orientation.EAST:
					return LW;
			
			case Orientation.NORTH:
					return FW;

			default: //South
					return BW;
			
			}
		}
	}
	
	public boolean verifyFrontWall( int orientation ){
		//return genericCheck((orientation.orientation + Orientation.NORTH ) % 4);
		switch (orientation) {
		case Orientation.NORTH:
			
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return RW;
			
			case Orientation.EAST:
					return LW;
			
			case Orientation.NORTH:
					return FW;

			default: //South
					return BW;
			
			}

		case Orientation.WEST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return FW;
			
			case Orientation.EAST:
					return BW;
			
			case Orientation.NORTH:
					return LW;

			default: //South
					return RW;
			
			}
		
		case Orientation.EAST:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return BW;
			
			case Orientation.EAST:
					return FW;
			
			case Orientation.NORTH:
					return RW;

			default: //South
					return LW;
			
			}
			
		default:
			switch (this.orientation.orientation) {
			case Orientation.WEST:
					return LW;
			
			case Orientation.EAST:
					return RW;
			
			case Orientation.NORTH:
					return BW;

			default: //South
					return FW;
			
			}
		}
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
		return "(x: " + x + ", y: " + y + ")";
	}
}
