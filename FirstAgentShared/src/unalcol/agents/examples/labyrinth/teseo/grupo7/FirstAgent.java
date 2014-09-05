package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;


public class FirstAgent implements AgentProgram {
	protected SimpleLanguage language;
	protected Vector<String> cmd = new Vector<String>();
	private Orientation orientation;
	private TreeSet<Coordinate> visit = new TreeSet<>();
	private Coordinate current = new Coordinate();
	private Coordinate lastCriticalCoordinate = null;
	private Random r = new Random();
	
	private final int NO_OP = 0;
	private final int DIE = 1;
	private final int ADVANCE = 2;
	private final int ROTATE = 3;
	
	private final int GOTO_SIGNAL = -2;
	private final int INFINITY = 1000000000;

	private LinkedList<Coordinate> pathInBuilding = new LinkedList<>();

	private LabyrinthMap map = new LabyrinthMap();

	public FirstAgent() {
		init();
	}

	public void setLanguage(SimpleLanguage _language) {
		language = _language;
	}

	public void init() {
		cmd = new Vector<>();
		orientation = new Orientation();
		r = new Random();
		visit = new TreeSet<>();
		current = new Coordinate(0,0);
		lastCriticalCoordinate = null;
		map = new LabyrinthMap();
		pathInBuilding = new LinkedList<>();
	}

	public int rotate(boolean FW, boolean RW, boolean BW, boolean LW, boolean T) {
		if (T)
			return -1;
		int rot = 0, amount = 0;
		if (LW == false) { amount++; }
		if (RW == false) { amount++; }
		if (FW == false) { amount++; }
		if (BW == false) { amount++; }
		
		int visited = 0;
		ArrayList<Integer> posibleDirections = new ArrayList<>(); 
		if (LW == false && visit.contains(current.coordToLeft(orientation)) ) { visited++; }
		if (RW == false && visit.contains(current.coordToRight(orientation)) ) { visited++; }
		if (FW == false && visit.contains(current.coordToUp(orientation)) ) { visited++; }
		if (BW == false && visit.contains(current.coordToDown(orientation)) ) { visited++; }
		
		if (LW == false && !visit.contains(current.coordToLeft(orientation)) ) { posibleDirections.add(3); }
		if (RW == false && !visit.contains(current.coordToRight(orientation)) ) { posibleDirections.add(1); }
		if (FW == false && !visit.contains(current.coordToUp(orientation)) ) { posibleDirections.add(0); }
		if (BW == false && !visit.contains(current.coordToDown(orientation)) ) { posibleDirections.add(2); }
		
		System.out.println(current + " -- " + amount + " " + visited + " " + posibleDirections);
		 
		//Cantidad de paredes libres es mas que dos es un critical node
		//si la cantidad disponible es mayor que los visitados
		//hay algun camino posible
		if (amount > 2 && amount > visited)
			save(true);
		//En este caso ya todo esta visitado, se debe guardar
		//y luego ir al nodo mas cercano que tenga opciones de explorar algo nuevo
		else if( amount > 2 && amount == visited ) {
			save(true);
			goToClosestOpenNode();
			return GOTO_SIGNAL;
		}
		//En este caso vamos por un tunel 
		else if(amount == 2 && visited == 1)
			pathInBuilding.add( current.clone() );
		//En este caso vamos por el tunel pero nos encontramos con un 
		//pedazo del tunel que ya ha sido visitado
		else if(amount == 2 && visited == 2) {
			goToClosestOpenNode();
			return GOTO_SIGNAL;
		}
		//Caso especial, cuando empieza la simulacion en un tunel (La mitad de una arista)
		else if( amount == 2 && visited == 0 )
			save(true);
		//Caso especial 2, cuando la simulacion empieza en el inicio de un tunel
		else if( amount == 1 && visited == 0 )
			save(true);
		//En este caso llegamos a un callejon sin salida y deberiamos
		//devolvernos al ultimo critical node
		else if(amount == 1) {
			save(false);
			goBack();
			breakEdge( current, lastCriticalCoordinate );
			return GOTO_SIGNAL;
		} else{
			goToClosestOpenNode();
			return GOTO_SIGNAL;
		}

		/*
		 * Cambia la orientacion de acuerdo a lo que escoja aleatoriamente
		 */
		rot = posibleDirections.get( r.nextInt(posibleDirections.size()) );
		/*switch (rot) {
		case Orientation.NORTH:
			orientation.goToNorth();
			break;

		case Orientation.EAST:
			orientation.goToEast();
			break;

		case Orientation.SOUTH:
			orientation.goToSouth();
			break;

		case Orientation.WEST:
			orientation.goToWest();
			break;
		}
*/
		return rot;
	}
	

