package unalcol.agents.examples.labyrinth.teseo.grupo7;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Debug extends JFrame {
	
	Grupo7If agent;
	String title;
	int space = 30; //pixeles
	public Debug(Grupo7If a){
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
			 
			 setTitle("ORIENTATION: " + agent.getOrientation());
			 for( Coordinate c : agent.getMap().visit.keySet() ){
				 g.drawOval( Math.abs(c.x)*space,  Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.RED);
			 
			 for( Coordinate c : agent.getMap().graph.keySet() ){
				 g.drawOval( Math.abs(c.x)*space, Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.CYAN);
			 
			 for( Coordinate c : agent.getPathInBuilding() ){
				 g.drawOval( Math.abs(c.x)*space, Math.abs(c.y)*space, space, space);
			 }
			 
			 g.setColor(Color.BLACK);
			 for( Coordinate u : agent.getMap().graph.keySet() ){
				 for( Coordinate v : agent.getMap().getNeighbors(u).keySet() ){
					 g.drawLine( Math.abs(u.x)*space + space/2 , Math.abs(u.y)*space + space/2
							 ,  Math.abs(v.x)*space + space/2, Math.abs(v.y)*space + space/2);
					 
					 g.fillOval( Math.abs(u.x)*space + space/4, Math.abs(u.y)*space + space/4, space/2, space/2);
					 g.fillOval( Math.abs(v.x)*space + space/4, Math.abs(v.y)*space + space/4, space/2, space/2);
				 }
			 }
			 
			 g.setColor(Color.MAGENTA);
			 Coordinate t = agent.getLastCoordinate();
			 if(t!=null)
			 g.drawOval( Math.abs(t.x)*space + space/4, Math.abs(t.y)*space + space/4, space/2, space/2);
			 
			 g.setColor(Color.BLUE);
			 g.drawOval( Math.abs(agent.getCurrent().x)*space, Math.abs(agent.getCurrent().y)*space, space, space);
			 
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
