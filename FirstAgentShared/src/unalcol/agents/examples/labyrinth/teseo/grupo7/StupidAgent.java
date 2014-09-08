package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;


public class StupidAgent implements AgentProgram {
	protected SimpleLanguage language;
	protected Vector<String> cmd = new Vector<String>();
	protected Orientation orientation;
	
	protected Coordinate current = new Coordinate(0,0);
	protected Coordinate lastCriticalCoordinate = null;
	protected Random r = new Random();
	
	protected final int NO_OP = 0;
	protected final int DIE = 1;
	protected final int ADVANCE = 2;
	protected final int ROTATE = 3;
	
	protected final int GOTO_SIGNAL = -2;
	protected final int INFINITY = 1000000000;
	

	protected LinkedList<Coordinate> pathInBuilding = new LinkedList<>();

	protected LabyrinthMap map = new LabyrinthMap();
	protected Debug debug;

	public StupidAgent(){
		init();
	}
	public void setLanguage(SimpleLanguage _language) {
		language = _language;
	}

	public void init() {
		cmd = new Vector<>();
		orientation = new Orientation();
		r = new Random();
		current = new Coordinate(0,0);
		lastCriticalCoordinate = null;
		map = new LabyrinthMap();
		pathInBuilding = new LinkedList<>();
	}
	
	public int rotate(boolean FW, boolean RW, boolean BW, boolean LW, boolean T) {
		debug.repaint();
		if (T)
			return -1;		
		
		int rot = 0, amount = 0;
		if (LW == false) { amount++; }
		if (RW == false) { amount++; }
		if (FW == false) { amount++; }
		if (BW == false) { amount++; }
		
		int visited = 0;
		ArrayList<Integer> posibleDirections = new ArrayList<>(); 
		if (LW == false && map.visit.containsKey(current.coordToLeft(orientation)) ) { visited++; }
		if (RW == false && map.visit.containsKey(current.coordToRight(orientation)) ) { visited++; }
		if (FW == false && map.visit.containsKey(current.coordToUp(orientation)) ) { visited++; }
		if (BW == false && map.visit.containsKey(current.coordToDown(orientation)) ) { visited++; }
		
		if (LW == false && !map.visit.containsKey(current.coordToLeft(orientation)) ) { posibleDirections.add(3); }
		if (RW == false && !map.visit.containsKey(current.coordToRight(orientation)) ) { posibleDirections.add(1); }
		if (FW == false && !map.visit.containsKey(current.coordToUp(orientation)) ) { posibleDirections.add(0); }
		if (BW == false && !map.visit.containsKey(current.coordToDown(orientation)) ) { posibleDirections.add(2); }
		
		current.updateInfo(amount, FW, RW, BW, LW, orientation.clone() );
		map.visit.put(current.clone(), current.clone());
		//System.out.println(current + " -- " + amount + " " + visited + " " + posibleDirections);
		
		
		
		//Cantidad de paredes libres es mas que dos es un critical node
		//si la cantidad disponible es mayor que los visitados
		//hay algun camino posible
		if (visited > amount) JOptionPane.showMessageDialog(null, "THIS IS SHIIIT ");
		
		if (amount > 2 && amount > visited){
			save(true);
			verifyAdjacents();
		//En este caso ya todo esta visitado, se debe guardar
		//y luego ir al nodo mas cercano que tenga opciones de explorar algo nuevo
		}else if( amount > 2 && amount == visited ) {
			save(true);
			verifyAdjacents();
			lastCriticalCoordinate = goToClosestOpenNode();
			//lastCriticalCoordinate = current.clone();
			return GOTO_SIGNAL;
		}
		//En este caso vamos por un tunel 
		else if(amount == 2 && visited == 1)
			pathInBuilding.add( current.clone() );
		//En este caso vamos por el tunel pero nos encontramos con un 
		//pedazo del tunel que ya ha sido visitado
		else if(amount == 2 && visited == 2) {
			//JOptionPane.showMessageDialog(null, "caso");
			save(false);
			verifyAdjacents();
			if( current.equals(lastCriticalCoordinate) )
				System.out.println("iguales");
			
			Coordinate next;
			lastCriticalCoordinate = goToClosestOpenNode();
			//lastCriticalCoordinate = current.clone();
			breakEdge(current, lastCriticalCoordinate);
			return GOTO_SIGNAL;
		}
		//Caso especial, cuando empieza en un tunel (La mitad de una arista)
		else if( amount == 2 && visited == 0 ){
			save(true);
			verifyAdjacents();
		//Caso especial 2, cuando empieza en el inicio de un tunel
		}else if( amount == 1 && visited == 0 ){
			save(true);
			verifyAdjacents();
		//En este caso llegamos a un callejon sin salida y deberiamos
		//devolvernos al ultimo critical node
		}else if(amount == 1) {
			save(false);
			goBack(orientation.orientation);
			breakEdge( current, lastCriticalCoordinate );
			return GOTO_SIGNAL;
		} else{
			JOptionPane.showMessageDialog(null, "else: "+amount+" " + visited);
			lastCriticalCoordinate = goToClosestOpenNode();
			return GOTO_SIGNAL;
		}

		/*
		 * Cambia la orientacion de acuerdo a lo que escoja aleatoriamente
		 */
		rot = posibleDirections.get( r.nextInt(posibleDirections.size()) );
		return rot;
	}
	
