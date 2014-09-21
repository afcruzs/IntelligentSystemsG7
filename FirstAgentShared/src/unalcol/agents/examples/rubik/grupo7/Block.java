package unalcol.agents.examples.rubik.grupo7;

public class Block {
	
	public static int NO_COLOR = -1;
	
	private int index;
	private int up,front,right,back,left,down;
	
	public Block(int index, int up,int front, int right, int back, int left, int down) {
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
