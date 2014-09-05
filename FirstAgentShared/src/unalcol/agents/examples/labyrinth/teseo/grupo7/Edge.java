package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.LinkedList;

import unalcol.agents.Action;

public class Edge {

	/*
	 * Guarda el camino *
	 */
	private LinkedList<Coordinate> road;
	
	public Edge(LinkedList<Coordinate> road) {
		this.road = road;
	}

	public int getWeight() {
		return road.size();
	}
	
	public String toString(){
		return getWeight()+"";
	}
	
}
