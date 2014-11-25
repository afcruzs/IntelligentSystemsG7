package unalcol.agents.examples.labyrinth.teseoeater.grupo7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;


public class Grupo7AgentEater implements AgentProgram, Grupo7If {
	protected SimpleLanguage language;
	protected Vector<String> cmd ;
	protected Orientation orientation;
	
	protected Coordinate current;
	protected Coordinate lastCriticalCoordinate;
	protected Random r;
	
	protected final int NO_OP = 0;
	protected final int DIE = 1;
	protected final int ADVANCE = 2;
	protected final int ROTATE = 3;
	protected final int EAT = 4;
	
	protected final int GOTO_SIGNAL = -2;
	protected final int GOAL = -1;
	
	protected final int INFINITY = 1000000000;
	

	protected LinkedList<Coordinate> pathInBuilding;

	protected LabyrinthMap map;
	protected Debug debug;
	int energyNeeded ;
	protected TreeSet<Coordinate> goodFood, badFood, visitedFood;
	protected Coordinate lastFoodCoord;
	int currentEnergy;
	final int  MAX_ENERGY = 40;
	Stack<Integer> energyHistory;

	public Grupo7AgentEater() {
		init();
	}

	public Grupo7AgentEater(SimpleLanguage language2) {
		init();
		setLanguage(language2);
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
		debug = new Debug(this);
		energyNeeded = 0;
		goodFood = new TreeSet<>();
		badFood = new TreeSet<>();
		visitedFood = new TreeSet<>();
		lastFoodCoord = null;
		currentEnergy = 0;
		energyHistory = new Stack<>();
	}
	
	public int rotate(boolean FW, boolean RW, boolean BW, boolean LW, 
						boolean FA, boolean RA, boolean BA, boolean LA, boolean T) {
		debug.repaint();
		if (T)
			return GOAL;		
		
		int amount = 0, visited = 0, rot;
		ArrayList<Integer> posibleDirections = new ArrayList<>(); 
		if( !LW ) { 
			amount++;
			if( map.visit.containsKey(current.coordToLeft(orientation)) )
				visited++;
			else if( !LA )
				posibleDirections.add(3);				
		}
		if (!RW) { 
			amount++;
			if( map.visit.containsKey(current.coordToRight(orientation)) )
				visited++;
			else if( !RA )
				posibleDirections.add(1);
		}
		if (!FW) { 
			amount++;
			if( map.visit.containsKey(current.coordToUp(orientation)) )
				visited++;
			else if( !FA )
				posibleDirections.add(0);
		}
		if (!BW) { 
			amount++;
			if( map.visit.containsKey(current.coordToDown(orientation)) )
				visited++;
			else if( !BA )
				posibleDirections.add(2);
		}
		
		current.updateInfo(amount, FW, RW, BW, LW, orientation.clone() );
		map.visit.put(current.clone(), current.clone());
		//System.out.println(current + " -- " + amount + " " + visited + " " + posibleDirections);
		
		
		//Cantidad de paredes libres es mas que dos es un critical node
		//si la cantidad disponible es mayor que los visitados
		//hay algun camino posible
		
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
		else if(amount == 2 && visited == 1){
			pathInBuilding.add( current.clone() );			
		}
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
			if(lastCriticalCoordinate!=null)
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
		System.out.println("Verify adjacents" + current);
		Coordinate c = map.visit.get( current.coordinateTo(Orientation.NORTH) );
		//System.out.println(current);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.NORTH) ){
			System.out.println("\tAdjacent added: " + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.WEST) );
		if( map.contains(c) && !current.verifyLeftWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: " + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.EAST) );
		if( map.contains(c) && !current.verifyRightWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: t" + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}

		c = map.visit.get( current.coordinateTo(Orientation.SOUTH) );
		if( map.contains(c) && !current.verifyBackWall(Orientation.NORTH) ) {
			System.out.println("\tAdjacent added: t" + c);
			map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
		}
	}
	
	
	/* Cantidad de vecinos visitados para una coordenada
	 * */
	private int visitedNeighbors( Coordinate coordinate ) {
		if(coordinate == null) return 0;
		int visited = 0;
		Coordinate c = coordinate.coordinateTo(Orientation.NORTH);
		coordinate = map.visit.get(coordinate);
		if( map.visit.containsKey(c) && !coordinate.verifyFrontWall(Orientation.NORTH)   )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.WEST);
		if(coordinate == null) return 0;
		//System.out.println("\t" + c );
		if( map.visit.containsKey(c) && !coordinate.verifyLeftWall(Orientation.NORTH) )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.EAST);
		if(coordinate == null) return 0;
		//System.out.println("\t" + c );
		if( map.visit.containsKey(c) && !coordinate.verifyRightWall(Orientation.NORTH) )
			visited++;
		
		c = coordinate.coordinateTo(Orientation.SOUTH);
		if(coordinate == null) return 0;
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
	
