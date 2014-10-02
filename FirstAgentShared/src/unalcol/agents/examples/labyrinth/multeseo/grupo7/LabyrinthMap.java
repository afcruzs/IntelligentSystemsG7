package unalcol.agents.examples.labyrinth.multeseo.grupo7;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;
import sun.misc.CRC16;

public class LabyrinthMap {
	
	protected TreeMap<Coordinate, TreeMap<Coordinate, Edge> > graph;
	protected TreeMap<Coordinate, Coordinate> visit,updatedCoordinates;
	
	
	
	public LabyrinthMap(  ) {
		graph = new TreeMap<>();
		visit = new TreeMap<>();
		updatedCoordinates = new TreeMap<>();
		
	}
	
	public Coordinate randomNode(){
		int idx = new Random().nextInt( graph.size() );
		int i = 0;
		Coordinate d = null;
		for( Coordinate c : graph.keySet() ){
			if( idx == i )
				return c;
			i++;
			d = c;
		}
		
		return d;
	}
	
	public boolean addEdge(Coordinate lastCriticalCoordinate, Coordinate current,
			LinkedList<Coordinate> path) {
		boolean ret = false;
		lastCriticalCoordinate = lastCriticalCoordinate.clone();
		current = current.clone();
		
		if( graph.get(lastCriticalCoordinate) == null ){
			ret = true;
			graph.put(lastCriticalCoordinate, new TreeMap<Coordinate, Edge>() );
		}
		
		if( graph.get(current) == null ){
			ret = true;
			graph.put(current, new TreeMap<Coordinate, Edge>() );
		}
		/*
		System.out.println( graph.get(lastCriticalCoordinate) );
		System.out.println( graph.get(current) );
		*/
		graph.get(lastCriticalCoordinate).put(current, new Edge( path ) );
		graph.get(current).put(lastCriticalCoordinate, new Edge( reverse(path) ) );
		
		updatedCoordinates.put(lastCriticalCoordinate, lastCriticalCoordinate);
		updatedCoordinates.put(current, current);
		return ret;
	}
	
	
	
	private LinkedList<Coordinate> reverse( LinkedList<Coordinate> l ) {
		LinkedList<Coordinate> ll = new LinkedList<>();
		for( Coordinate c : l )
			ll.add(0, c);
		return ll;
	}
	
	public String  toString() {
		return graph.toString();
	}

	public TreeMap<Coordinate,Edge> getNeighbors(Coordinate coordinate) {
		return graph.get(coordinate);
	}
	
	public LinkedList<Coordinate> getPath( Coordinate from, Coordinate to ) {
		
		return graph.get(from).get(to).getPath();
	}

	
	public boolean isEdge( Coordinate from, Coordinate to ){
		if( graph.get(from) == null || graph.get(to) == null ) return false;
		
		return graph.get(from).get(to) != null;
	}

	public int size() {
		return graph.size();
	}

	public void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		TreeMap<Coordinate, Edge> current = graph.get(current2);
		current.remove(lastCriticalCoordinate2);
		TreeMap<Coordinate, Edge> lastCriticalCoordinate = graph.get(lastCriticalCoordinate2);
		lastCriticalCoordinate.remove(current2);
		
		if( current.isEmpty() )
			graph.remove(current2);
		if( lastCriticalCoordinate.isEmpty() )
			graph.remove(lastCriticalCoordinate2);
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
	public int getWeight(Coordinate from, Coordinate to) {
		// TODO Auto-generated method stub
		return getPath(from, to).size()+2;
	}

}
