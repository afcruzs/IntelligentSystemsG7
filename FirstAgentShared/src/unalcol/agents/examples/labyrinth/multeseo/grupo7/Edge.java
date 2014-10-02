package unalcol.agents.examples.labyrinth.multeseo.grupo7;

import java.util.LinkedList;

import unalcol.agents.Action;

public class Edge {

	/*
	 * Guarda el camino *
	 */
	private LinkedList<Coordinate> path;
	
	public Edge(LinkedList<Coordinate> path) {
		this.path = path;
	}

	public LinkedList<Coordinate> getPath() {
		return path;
	}
	
	public int getWeight() {
		return path.size() + 1;
	}
	
	public String toString(){
		return getWeight()+"";
	}
	
}
