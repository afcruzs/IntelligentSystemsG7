package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.util.LinkedList;

public interface Grupo7If {
	LabyrinthMap getMap();
	LinkedList<Coordinate> getPathInBuilding();
	Orientation getOrientation();
	Coordinate getLastCoordinate();
	Coordinate getCurrent();
}
