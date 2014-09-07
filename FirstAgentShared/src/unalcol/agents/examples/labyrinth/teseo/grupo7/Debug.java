package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Debug extends JFrame {
	
	FirstAgent agent;
	String title;
	int space = 30; //pixeles
	public Debug(FirstAgent a){
		agent = a;
		title = "Debug :v";
		setTitle(title);
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(500, 500);
		add(new DebugPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class DebugPanel extends JPanel{
		
		 @Override
		 public void paintComponent(Graphics g){
			 
			 setTitle("ORIENTATION: " + agent.orientation);
			 for( Coordinate c : agent.map.visit.keySet() ){
				 g.drawOval(Math.abs(c.x)*space, Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.RED);
			 
			 for( Coordinate c : agent.map.graph.keySet() ){
				 g.drawOval(Math.abs(c.x)*space, Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.YELLOW);
			 
			 for( Coordinate c : agent.pathInBuilding ){
				 g.drawOval(Math.abs(c.x)*space, Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.BLACK);
			 for( Coordinate u : agent.map.graph.keySet() ){
				 for( Coordinate v : agent.map.getNeighbors(u).keySet() ){
					 g.drawLine(Math.abs(u.x)*space + space/2 , Math.abs(u.y)*space + space/2
							 , Math.abs(v.x)*space + space/2, Math.abs(v.y)*space + space/2);
					 
				 }
			 }
			 
			 g.setColor(Color.MAGENTA);
			 Coordinate t = agent.lastCriticalCoordinate;
			 if(t!=null)
			 g.drawOval(Math.abs(t.x)*space + space/4, Math.abs(t.y)*space + space/4, space/2, space/2);
			 
			 g.setColor(Color.BLUE);
			 g.drawOval(Math.abs(agent.current.x)*space, Math.abs(agent.current.y)*space, space, space);
			 
		 }
		
	}
	
	/*public static void main(String[] args) {
		LabyrinthMap m = new LabyrinthMap();
		m.visit.put(new Coordinate(0, 0), new Coordinate(0, 0));
		m.visit.put(new Coordinate(1, 0), new Coordinate(1, 0));
		m.visit.put(new Coordinate(2, 0), new Coordinate(2, 0));
		m.visit.put(new Coordinate(2, 1), new Coordinate(2, 1));
		m.visit.put(new Coordinate(2, -7), new Coordinate(2, -7));
		Debug d =  new Debug(m);
	}*/
}
