package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JOptionPane;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;

public class SecondAgent implements AgentProgram, Grupo7If {
	protected SimpleLanguage language;
	protected Vector<String> cmd;
	protected Orientation orientation;

	protected Coordinate current;
	protected Coordinate lastCriticalCoordinate;
	protected Random r;

	protected final int NO_OP = 0;
	protected final int DIE = 1;
	protected final int ADVANCE = 2;
	protected final int ROTATE = 3;

	protected final int GOTO_SIGNAL = -2;
	protected final int GOAL = -1;

	protected final int INFINITY = 1000000000;
	
	protected int currentOperation = -1;
	
	protected final int GO_TO_CLOSEST = 0;
	protected final int GO_BACK = 1;
	protected final int SEARCHING = 2;
	
	protected int timesBlocked = 0;
	protected final int MAX_BLOCKED_TIMES = 10;
	protected LinkedList<Coordinate> pathInBuilding;

	protected LabyrinthMap map;
	protected Debug debug;
	private final String UPDATE_LAST_CRITICAL = "KOKO";
	private Queue<Coordinate> lastCriticalHistory;

	public SecondAgent() {
		init();
	}

	public void setLanguage(SimpleLanguage _language) {
		language = _language;
	}

	public void init() {
		cmd = new Vector<>();
		orientation = new Orientation();
		r = new Random();
		current = new Coordinate(0, 0);
		lastCriticalCoordinate = null;
		map = new LabyrinthMap();
		pathInBuilding = new LinkedList<>();
		timesBlocked = 0;
		lastCriticalHistory = new LinkedList<>();
		debug = new Debug(this);
	}

	public int rotate(boolean FW, boolean RW, boolean BW, boolean LW,
			boolean FA, boolean RA, boolean BA, boolean LA, boolean T) {
		debug.repaint();
		if (T)
			return -1;

		int amount = 0, visited = 0;
		ArrayList<Integer> posibleDirections = new ArrayList<>();
		if (!LW) {
			amount++;
			if (map.visit.containsKey(current.coordToLeft(orientation)))
				visited++;
			else if (!LA)
				posibleDirections.add(3);
		}
		if (!RW) {
			amount++;
			if (map.visit.containsKey(current.coordToRight(orientation)))
				visited++;
			else if (!RA)
				posibleDirections.add(1);
		}
		if (!FW) {
			amount++;
			if (map.visit.containsKey(current.coordToUp(orientation)))
				visited++;
			else if (!FA)
				posibleDirections.add(0);
		}
		if (!BW) {
			amount++;
			if (map.visit.containsKey(current.coordToDown(orientation)))
				visited++;
			else if (!BA)
				posibleDirections.add(2);
		}

		current.updateInfo(amount, FW, RW, BW, LW, orientation.clone());
		map.visit.put(current.clone(), current.clone());
		currentOperation = SEARCHING;
		if (!LA && !RA && !FA && !BA)
			return rotateWithoutAgent(amount, visited, posibleDirections);

		
		System.out.println( "AGENT FOUND " );
		return rotateWithAgent(amount, visited, posibleDirections);
	}
	
	private void printCurrentOp(){
		System.out.println("Times blocked " + timesBlocked);
		switch (currentOperation) {
		case SEARCHING:
			System.out.println("Searching");
			break;

		case GO_BACK:
			System.out.println("Go back...");
			break;
		case GO_TO_CLOSEST:
			System.out.println("Go to closest...");
		}
	}

