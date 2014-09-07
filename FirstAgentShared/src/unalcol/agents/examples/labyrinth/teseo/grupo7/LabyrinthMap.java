package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

public class LabyrinthMap {
	
	protected TreeMap<Coordinate, TreeMap<Coordinate, Edge> > graph;
	protected TreeMap<Coordinate, Coordinate> visit,updatedCoordinates;
	
	
	
	public LabyrinthMap(  ) {
		graph = new TreeMap<>();
		visit = new TreeMap<>();
		updatedCoordinates = new TreeMap<>();
		
	}
	public void addEdge(Coordinate lastCriticalCoordinate, Coordinate current,
			LinkedList<Coordinate> path) {
		
		lastCriticalCoordinate = lastCriticalCoordinate.clone();
		current = current.clone();
		
		if( graph.get(lastCriticalCoordinate) == null )
			graph.put(lastCriticalCoordinate, new TreeMap<Coordinate, Edge>() );
		
		if( graph.get(current) == null )
			graph.put(current, new TreeMap<Coordinate, Edge>() );
		/*
		System.out.println( graph.get(lastCriticalCoordinate) );
		System.out.println( graph.get(current) );
		*/
		graph.get(lastCriticalCoordinate).put(current, new Edge( path ) );
		graph.get(current).put(lastCriticalCoordinate, new Edge( reverse(path) ) );
		
		updatedCoordinates.put(lastCriticalCoordinate, lastCriticalCoordinate);
		updatedCoordinates.put(current, current);
	}
	
	
	
	private LinkedList<Coordinate> reverse( LinkedList<Coordinate> l ) {
		LinkedList<Coordinate> ll = new LinkedList<>();
		for( Coordinate c : l )
			ll.add(0, c);
		return ll;
	}
	
	public String  toString(){
		return graph.toString();
	}

	public TreeMap<Coordinate,Edge> getNeighbors(Coordinate coordinate) {
		return graph.get(coordinate);
	}
	
	public LinkedList<Coordinate> getPath( Coordinate from, Coordinate to ) {
		return graph.get(from).get(to).getPath();
	}

	public int size() {
		return graph.size();
	}

	public void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		System.out.println( "Breakeada " + current2 + " " + lastCriticalCoordinate2 + " " + graph.get(current2).remove(lastCriticalCoordinate2) );
		
		System.out.println( "Breakeada " + current2 + " " + lastCriticalCoordinate2 + " "+ graph.get(lastCriticalCoordinate2).remove(current2) );
	}
	
	public boolean contains(Coordinate c) {
		if(c == null)
			 return false;
		return graph.containsKey(c);
	}
	
	public Coordinate getKey(Coordinate coordinate) {
		
		return updatedCoordinates.get(coordinate);
		/*for( Coordinate c: graph.keySet() )
			if( c.equals(coordinate) )
				return c;
		return null;*/
	}

}
