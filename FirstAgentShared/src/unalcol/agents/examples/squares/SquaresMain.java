/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares;

import unalcol.agents.Agent;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
  public static void main( String[] argv ){
    // Reflection
    ServiceProvider provider = ReflectUtil.getProvider("services/");
    Agent w_agent = new Agent( new DummySquaresAgentProgram( Squares.WHITE ));
    Agent b_agent =  new Agent( new DummySquaresAgentProgram( Squares.BLACK ));
    //Agent b_agent = new Agent( new ReversiSinGrupoAPv2(Reversi.BLACK) );
    //Agent w_agent = new Agent( new NoTanDummiReversiAgentProgram(Reversi.WHITE) );
    SquaresMainFrame frame = new SquaresMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
    
}