	private int rotateWithAgent(int amount, int visited,
			ArrayList<Integer> posibleDirections) {
		// Sin direcciones posibles para avanzar
		if (posibleDirections.size() == 0) {
			/*
		}
			//Aca guardar :v
			// Puede calcular el nodo mas cercano o esperar
			if (amount > visited) {
			
				// Caso de critical node
				if (amount > 2) {
					save(true);
					verifyAdjacents();
					goToClosestOpenNode();
					// goBack is an option
				} else {
					save(false);
					verifyAdjacents();
					if (lastCriticalCoordinate != null) {
						goToClosestOpenNode();
						/*if( lastCriticalCoordinate != null )
							breakEdge(current, lastCriticalCoordinate);
					} else {
						//Va por un camino que esta cerrado por un agente
						// asi que se devuelve
						goBack(orientation.orientation);
						//breakEdge(current, lastCriticalCoordinate);
					}
				}
				return GOTO_SIGNAL;
			}
			// Puede calcular el nodo mas cercano, pero en el camino puede estar
			// el otro agente
			// La funcion de contigencia en compute podria arreglarlo
			else {
				if (amount > 2) {
					save(true);
					verifyAdjacents();
					goToClosestOpenNode();
					// goBack is a option
				} else {
					save(false);
					verifyAdjacents();
					if (lastCriticalCoordinate != null) {
						goToClosestOpenNode();
						/*if( lastCriticalCoordinate != null )
							breakEdge(current, lastCriticalCoordinate);
						//goBack(orientation.orientation);
					} else {
						
						//Va por un camino que esta cerrado por un agente
						// asi que se devuelve
						goBack(orientation.orientation);
						//breakEdge(current, lastCriticalCoordinate);
						//goBack(orientation.orientation);
					}
				}
				
			}*/
			
			save(true);
			verifyAdjacents();
			goToClosestOpenNode();
				
			return GOTO_SIGNAL;
		}

		// Siempre es nodo critico
		save(true);
		verifyAdjacents();
		return posibleDirections.get(r.nextInt(posibleDirections.size()));
	}

	public int rotateWithoutAgent(int amount, int visited,
			ArrayList<Integer> posibleDirections) {
		// Cantidad de paredes libres es mas que dos es un critical node
		// si la cantidad disponible es mayor que los visitados
		// hay algun camino posible
		if (amount > 2 && amount > visited) {
			save(true);
			verifyAdjacents();
			// En este caso ya todo esta visitado, se debe guardar
			// y luego ir al nodo mas cercano que tenga opciones de explorar
			// algo nuevo
		} else if (amount > 2 && amount == visited) {
			save(true);
			verifyAdjacents();
			goToClosestOpenNode();
			// lastCriticalCoordinate = current.clone();
			return GOTO_SIGNAL;
		}
		// En este caso vamos por un tunel
		else if (amount == 2 && visited == 1 ) {
			if( !map.contains(current) ) 
				pathInBuilding.add(current.clone());
		}
		// En este caso vamos por el tunel pero nos encontramos con un
		// pedazo del tunel que ya ha sido visitado
		else if (amount == 2 && visited == 2) {
			// JOptionPane.showMessageDialog(null, "caso");
			save(false);
			verifyAdjacents();
			/*if (current.equals(lastCriticalCoordinate))
				//System.out.println("iguales");*/

			goToClosestOpenNode();
			// lastCriticalCoordinate = current.clone();
			breakEdge(current, lastCriticalCoordinate);
			return GOTO_SIGNAL;
		}
		// Caso especial, cuando empieza en un tunel (La mitad de una arista)
		else if (amount == 2 && visited == 0) {
			save(true);
			verifyAdjacents();
			// Caso especial 2, cuando empieza en el inicio de un tunel
		} else if (amount == 1 && visited == 0) {
			save(true);
			verifyAdjacents();
			// En este caso llegamos a un callejon sin salida y deberiamos
			// devolvernos al ultimo critical node
		} else if (amount == 1) {
			save(false);
			goBack(orientation.orientation);
			breakEdge(current, lastCriticalCoordinate);
			return GOTO_SIGNAL;
		} else {
			/*JOptionPane.showMessageDialog(null, "else: " + amount + " "
					+ visited);*/
			goToClosestOpenNode();
			return GOTO_SIGNAL;
		}
		return posibleDirections.get(r.nextInt(posibleDirections.size()));
	}

