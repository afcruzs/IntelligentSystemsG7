package unalcol.agents.examples.labyrinth.teseoeater.grupo7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
			
		public DebugPanel(){
			setBackground(Color.WHITE);
		}
		 @Override
		 public void paintComponent(Graphics g){
		     super.paintComponent(g);
		     Graphics2D g2d = (Graphics2D)g;
		     
		     g2d.setRenderingHint(
		    		    RenderingHints.KEY_ANTIALIASING,
		    		    RenderingHints.VALUE_ANTIALIAS_ON);
		     
			 setTitle("ORIENTATION: " + agent.getOrientation());
			 g.setColor(Color.GRAY);
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
					 
					 g.fillOval( Math.abs(u.x)*space + space/4, Math.abs(u.y)*space + space/4, space/2, space/2);
					 g.fillOval( Math.abs(v.x)*space + space/4, Math.abs(v.y)*space + space/4, space/2, space/2);
					 
					 g.drawLine( Math.abs(u.x)*space + space/2 , Math.abs(u.y)*space + space/2
							 ,  Math.abs(v.x)*space + space/2, Math.abs(v.y)*space + space/2);
					 
					 Color prev = g.getColor();
					 g.setColor(Color.MAGENTA);
					 int a = (int) ( Math.abs(u.x)*space +  Math.abs(v.x)*space+ space)/2;
					 int b = (int) ( Math.abs(u.y)*space +  Math.abs(v.y)*space+ space)/2;
					 g.drawString(String.valueOf(agent.getMap().getWeight(u,v))
							 , a,b  );
					 g.setColor(prev);
					 
				 }
			 }
			 
			 g.setColor(Color.CYAN);
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