	public void save(boolean updateLast) {
		
		
		if (lastCriticalCoordinate != null) {
			if( current.equals(lastCriticalCoordinate) )
				return;
			//System.out.println(lastCriticalCoordinate + " to:  " + current);
			//System.out.println("LAST /:"+lastCriticalCoordinate + "  CURRENT /:" + current + " "
				//	+ pathInBuilding);
			//pathInBuilding.add(current);
			map.addEdge(lastCriticalCoordinate, current, pathInBuilding);
			
			pathInBuilding = new LinkedList<>();
			//pathInBuilding.add(current);
		}
		if( updateLast )
			lastCriticalCoordinate = current.clone();
		
		/*System.out.println(lastCriticalCoordinate);*/
		//System.out.println(map);
	}

	
	private void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		
		map.breakEdge(current2,lastCriticalCoordinate2);
	}

	private void goToClosestOpenNode() {
		System.out.println("goToClosestNode");
		PriorityQueue<ShorestPathNode> q = new PriorityQueue<>();
		q.add( new ShorestPathNode(current, 0) );
		
		ShorestPathNode current = null;
		Coordinate u = null ,v = null;
		int vd, w, ud;
		
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
			for( Entry<Coordinate, Edge> x : map.getNeighbors(u).entrySet() ){
				v = x.getKey();
				
				if( distances.containsKey(v) ) continue;
				
				vd = distancesSoFar.containsKey(v) ? distancesSoFar.get(v) : INFINITY;
				w = x.getValue().getWeight();
				
				System.out.println("U: " + u + " V: " + v+ " W: "+ w);
				if( vd > ud + w ){
					distancesSoFar.put( v, w + ud );
					parent.put(v, u);		
					q.add(new ShorestPathNode( v, w + ud ));
				}
			}
		}
		System.out.println(distances);
	}

	private void goBack() {
		System.out.println("Go back");
		addActions( map.getPath(current, lastCriticalCoordinate), current, lastCriticalCoordinate );
	}
	
	private int numberOfRotationsTo(int from, int to) {
		return  ( 4 - (from - to) )%4;
	}

	private void rotationsTo( Coordinate current, Coordinate next ) {
		int o, dir = orientation.orientation;
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
			orientation.orientation = (orientation.orientation + 1)%4;
		}
//		System.out.println(dir+" + "+ o + " = "+r + " nueva orientacion: " + orientation.orientation);
		cmd.add(language.getAction(ADVANCE));
	}
	
	private void addActions( LinkedList<Coordinate> path,
							 Coordinate from, Coordinate to ) {
		Coordinate current = from;
		for( Coordinate c : path ) {
			rotationsTo(current, c);
			//System.out.println(orientation.orientation + "  " + current +  "        " + c);
			current = c;
		}
		rotationsTo(current, to);
	}
	
	
	private void updateCoordinate() {
		switch (orientation.orientation) {
		case Orientation.NORTH:
			current.y++;
			// current = current.coordToNorth(orientation);
			break;
		case Orientation.SOUTH:
			current.y--;
			// current = current.coordToSouth(orientation);
			break;

		case Orientation.EAST:
			current.x++;
			// current = current.coordToLeft(orientation);
			break;

		case Orientation.WEST:
			current.x--;
			// current = current.coordToRight(orientation);
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
		if (cmd.size() == 0) {

			/*
			 * Captura percepciones
			 */
			boolean FW = ((Boolean) p.getAttribute(language.getPercept(0)))
					.booleanValue();
			boolean RW = ((Boolean) p.getAttribute(language.getPercept(1)))
					.booleanValue();
			boolean BW = ((Boolean) p.getAttribute(language.getPercept(2)))
					.booleanValue();
			boolean LW = ((Boolean) p.getAttribute(language.getPercept(3)))
					.booleanValue();
			boolean T = ((Boolean) p.getAttribute(language.getPercept(4)))
					.booleanValue();
		/*	boolean FA = ((Boolean) p.getAttribute(language.getPercept(5)))
					.booleanValue();
			boolean RA = ((Boolean) p.getAttribute(language.getPercept(6)))
					.booleanValue();
			boolean BA = ((Boolean) p.getAttribute(language.getPercept(7)))
					.booleanValue();
			boolean LA = ((Boolean) p.getAttribute(language.getPercept(8)))
					.booleanValue();*/

			int d = rotate(FW, RW, BW, LW, T);
			System.out.println(d);
			if (0 <= d && d < 4) {
				for (int i = 1; i <= d; i++) {
					cmd.add(language.getAction(ROTATE)); // rotate
					orientation.orientation = (orientation.orientation + 1)%4;
				}
				cmd.add(language.getAction(ADVANCE)); // advance

			} else if( d == DIE ){
				//System.out.println("PAILA");
				cmd.add(language.getAction(DIE)); // die
			}
		}
		/*
		 * Meter percepciones de agente
		 */

		String x = cmd.get(NO_OP);
		if (x.equals(language.getAction(ADVANCE))) {

			// JOptionPane.showMessageDialog(null, /*visit.size() + " \n" +*/
			// orientation + " \n" + current);
			visit.add(current.clone());
			updateCoordinate();

		} else if(x.equals(language.getAction(ROTATE)) ) {
			
			
		}
		//System.out.println(x + " Orientation: " + orientation.orientation);
		System.out.println(x+ "  " + current + " " + lastCriticalCoordinate );
		cmd.remove(0);		
		return new Action(x);
	}
	
	class ShorestPathNode implements Comparable<ShorestPathNode>{
		protected Coordinate coordinate;
		protected int weight;
		public ShorestPathNode(Coordinate coo, int weight){
			this.coordinate = coo;
			this.weight = weight;
		}
		@Override
		public int compareTo(ShorestPathNode sn) {
			return weight - sn.weight;
		}
	}

}