	/*
	 * Guarda una nueva coordenada critica encontrada
	 */
	public void save(boolean updateLast) {

		if (lastCriticalCoordinate != null
				&& !current.equals(lastCriticalCoordinate)) {
			
			//System.out.println( "Saving path from " + lastCriticalCoordinate + " to " + current );
			//System.out.println( "Path: " + pathInBuilding );
			boolean newNode = map.addEdge(lastCriticalCoordinate.clone(), current.clone(),
					pathInBuilding);
			if(newNode) timesBlocked = 0;
			restorePathInBuilding();
		}// else //System.out.println("lastCritical no Null");
		if (updateLast){
			lastCriticalHistory.add(lastCriticalCoordinate);
			lastCriticalCoordinate = current.clone();
		}
	}
	
	private void restorePathInBuilding(){
		pathInBuilding = new LinkedList<>();
	}
	
	private void breakEdge(Coordinate current2,
			Coordinate lastCriticalCoordinate2) {
		if (current2.equals(lastCriticalCoordinate2))
			/*JOptionPane.showMessageDialog(null,
					"Ambos son iguales en break edge " + current2);*/
		map.breakEdge(current2, lastCriticalCoordinate2);
	}

	/*
	 * Verifica si una coordenada critica se encuentra al lado pero aun no ha
	 * sido creado el enlace con esta
	 */
	private void verifyAdjacents() {
		////System.out.println("Verify adjacents" + current);
		Coordinate c = map.visit.get(current.coordinateTo(Orientation.NORTH));
		// //System.out.println(current);
		if (map.contains(c) && !current.verifyFrontWall(Orientation.NORTH)) {
			////System.out.println("\tAdjacent added: " + c);
			boolean newNode = map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
			if(newNode) timesBlocked = 0;
		}

		c = map.visit.get(current.coordinateTo(Orientation.WEST));
		if (map.contains(c) && !current.verifyLeftWall(Orientation.NORTH)) {
			////System.out.println("\tAdjacent added: " + c);
			boolean newNode = map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
			if(newNode) timesBlocked = 0;
		}

		c = map.visit.get(current.coordinateTo(Orientation.EAST));
		if (map.contains(c) && !current.verifyRightWall(Orientation.NORTH)) {
			////System.out.println("\tAdjacent added: t" + c);
			boolean newNode = map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
			if(newNode) timesBlocked = 0;
		}

		c = map.visit.get(current.coordinateTo(Orientation.SOUTH));
		if (map.contains(c) && !current.verifyBackWall(Orientation.NORTH)) {
			////System.out.println("\tAdjacent added: t" + c);
			boolean newNode = map.addEdge(current, map.getKey(c), new LinkedList<Coordinate>());
			if(newNode) timesBlocked = 0;
		}
	}

	/*
	 * Cantidad de vecinos visitados para una coordenada
	 */
	private int visitedNeighbors(Coordinate coordinate) {
		int visited = 0;
		Coordinate c = coordinate.coordinateTo(Orientation.NORTH);
		coordinate = map.visit.get(coordinate);
		if (map.visit.containsKey(c)
				&& !coordinate.verifyFrontWall(Orientation.NORTH))
			visited++;

		c = coordinate.coordinateTo(Orientation.WEST);
		// //System.out.println("\t" + c );
		if (map.visit.containsKey(c)
				&& !coordinate.verifyLeftWall(Orientation.NORTH))
			visited++;

		c = coordinate.coordinateTo(Orientation.EAST);
		// //System.out.println("\t" + c );
		if (map.visit.containsKey(c)
				&& !coordinate.verifyRightWall(Orientation.NORTH))
			visited++;

		c = coordinate.coordinateTo(Orientation.SOUTH);
		// //System.out.println("\t" + c );
		if (map.visit.containsKey(c)
				&& !coordinate.verifyBackWall(Orientation.NORTH))
			visited++;

		// //System.out.println(map.visit);
		return visited;
	}

