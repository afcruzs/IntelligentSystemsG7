package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
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

	/*private final int LEFT_EMPTY = 11;
	private final int RIGHT_EMPTY = 5;
	private final int FRONT_EMPTY = 3;
	private final int BACK_EMPTY = 7;*/
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
		current = new Coordinate();
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
		if (FW == false && visit.contains(current.coordToNorth(orientation)) ) { visited++; }
		if (BW == false && visit.contains(current.coordToSouth(orientation)) ) { visited++; }
		
		if (LW == false && !visit.contains(current.coordToLeft(orientation)) ) { posibleDirections.add(3); }
		if (RW == false && !visit.contains(current.coordToRight(orientation)) ) { posibleDirections.add(1); }
		if (FW == false && !visit.contains(current.coordToNorth(orientation)) ) { posibleDirections.add(0); }
		if (BW == false && !visit.contains(current.coordToSouth(orientation)) ) { posibleDirections.add(2); }
		
		//JOptionPane.showMessageDialog(null, visited + "  " + posibleDirections);
		
		//Cantidad de paredes libres es mas que dos es un critical node
		//si la cantidad disponible es mayor que los visitados
		//hay algun camino posible
		if (amount > 2 && amount > visited)
			save(FW, RW, BW, LW);
		//En este caso ya todo esta visitado, se debe guardar
		//y luego ir al nodo mas cercano que tenga opciones de explorar algo nuevo
		else if( amount > 2 && amount == visited ) {
			save(FW, RW, BW, LW);
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
			save(FW, RW, BW, LW);
		//Caso especial 2, cuando la simulacion empieza en el inicio de un tunel
		else if( amount == 1 && visited == 0 )
			save(FW, RW, BW, LW);
		//En este caso llegamos a un callejon sin salida y deberiamos
		//devolvernos al ultimo critical node
		else if(amount == 1) {
			save(FW, RW, BW, LW);
			goTo( lastCriticalCoordinate );
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
		switch (rot) {
		case 0:
			orientation.goToNorth();
			break;

		case 1:
			orientation.goToRight();
			break;

		case 2:
			orientation.goToSouth();
			break;

		case 3:
			orientation.goToLeft();
			break;
		}

		return rot;
	}
	

	private void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		
		map.breakEdge(current2,lastCriticalCoordinate2);
	}

	private void goToClosestOpenNode() {
		// TODO Auto-generated method stub
		//System.out.println("GOTO");
	}

	private void goTo(Coordinate lastCriticalCoordinate2) {
		
		
		
		System.out.println("Dijkstra");
		PriorityQueue<ShorestPathNode> q = new PriorityQueue<>();
		q.add( new ShorestPathNode(current, 0) );
		
		ShorestPathNode current = null;
		Coordinate u = null ,v = null;
		int vd ,w,ud;
		
		HashMap<Coordinate, Integer> distances = new HashMap<>( map.size() );
		HashMap<Coordinate, Integer> distancesSoFar = new HashMap<>( map.size() );
		HashMap<Coordinate, Coordinate> parent = new HashMap<>( map.size() );
		HashSet<Coordinate> visit = new HashSet<>( map.size() );
		parent.put(this.current, null);
		distancesSoFar.put(this.current, 0);
		
		while(!q.isEmpty()){
			current = q.poll();
			
			if( distances.containsKey(current) ) continue;
			
			u = current.coordinate;
			ud = distancesSoFar.get(u);
			visit.add(u);
			distances.put(current.coordinate, current.weight);
			for( Entry<Coordinate, Edge> x : map.getNeighbors(current.coordinate).entrySet() ){
				
				if( !visit.contains(v) ){
					v = x.getKey();
					vd = distancesSoFar.containsKey(v) ? distancesSoFar.get(v) : INFINITY;
					w = x.getValue().getWeight();
					if( vd + w < ud ){
						distancesSoFar.put( u, vd + w );
						parent.put(u, v);		
						q.add(new ShorestPathNode(u, vd + w));
					}
				}
					
			}
			
			
		}
		
		System.out.println(parent);
		
		//System.out.println("CLOSEST");
	}

	public void save(boolean FW, boolean RW, boolean BW, boolean LW) {
		
		if (lastCriticalCoordinate != null) {
			System.out.println(lastCriticalCoordinate + " to:  " + current);
			//System.out.println("LAST /:"+lastCriticalCoordinate + "  CURRENT /:" + current + " "
				//	+ pathInBuilding);
			map.addEdge(lastCriticalCoordinate, current, pathInBuilding);
			
			pathInBuilding = new LinkedList<>();
		}
		lastCriticalCoordinate = current.clone();
		
		/*System.out.println(lastCriticalCoordinate);*/
		System.out.println(map);
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
			current.x--;
			// current = current.coordToLeft(orientation);
			break;

		case Orientation.WEST:
			current.x++;
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

			if (0 <= d && d < 4) {
				for (int i = 1; i <= d; i++) {
					cmd.add(language.getAction(3)); // rotate
				}
				cmd.add(language.getAction(2)); // advance

			} else {
				//System.out.println("PAILA");
				cmd.add(language.getAction(0)); // die
			}
		}
		/*
		 * Meter percepciones de agente
		 */

		String x = cmd.get(0);
		if (x.equals(language.getAction(2))) {

			// JOptionPane.showMessageDialog(null, /*visit.size() + " \n" +*/
			// orientation + " \n" + current);
			visit.add(current.clone());
			updateCoordinate();

		}
		cmd.remove(0);
		System.out.println(x + " ");
		
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
