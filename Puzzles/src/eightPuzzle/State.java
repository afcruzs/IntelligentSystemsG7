package eightPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;

public class State implements Comparable<State>{
	private int tiles[][];
	public static int BLANK = -1;
	protected int blankX, blankY;
	
	protected static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	
	public State(int tiles[][], int x, int y){
		this.tiles = tiles;
		blankX = x;
		blankY = y;
	}
	
	public State moveTile( int action ){
		State newState = this.clone();
		switch (action) {
		case UP:
			newState.swap(newState.blankX, newState.blankY, newState.blankX, newState.blankY-1);
			newState.blankY--;
			
			break;
		
		case RIGHT:
			newState.swap(newState.blankX, newState.blankY, newState.blankX+1, newState.blankY);
			newState.blankX++;
			break;
		
		case DOWN:
			newState.swap(newState.blankX, newState.blankY, newState.blankX, newState.blankY+1);
			newState.blankY++;
			break;

		case LEFT:
			newState.swap(newState.blankX, newState.blankY, newState.blankX-1, newState.blankY);
			newState.blankX--;
			break;
		}
		
		return newState;
	}

	
	@Override
	public boolean equals(Object o){
		State s = (State)o;
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
					if( tiles[i][j] != s.tiles[i][j]  )
						return false;
		return true;
		
	}
	
	public void swap( int x1, int y1, int x2, int y2 ){
		int t = tiles[y1][x1];
		tiles[y1][x1] = tiles[y2][x2];
		tiles[y2][x2] = t;
	}
	
	public State clone(){
		int tilesNew [][] = new int[3][3];
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				tilesNew[i][j] = tiles[i][j];
		
		return new State(tilesNew, blankX, blankY);
	} 

	
	
	@Override
	public int compareTo(State o) {
		return toString().compareTo(o.toString());
	}
	
	public String toString(){
		StringBuilder out = new StringBuilder("");
		for( int i=0; i<3; i++ ){
			for (int j = 0; j < 3; j++)
				out.append( tiles[i][j] + " " );
			out.append( "\n" );
		}
		return out.toString();	
	}
}