	/*
	 * Revisa el caso en que la coordenada mas cercana está adyacente
	 */
	private Coordinate checkTrivialCase() {
		////System.out.println("Checking Trivial Case");
		ArrayList<Coordinate> t = new ArrayList<>();

		Coordinate c = current.coordinateTo(Orientation.NORTH);
		c = map.visit.get(c);
		if (map.contains(c) && !current.verifyFrontWall(Orientation.NORTH)
				&& c.getAmount() - visitedNeighbors(c) > 0)
			t.add(c);

		c = current.coordinateTo(Orientation.WEST);
		c = map.visit.get(c);
		if (map.contains(c) && !current.verifyFrontWall(Orientation.WEST)
				&& c.getAmount() - visitedNeighbors(c) > 0)
			t.add(c);

		c = current.coordinateTo(Orientation.EAST);
		c = map.visit.get(c);
		if (map.contains(c) && !current.verifyFrontWall(Orientation.EAST)
				&& c.getAmount() - visitedNeighbors(c) > 0)
			t.add(c);

		c = current.coordinateTo(Orientation.SOUTH);
		c = map.visit.get(c);
		if (map.contains(c) && !current.verifyFrontWall(Orientation.SOUTH)
				&& c.getAmount() - visitedNeighbors(c) > 0)
			t.add(c);

		if (t.size() == 0)
			return null;

		Coordinate next = t.get(r.nextInt(t.size()));
		addActions(new LinkedList<Coordinate>(), current, next,
				orientation.orientation);

		return next;
	}

	/*
	 * Cuenta las rotaciones para llegar de current a next dada una rotacion
	 */
	private int countRotationsToAdjacent(Coordinate current, Coordinate next,
			int orientation) {
		int o, dir = orientation;
		if (next.equals(current.coordinateTo(Orientation.NORTH)))
			o = Orientation.NORTH;

		else if (next.equals(current.coordinateTo(Orientation.WEST)))
			o = Orientation.WEST;

		else if (next.equals(current.coordinateTo(Orientation.EAST)))
			o = Orientation.EAST;

		else
			o = Orientation.SOUTH;

		return numberOfRotationsTo(dir, o);

	}

	/*
	 * Cuenta la cantidad de rotaciones totales en un path
	 */
	private int countRotationsInPath(LinkedList<Coordinate> path,
			Coordinate from, Coordinate to) {
		if (path == null || from == null || to == null)
			return 0;
		int orientation = this.orientation.orientation;

		Coordinate current = from;
		int r, ans = 0;
		for (Coordinate c : path) {
			r = countRotationsToAdjacent(current, c, orientation);
			ans += r;
			for (int i = 0; i < r; i++)
				orientation = (orientation + 1) % 4;
			current = c;
		}

		return ans;
	}