	private Coordinate checkTrivialFood(){
		ArrayList<Coordinate> t = new ArrayList<>();
		Coordinate c = current.coordinateTo(Orientation.NORTH);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.NORTH) && goodFood.contains(c) )
			t.add(c);

		c = current.coordinateTo(Orientation.WEST);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.WEST) && goodFood.contains(c) )
			t.add(c);
		
		c = current.coordinateTo(Orientation.EAST);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.EAST) && goodFood.contains(c) )
			t.add(c);

		c = current.coordinateTo(Orientation.SOUTH);
		c = map.visit.get(c);
		if( map.contains(c) && !current.verifyFrontWall(Orientation.SOUTH) && goodFood.contains(c) )
			t.add(c);
		
		if( t.size() == 0 )
			return null;
		
		Coordinate next = t.get( r.nextInt( t.size() ) );
		addActions(new LinkedList<Coordinate>(), current, next, orientation.orientation);
		
		return next;
	}
	

	/*
	 * Cuenta las rotaciones para llegar de current a next dada una rotacion
	 */
	private int countRotationsToAdjacent( Coordinate current, Coordinate next, int orientation) {
		int o, dir = orientation;
		if( next.equals(current.coordinateTo(Orientation.NORTH)) )
			o = Orientation.NORTH;

		else if( next.equals(current.coordinateTo(Orientation.WEST)) )
			o = Orientation.WEST;

		else if( next.equals(current.coordinateTo(Orientation.EAST) ) ) 
			o = Orientation.EAST;

		else 
			o = Orientation.SOUTH;

		return numberOfRotationsTo(dir, o);

	}
	
	/*
	 * Cuenta la cantidad de rotaciones totales en un path 
	 */
	private int countRotationsInPath(LinkedList<Coordinate> path, 
			Coordinate from, Coordinate to){
		if(path==null || from == null || to == null) return 0;
		int orientation = this.orientation.orientation;
		
		Coordinate current = from;
		int r,ans=0;
		for( Coordinate c : path ) {
			r = countRotationsToAdjacent(current, c, orientation);
			ans += r;
			for(int i=0; i<r; i++) orientation = (orientation+1)%4;
			current = c;
		}
		
		return ans;
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
		verifyAdjacents();
		Coordinate adjacentSolution = checkTrivialCase();
		if(adjacentSolution!= null) return adjacentSolution;

		PriorityQueue<ShortestPathNode> q = new PriorityQueue<>();
		q.add( new ShortestPathNode( current, 0,0 ) );
		
		ShortestPathNode current = null;
		Coordinate u = null ,v = null;
		int vd, w, ud, rot; // rot -> rotations
		
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
				rot = countRotationsInPath(map.getPath(u, x.getKey()), u, x.getKey());
				w = x.getValue().getWeight();
				
				//System.out.println("U: " + u + " V: " + v+ " W: "+ w);
				if( vd > ud + w + rot ){
					distancesSoFar.put( v, w + ud + rot );
					parent.put(v, u);		
					q.add(new ShortestPathNode( v, w + ud, rot ));
				}
			}
		}
		
		//System.out.println(distances);
		System.out.println("PARENT: " +parent);
		
		while( !posibleCoordinates.isEmpty() ){
			Coordinate coordinate = posibleCoordinates.poll().coordinate;
			
			int visitedN = visitedNeighbors(coordinate);
			//System.out.println( coordinate + " " + (coordinate.getAmount() - visitedN) );
			if( coordinate.getAmount() - visitedN > 0 ) {
				LinkedList<Coordinate> path = new LinkedList<>();
				path.addFirst(coordinate);
				Coordinate next = parent.get(coordinate);
				
				System.out.println("\tPATH: " +path);
				
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
		
		System.out.println(goodFood);
		
		
		System.out.println( "resource-type " + p.getAttribute("resource-type") );
		int energy = Integer.parseInt(p.getAttribute("resource-type").toString());
		
		if( energy == 30 && goodFood.size() > 0 )
		{
			System.err.println("olol");
			goToClosestFood();
			if( cmd.size() > 0 )
				return new Action(cmd.remove(0));
		}
		
		if( p.getAttribute("resource").toString().equals("true") && energyNeeded > 0 ){
			if( !visitedFood.contains(current) ){
				energyNeeded--;
				visitedFood.add(current);
				energyHistory.add( energy );
				return new Action(language.getAction(EAT));
			}else{
				
				if( energyHistory.peek() <= energy ){
					energyNeeded--;
					energyHistory.add( energy );
					visitedFood.add(current);
					goodFood.add(current);
					return new Action(language.getAction(EAT));
				}
			}
			
		}
		
		energyHistory.add( energy );
		
		
	//	System.out.println( "resource  " + p.getAttribute("resource") );
	//	System.out.println( "resource-color " + p.getAttribute("resource-color") );
	//	System.out.println("resource-shape " + p.getAttribute("resource-shape") );
	//	System.out.println( "resource-size " + p.getAttribute("resource-size") );
	//	System.out.println( "resource-weight "+p.getAttribute("resource-weight") );
		//System.out.println( "resource-type " + p.getAttribute("resource-type") );
	//	System.out.println( "energy_level " + p.getAttribute("energy_level") );
		
		energyNeeded = MAX_ENERGY - Integer.parseInt(p.getAttribute("resource-type").toString());
		/*System.out.println("--------");
		if( lastFoodCoord != null ){
			int newThing = Integer.parseInt(p.getAttribute("resource-type").toString());
			if( newThing < currentEnergy )
				badFood.add(current);
			else
				goodFood.add(current);
		}else{
			if( p.getAttribute("resource").toString().equals("true") && !badFood.contains(current) && goodFood.contains(current) ){
				while( energyNeeded > 0 ){
					cmd.add(language.getAction(EAT));
					energyNeeded--;
				}
				
				lastFoodCoord = current;
			}else if(p.getAttribute("resource").equals("true") && !badFood.contains(current) ){
				cmd.add(language.getAction(EAT));
				lastFoodCoord = current;
			}
		}*/
		
		currentEnergy = Integer.parseInt(p.getAttribute("resource-type").toString());
		
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
			/*boolean FA = ((Boolean) p.getAttribute(language.getPercept(5)))
					.booleanValue();
			boolean RA = ((Boolean) p.getAttribute(language.getPercept(6)))
					.booleanValue();
			boolean BA = ((Boolean) p.getAttribute(language.getPercept(7)))
					.booleanValue();
			boolean LA = ((Boolean) p.getAttribute(language.getPercept(8)))
					.booleanValue(); */

			//int d = rotate(FW, RW, BW, LW, FA, RA, BA, LA, T );
			int d = rotate(FW, RW, BW, LW, false, false, false, false, T );
			if( d == GOAL ) return new Action(language.getAction(NO_OP));
			if (0 <= d && d < 4) {
				for (int i = 1; i <= d; i++) {
					cmd.add(language.getAction(ROTATE)); // rotate
					
				}
				cmd.add(language.getAction(ADVANCE)); // advance

			} else if( d == DIE ){
				cmd.add(language.getAction(DIE)); // die
			}
		}
		/*
		 * Meter percepciones de agente
		 */

		String x = cmd.get(0);
		if (x.equals(language.getAction(ADVANCE))) {
			updateCoordinate();
		} else if(x.equals(language.getAction(ROTATE)) ) {
			/* Actualiza la orientacion */
			orientation.orientation = (orientation.orientation + 1)%4;
		}

		cmd.remove(0);		
		debug.repaint();
		return new Action(x);
	}
	
	
	private Coordinate goToClosestFood() {

		Queue<Coordinate> q = new LinkedList<>();
		TreeSet<Coordinate> visit = new TreeSet<>();
		TreeMap<Coordinate, Coordinate> parent = new TreeMap<>();
		parent.put(this.current,null);
		
		while(q.size() > 0){
			Coordinate c = q.poll();
			visit.add(c);
			
			for(Coordinate child : map.getNeighbors(c).keySet() ){
				if( !visit.contains(child) ){
					q.add(child);
					parent.put(child,c);
				}
			}
		}
		
		ArrayList<Coordinate> t = new ArrayList<>();
		
		for( Coordinate xd : goodFood ){
			t.add(xd);
		}
		
		
		while(t.size() > 0){
			Coordinate xd = t.remove(r.nextInt(t.size()));
			
			if( parent.keySet().contains(xd) ){
				Coordinate next = parent.get(xd);
				LinkedList<Coordinate> path = new LinkedList<>();
				while( next != null ) {
					path.addFirst(next);
					next = parent.get(next);
				}
				
				next = this.current;
				int o = orientation.orientation;
				for( Coordinate c: path ) {
					o = addActions( map.getPath(next, c), next, c, o );
					next = c;
				}
				return xd.clone();
				
			}
		}
		
		System.err.println("did not do a shit");
		return null;		
	}

	//Cuenta los nodos a los que puede seguir visitando
	private int countFreeNodes(Coordinate c){
		return c.getAmount() - visitedNeighbors(c);
	}
	
	
	
	class ShortestPathNode implements Comparable<ShortestPathNode>{
		protected Coordinate coordinate;
		protected int weight;
		protected int rotations;
		public ShortestPathNode(Coordinate coo, int weight,int rotations){
			this.coordinate = coo;
			this.weight = weight;
			this.rotations = rotations;
			
		}
		
		@Override
		public int compareTo(ShortestPathNode sn) {
			if( weight + rotations != sn.weight + sn.rotations ) return (weight+rotations) - (sn.weight+sn.rotations);
			Coordinate a = map.visit.get(coordinate);
			Coordinate b = map.visit.get(sn.coordinate);
			return countFreeNodes(b) - countFreeNodes(a);
		}
	}

	@Override
	public LabyrinthMap getMap() {
		return map;
	}

	@Override
	public LinkedList<Coordinate> getPathInBuilding() {
		return pathInBuilding;
	}

	@Override
	public Orientation getOrientation() {
		// TODO Auto-generated method stub
		return orientation;
	}

	@Override
	public Coordinate getLastCoordinate() {
		// TODO Auto-generated method stub
		return lastCriticalCoordinate;
	}

	@Override
	public Coordinate getCurrent() {
		// TODO Auto-generated method stub
		return current;
	}
}