	/*
	 * Guarda una nueva coordenada critica encontrada
	 * */
	public void save(boolean updateLast) {
		
		if (lastCriticalCoordinate != null && !current.equals(lastCriticalCoordinate) ) {
			
			map.addEdge(lastCriticalCoordinate.clone(), current.clone(), pathInBuilding);
			pathInBuilding = new LinkedList<>();
		}// else System.out.println("lastCritical no Null");
		if( updateLast )
			lastCriticalCoordinate = current.clone();
	}

	
	private void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		if( current2.equals(lastCriticalCoordinate2) ) JOptionPane.showMessageDialog(null, "Ambos son iguales en break edge " + current2);
		map.breakEdge(current2,lastCriticalCoordinate2);
	}
	
	/* Verifica si una coordenada critica se encuentra al lado pero
	 * aun no ha sido creado el enlace con esta
	 *  */
	private void verifyAdjacents() {
		System.out.println("Verify adjacents");
		Coordinate c = map.visit.get( current.coordinateTo(Orientation.NORTH) );
		//System.out.println(current);
		if( map.contains(c) && !map.getNeighbors(current).containsKey(c) && !current.verifyFrontWall(Orientation.NORTH) ){
			System.out.println("\tAdjacent added: " + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.WEST) );
		if( map.contains(c) && !map.getNeighbors(current).containsKey(c) && !current.verifyLeftWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: " + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.EAST) );
		if( map.contains(c) && !map.getNeighbors(current).containsKey(c) && !current.verifyRightWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: t" + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.SOUTH) );
		if( map.contains(c) && !map.getNeighbors(current).containsKey(c) && !current.verifyBackWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: t" + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}
	}
	
	/* Cantidad de vecinos visitados para una coordenada
	 * */
	private int visitedNeighbors( Coordinate coordinate ) {
		int visited = 0;
		Coordinate c = coordinate.coordinateTo(Orientation.NORTH);
		coordinate = map.visit.get(coordinate);
		if( map.visit.containsKey(c) && !coordinate.verifyFrontWall(Orientation.NORTH)   )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.WEST);
		//System.out.println("\t" + c );
		if( map.visit.containsKey(c) && !coordinate.verifyLeftWall(Orientation.NORTH) )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.EAST);
		//System.out.println("\t" + c );
		if( map.visit.containsKey(c) && !coordinate.verifyRightWall(Orientation.NORTH) )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.SOUTH);
		//System.out.println("\t" + c );
		if( map.visit.containsKey(c)  && !coordinate.verifyBackWall(Orientation.NORTH) )
			visited++;
		
		//System.out.println(map.visit);
		return visited;
	}
	
	/*
	 * Revisa el caso en que la coordenada mas cercana está adyacente
	 * */
	private Coordinate checkTrivialCase() {
		System.out.println("Checking Trivial Case");
		ArrayList<Coordinate> t = new ArrayList<>();
		
		
		Coordinate c = current.coordinateTo(Orientation.NORTH);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.NORTH) && c.getAmount() - visitedNeighbors(c) > 0 )
			t.add(c);

		c = current.coordinateTo(Orientation.WEST);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.WEST) && c.getAmount() - visitedNeighbors(c) > 0 )
			t.add(c);
		
		c = current.coordinateTo(Orientation.EAST);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.EAST) && c.getAmount() - visitedNeighbors(c) > 0 )
			t.add(c);

		c = current.coordinateTo(Orientation.SOUTH);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.SOUTH) && c.getAmount() - visitedNeighbors(c) > 0 )
			t.add(c);
		
		if( t.size() == 0 )
			return null;
		
		Coordinate next = t.get( r.nextInt( t.size() ) );
		addActions(new LinkedList<Coordinate>(), current, next, orientation.orientation);
		
		return next;
	}
	
	/*
	 * En caso de que todos las coordenadas adyacentes esten visitadas
	 * se escoge una en la cual haya al menos una direccion disponible
	 * para explorar
	 * */
	private Coordinate goToClosestOpenNode() {
		//JOptionPane.showMessageDialog(null, "closest");
		System.out.println("goToClosestNode");
		System.out.println("\t"+current);

		Coordinate adjacentSolution = checkTrivialCase();
		if(adjacentSolution!= null) return adjacentSolution;

		PriorityQueue<ShortestPathNode> q = new PriorityQueue<>();
		q.add( new ShortestPathNode(current, 0) );
		
		ShortestPathNode current = null;
		Coordinate u = null ,v = null;
		int vd, w, ud;
		
		/*
		 * Cola de prioridad para extraer los posibles destinos
		 * */
		/*
		Comparator<ShortestPathNode> xd = new Comparator<FirstAgent.ShortestPathNode>() {

			@Override
			public int compare(ShortestPathNode arg0, ShortestPathNode arg1) {
				return (int) (arg0.getValue() - arg1.getValue());
			}
		};*/
		
		PriorityQueue<ShortestPathNode> posibleCoordinates = new PriorityQueue<>();
		
		
		TreeMap<Coordinate, Integer> distances = new TreeMap<>();
		TreeMap<Coordinate, Integer> distancesSoFar = new TreeMap<>();
		TreeMap<Coordinate, Coordinate> parent = new TreeMap<>();
		TreeSet<Coordinate> visit = new TreeSet<>();
		parent.put(this.current, null);
		distancesSoFar.put(this.current, 0);
		
		while(!q.isEmpty()){
			current = q.poll();
			u = current.coordinate;
			
			if( distances.containsKey(u) ) continue;
			
			ud = distancesSoFar.get(u);
			visit.add(u);
			distances.put(u, ud);
			posibleCoordinates.add(current);
			for( Entry<Coordinate, Edge> x : map.getNeighbors(u).entrySet() ){
				v = x.getKey();
				
				if( distances.containsKey(v) ) continue;
				
				vd = distancesSoFar.containsKey(v) ? distancesSoFar.get(v) : INFINITY;
				w = x.getValue().getWeight();
				
				//System.out.println("U: " + u + " V: " + v+ " W: "+ w);
				if( vd > ud + w ){
					distancesSoFar.put( v, w + ud );
					parent.put(v, u);		
					q.add(new ShortestPathNode( v, w + ud ));
				}
			}
		}
		
		//System.out.println(distances);
		//System.out.println(parent);
		
		while( !posibleCoordinates.isEmpty() ){
			Coordinate coordinate = posibleCoordinates.poll().coordinate;
			
			int visitedN = visitedNeighbors(coordinate);
			//System.out.println( coordinate + " " + (coordinate.getAmount() - visitedN) );
			if( coordinate.getAmount() - visitedN > 0 ) {
				LinkedList<Coordinate> path = new LinkedList<>();
				path.addFirst(coordinate);
				Coordinate next = parent.get(coordinate);
				
				while( !next.equals(this.current) ) {
					System.out.println(next);
					path.addFirst(next);
					next = parent.get(next);
				}
				//System.out.println( "Camino al mas cercano: " + path );
				next = this.current;
				int o = orientation.orientation;
				for( Coordinate c: path ) {
					o = addActions( map.getPath(next, c), next, c, o );
					next = c;
				}
				return coordinate.clone();
			}
		}
		/* TODO el mapa ha sido explorado */
		cmd.add(language.getAction(DIE));
		return null;
	}

	private void goBack(int orientation) {
		System.out.println("Go back");
		addActions( map.getPath(current, lastCriticalCoordinate), current, lastCriticalCoordinate,
					orientation );
	}
	
	/* Numero de rotaciones desde una orientacion hacia otra */
	private int numberOfRotationsTo(int from, int to) {
		return  (4 - (from - to) )%4;
	}

	/* Agrega las roaciones necesarias para llegar a una coordenada adyacente */
	private int rotationsTo( Coordinate current, Coordinate next, int orientation ) {
		int o, dir = orientation;
		if( next.equals(current.coordinateTo(Orientation.NORTH)) )
			o = Orientation.NORTH;

		else if( next.equals(current.coordinateTo(Orientation.WEST)) )
			o = Orientation.WEST;

		else if( next.equals(current.coordinateTo(Orientation.EAST) ) ) 
			o = Orientation.EAST;

		else 
			o = Orientation.SOUTH;

		int r = numberOfRotationsTo(dir, o);
		
		for( int i=0; i<r; i++ ) {
			cmd.add(language.getAction(ROTATE));
			orientation = (orientation + 1)%4;
		}
		cmd.add(language.getAction(ADVANCE));
		return orientation;
	}
	
	/* Agrega acciones necesarias para llegar desde un nodo a otro dado un camino 
	 * */
	private int addActions( LinkedList<Coordinate> path,
							 Coordinate from, Coordinate to, int orientation ) {
		Coordinate current = from;
		for( Coordinate c : path ) {
			orientation = rotationsTo(current, c, orientation);
			current = c;
		}
		return rotationsTo(current, to, orientation);
	}
	
	private void updateCoordinate() {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			current.y++;
			break;
		case Orientation.SOUTH:
			current.y--;
			break;

		case Orientation.EAST:
			current.x++;
			break;

		case Orientation.WEST:
			current.x--;
			break;
		}
	}

	/**
	 * execute
	 *
	 * @param perception
	 *            Perception
	 * @return Action[]
	 */
	public Action compute(Percept p) {
		return new Action("no_op");
	}
	
	class ShortestPathNode implements Comparable<ShortestPathNode>{
		protected Coordinate coordinate;
		protected int weight;
		public ShortestPathNode(Coordinate coo, int weight){
			this.coordinate = coo;
			this.weight = weight;
		}
		
		public double getValue(){
			return (coordinate.getAmount() - visitedNeighbors(coordinate))/Math.max(weight,1);
		}
		
		@Override
		public int compareTo(ShortestPathNode sn) {
			return weight - sn.weight;
			//return (int) (getValue() - sn.getValue());
		}
	}
}