	/*
	 * En caso de que todos las coordenadas adyacentes esten visitadas se escoge
	 * una en la cual haya al menos una direccion disponible para explorar
	 */
	private Coordinate goToClosestOpenNode() {
		
		 //JOptionPane.showMessageDialog(null, "closest");
		//System.out.println("goToClosestNode: " + current);
		//System.out.println("\t" + current);
		if( map.size() == 0 )
			return null;
		
		verifyAdjacents();
		Coordinate adjacentSolution = checkTrivialCase();
		if (adjacentSolution != null)
			return adjacentSolution;

		PriorityQueue<ShortestPathNode> q = new PriorityQueue<>();
		q.add(new ShortestPathNode(current, 0, 0));

		ShortestPathNode current = null;
		Coordinate u = null, v = null;
		int vd, w, ud, rot; // rot -> rotations

		/*
		 * Cola de prioridad para extraer los posibles destinos
		 */
		/*
		 * Comparator<ShortestPathNode> xd = new
		 * Comparator<FirstAgent.ShortestPathNode>() {
		 * 
		 * @Override public int compare(ShortestPathNode arg0, ShortestPathNode
		 * arg1) { return (int) (arg0.getValue() - arg1.getValue()); } };
		 */

		PriorityQueue<ShortestPathNode> posibleCoordinates = new PriorityQueue<>();

		TreeMap<Coordinate, Integer> distances = new TreeMap<>();
		TreeMap<Coordinate, Integer> distancesSoFar = new TreeMap<>();
		TreeMap<Coordinate, Coordinate> parent = new TreeMap<>();
		TreeSet<Coordinate> visit = new TreeSet<>();
		parent.put(this.current, null);
		distancesSoFar.put(this.current, 0);
		
		while (!q.isEmpty()) {
			current = q.poll();
			u = current.coordinate;

			if (distances.containsKey(u))
				continue;

			ud = distancesSoFar.get(u);
			visit.add(u);
			distances.put(u, ud);
			posibleCoordinates.add(current);
			for (Entry<Coordinate, Edge> x : map.getNeighbors(u).entrySet()) {
				v = x.getKey();

				if (distances.containsKey(v))
					continue;

				vd = distancesSoFar.containsKey(v) ? distancesSoFar.get(v) : INFINITY;
				rot = countRotationsInPath(map.getPath(u, x.getKey()), u, x.getKey());
				w = x.getValue().getWeight();

				// //System.out.println("U: " + u + " V: " + v+ " W: "+ w);
				if (vd > ud + w + rot) {
					distancesSoFar.put(v, w + ud + rot);
					parent.put(v, u);
					q.add(new ShortestPathNode(v, w + ud, rot));
				}
			}
		}

		//System.out.println(distances);
		//System.out.println("PARENT: " + parent);

//		posibleCoordinates.poll();
		while (!posibleCoordinates.isEmpty()) {
			Coordinate coordinate = posibleCoordinates.poll().coordinate;
			if( coordinate.equals(this.current) ) continue;
			int visitedN = visitedNeighbors(coordinate);
			// //System.out.println( coordinate + " " + (coordinate.getAmount() -
			// visitedN) );
			if (coordinate.getAmount() - visitedN > 0) {
				LinkedList<Coordinate> path = new LinkedList<>();
				path.addFirst(coordinate);
				Coordinate next = parent.get(coordinate);
				//System.out.println( "Coordinate " + coordinate );
				//System.out.println("\tPATH: " + path);
				
				pathInBuilding.addAll(0,  map.getPath(next, coordinate) );

				while (!next.equals(this.current)) {
					//System.out.println(next);
					path.addFirst(next);
					pathInBuilding.addFirst(next);
					pathInBuilding.addAll(0,  map.getPath(parent.get(next), next) );
					next = parent.get(next);
					
				}
				// //System.out.println( "Camino al mas cercano: " + path );
				next = this.current;
				int o = orientation.orientation;
				for (Coordinate c : path) {
					o = addActions(map.getPath(next, c), next, c, o);
					next = c;
				}
				
				lastCriticalFlag();
				currentOperation = GO_TO_CLOSEST;
				System.out.println("The go to closest is: " + pathInBuilding);
				return coordinate.clone();
			}
		}
		
		System.out.println( "TODO el mapa ha sido explorado FELIPEDEMIERDA" );
		/* TODO el mapa ha sido explorado */
		cmd.add(language.getAction(NO_OP));
		return lastCriticalCoordinate;
	}
	
	private void lastCriticalFlag(){
		cmd.add(UPDATE_LAST_CRITICAL);
	}
	
	private void goBack(int orientation) {
		//System.out.println("Go back");
		// JOptionPane.showMessageDialog(null, "go back");
		addActions(map.getPath(current, lastCriticalCoordinate), current,
				lastCriticalCoordinate, orientation);
		currentOperation = GO_BACK;
	}

	/* Numero de rotaciones desde una orientacion hacia otra */
	private int numberOfRotationsTo(int from, int to) {
		return (4 - (from - to)) % 4;
	}

