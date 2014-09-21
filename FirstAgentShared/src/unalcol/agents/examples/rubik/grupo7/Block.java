package unalcol.agents.examples.rubik.grupo7;

public class Block {
	
	public static byte NO_COLOR = -1;
	
	private int index;
	private byte up,front,right,back,left,down;
	
	public Block(int index, byte up,byte front, byte right, byte back, byte left, byte down) {
		super();
		this.index = index;
		this.up = up;
		this.right = right;
		this.back = back;
		this.left = left;
		this.down = down;
		this.front = front;
	}
	
	
	public boolean equals(Object o){
		Block t = (Block)o;
		return t.up == up && t.front == front && t.right == right 
				&& t.back == back && t.left == left && t.down == down; 
	}
	
}
