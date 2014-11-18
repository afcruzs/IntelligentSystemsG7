/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares;


import unalcol.agents.Agent;
import unalcol.agents.examples.squares.grupo7.Grupo7BoxesAgent;
import unalcol.agents.examples.squares.grupo7.Matrix;
import unalcol.agents.examples.squares.grupo7.Agent2;
import unalcol.agents.examples.squares.grupo7.Matrix2;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
  public static void main( String[] argv ){

	  //System.out.println(m.possibleLines.get(8) + " " + m.isNotDumb(m.possibleLines.get(8)));
	  /*for(unalcol.agents.examples.squares.grupo7.Matrix.Line l : m.possibleLines){
		  System.out.println( l + " " +m.isNotDumb(l) );
	  }*/
    // Reflection
    ServiceProvider provider = ReflectUtil.getProvider("services/");
    Agent w_agent = new Agent( new Grupo7BoxesAgent( Squares.WHITE ));
    Agent b_agent =  new Agent( new Grupo7BoxesAgent( Squares.BLACK ));
    //Agent b_agent = new Agent( new ReversiSinGrupoAPv2(Reversi.BLACK) );
    //Agent w_agent = new Agent( new NoTanDummiReversiAgentProgram(Reversi.WHITE) );
    SquaresMainFrame frame = new SquaresMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
    
}