	/* Agrega las roaciones necesarias para llegar a una coordenada adyacente */
	private int rotationsTo(Coordinate current, Coordinate next, int orientation) {
		int o, dir = orientation;
		if (next.equals(current.coordinateTo(Orientation.NORTH)))
			o = Orientation.NORTH;

		else if (next.equals(current.coordinateTo(Orientation.WEST)))
			o = Orientation.WEST;

		else if (next.equals(current.coordinateTo(Orientation.EAST)))
			o = Orientation.EAST;

		else
			o = Orientation.SOUTH;

		int r = numberOfRotationsTo(dir, o);

		for (int i = 0; i < r; i++) {
			cmd.add(language.getAction(ROTATE));
			
			orientation = (orientation + 1) % 4;
		}
		cmd.add(language.getAction(ADVANCE));
		return orientation;
	}

	/*
	 * Agrega acciones necesarias para llegar desde un nodo a otro dado un
	 * camino
	 */
	private int addActions(LinkedList<Coordinate> path, Coordinate from,
			Coordinate to, int orientation) {
		Coordinate current = from;
		for (Coordinate c : path) {
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
	
	void avoidLoopByTimesBlocked(boolean FA){
		
		if(FA && timesBlocked >= MAX_BLOCKED_TIMES){
			JOptionPane.showMessageDialog(null, "Machete extremo");
			clearMemory();
		}
		
		/*if(FA && timesBlocked >= MAX_BLOCKED_TIMES){
			if( lastCriticalHistory.size() == 0 ){
				JOptionPane.showMessageDialog(null, "Machete extremo");
				clearMemory();
			}else{
				Coordinate newLast = null;
				while( newLast != lastCriticalCoordinate && lastCriticalHistory.size() > 0 ){
					newLast = lastCriticalHistory.poll();
				}
				
				if( newLast != lastCriticalCoordinate ){
					lastCriticalCoordinate = newLast;
				}else{
					JOptionPane.showMessageDialog(null, "Machete extremo");
					clearMemory();
				}
			}
		}*/
			
	}
	
	void clearMemory(){
		init();
	}
	
	/**
	 * execute
	 * 
	 * @param perception
	 *            Perception
	 * @return Action[]
	 */
	public Action compute(Percept p) {
		//try{	
			printCurrentOp();
			//System.out.println("Current: " + current);
			boolean FA = ((Boolean) p.getAttribute(language.getPercept(5))).booleanValue(); 
			boolean RA = ((Boolean) p.getAttribute(language.getPercept(6))) .booleanValue(); 
			boolean BA = ((Boolean) p.getAttribute(language.getPercept(7))).booleanValue(); 
			boolean LA = ((Boolean) p.getAttribute(language.getPercept(8))) .booleanValue();
			
			if(FA)
				timesBlocked++;
			avoidLoopByTimesBlocked(FA);
			
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
			
			if (cmd.size() == 0) {
	
				int d = rotate(FW, RW, BW, LW, FA, RA, BA, LA, T );
			//	int d = rotate(FW, RW, BW, LW, false, false, false, false, T);
				if (d == GOAL)
					return new Action(language.getAction(NO_OP));
				if (0 <= d && d < 4) {
					for (int i = 1; i <= d; i++) {
						cmd.add(language.getAction(ROTATE)); // rotate
	
					}
					cmd.add(language.getAction(ADVANCE)); // advance
	
				} else if (d == DIE) {
					cmd.add(language.getAction(DIE)); // die
				}
				
				if( cmd.size() == 0 ){
					cmd.add(language.getAction(NO_OP)); // NO OP
				}
			}
	
			
			/* METER CONTINGENCIA */
			
			//if( checkAgentInPossibleUpdateCoordinate(FA, RA, BA, LA) )
	
			String x = cmd.get(0);
			if( checkFlag(x) ){
				System.out.println( "Flag plz: " + cmd );
				lastCriticalHistory.add( lastCriticalCoordinate );
				lastCriticalCoordinate = current.clone();
				cmd.remove(0);
				restorePathInBuilding();
				timesBlocked--;
				return compute(p);
			}
			
			else if (x.equals(language.getAction(ADVANCE))) {
				
				//Solo si va a avanzar deberia verificar
				if( FA ){
					actWhenAgentInterrupted(FW, RW, BW, LW, FA, RA, BA, LA, T);
					timesBlocked--;
					return compute(p);
				}else{
					updateCoordinate();
				}
			} else if (x.equals(language.getAction(ROTATE))) {
				/* Actualiza la orientacion */
				orientation.orientation = (orientation.orientation + 1) % 4;
			}
	
			cmd.remove(0);
			debug.repaint();
			return new Action(x);
		/*}catch(Exception e){
			clearMemory();
			return compute(p);
		}*/
	}
	
	private boolean checkFlag( String x ){
		return x.equals(UPDATE_LAST_CRITICAL);
	}
	
	public void actWhenAgentInterrupted(boolean FW, boolean RW, boolean BW, boolean LW, boolean FA, boolean RA, 
			boolean BA, boolean LA, boolean T){
		
		System.out.println("INTERRRRRRRRRRRRRRRRRRUPPPPPPPPPPPPPPPPPTED");
		
		//Probabilidad de estar quieto
		int p = 60;
		if( currentOperation == SEARCHING ){
			
			if( r.nextInt(100) < p ){
				System.out.println( "NO_OP" );
				cmd.add(0, language.getAction(NO_OP));
				return;
			}
			JOptionPane.showMessageDialog(null, "did it!!");
			save(false);
			goToClosestOpenNode();
			return;
		}
		
		
		 p = 70;
		if( r.nextInt(100) < p || 
				currentOperation == GO_BACK ) {
			System.out.println( "NO_OP" );
			cmd.add(0, language.getAction(NO_OP));
			return;
		}
		
		
		
		
		/* PAILAAAAS */
		System.out.println("Pailaaaas");
		cmd.clear();
		//System.out.println( "GoBack" + current + " " + lastCriticalCoordinate );
		System.out.println( pathInBuilding );
		
		Coordinate temp = null;
		
		do{
			temp = pathInBuilding.pollLast();
			System.out.println(temp);
		}while( pathInBuilding.size() > 0 && !temp.equals(current) );
		System.out.println(pathInBuilding);
		
		save(false);
		goBack(orientation.orientation);
		breakEdge(current, lastCriticalCoordinate);
		
		//System.out.println(orientation.orientation + " cmd " + cmd);
	}
	
	private Vector<String> cloneCmd(){
		Vector<String> v = new Vector<>();
		for( String s : cmd )
			v.add( s );
		return v;
		
	}
	
	private void addWaitActions( int n ){
		for (int i = 0; i < n; i++) {
			cmd.add(0,language.getAction(NO_OP));
		}
	}
	
	private boolean checkAgentInPossibleUpdateCoordinate( 
			boolean FA, boolean RA, boolean BA, boolean LA ){
	
			switch (orientation.orientation) {
				case Orientation.NORTH:
					return FA;
				case Orientation.SOUTH:
					return BA;
			
				case Orientation.EAST:
					return RA;
			
				case Orientation.WEST:
					return LA;
			}
			
			throw new IllegalArgumentException("bad given orientation ");

	}

	// Cuenta los nodos a los que puede seguir visitando
	private int countFreeNodes(Coordinate c) {
		return c.getAmount() - visitedNeighbors(c);
	}

	class ShortestPathNode implements Comparable<ShortestPathNode> {
		protected Coordinate coordinate;
		protected int weight;
		protected int rotations;

		public ShortestPathNode(Coordinate coo, int weight, int rotations) {
			this.coordinate = coo;
			this.weight = weight;
			this.rotations = rotations;

		}

		@Override
		public int compareTo(ShortestPathNode sn) {
			if (weight + rotations != sn.weight + sn.rotations)
				return (weight + rotations) - (sn.weight + sn.rotations);
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
