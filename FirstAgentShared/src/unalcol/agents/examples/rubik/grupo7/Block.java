package unalcol.agents.examples.rubik.grupo7;

import java.util.Arrays;

public class Block implements Comparable<Block> {
	
	public static byte NO_COLOR = -1;
	
	private int index;
	private byte up,front,right,back,left,down;
	protected int x,y,z;
	
	public Block(int index, byte up,byte front, byte right, byte back, byte left, byte down) {
		super();
		this.index = index;
		this.up = up;
		this.right = right;
		this.back = back;
		this.left = left;
		this.down = down;
		this.front = front;
		
		
		
		byte cmp[] = { up, right, back, left, down, front };
		Arrays.sort(cmp);
		this.up = cmp[5];
		this.right = cmp[4];
		this.back = cmp[3];
		this.left = cmp[2];
		this.down = cmp[1];
		this.front = cmp[0];
		
		
		switch (index) {
		case 0:
			x = y = z = 0;
			break;
		
		case 1:
			x = y = 0; z = 1;
			break;
		
		case 2:
			x = y = 0; z = 2;
			break;
		
		case 3:
			x =  0; y = 1; z = 0;
			break;
		
		case 4:
			x =  0; y = 1; z = 2;
			break;
		
		case 5:
			x =  0; y = 2; z = 0;
			break;
		
		case 6:
			x =  0; y = 2; z = 1;
			break;
		
		case 7:
			x =  0; y = 2; z = 2;
			break;
		
		case 8:
			x = 1; y = 2; z = 0;
			break;
		
		case 9:
			x = 1; y = 2; z = 2;
			break;
		
		case 10:
			x = 2; y = 2; z = 0;
			break;
		
		case 11:
			x = 2; y = 2; z = 1;
			break;
		
		case 12:
			x = 2; y = 2; z = 2;
			break;
		
		case 13:
			x = 1; y = 0; z = 2;
			break;
		
		case 14:
			x = 2; y = 1; z = 2;
			break;
		
		case 15:
			x = 2; y = 0; z = 2;
			break;
		
		case 16:
			x = 1; y = 0; z = 0;
			break;
		
		case 17:
			x = 2; y = 0; z = 1;
			break;
		
		case 18:
			x = 2; y = 0; z = 0;
			break;
		
		case 19:
			x = 2; y = 1; z = 0;
			break;
			
		default:
			throw new IllegalArgumentException("bad index in block: " + index);
		}
	}
	
	
	public boolean equals(Object o){
		Block t = (Block)o;
		return t.up == up && t.front == front && t.right == right 
				&& t.back == back && t.left == left && t.down == down; 
	}


	@Override
	public int compareTo(Block o) {
		
		if( o.equals(this) ) return 0;
		
		if( x == o.x )
			if( y == o.y )
				return z - o.z;
			else return y - o.y;
		
		return x - o.x;
	}
	
}
