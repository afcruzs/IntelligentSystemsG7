/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.multeseo.sinergia;

/**
 *
 * @author Miguel A. Rodriguez
 */

import unalcol.agents.Agent;

import unalcol.agents.examples.labyrinth.*;
import unalcol.agents.examples.labyrinth.teseo.TeseoMainFrame;
import unalcol.agents.simulate.util.*;

public class LabyrinthTest {
  private static SimpleLanguage getLanguage(){
    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "exit", "afront", "aright", "aback", "aleft"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }

  public static void main( String[] argv ){
    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
    LabyrinthDrawer.CELL_SIZE = 40;
    Labyrinth.DEFAULT_SIZE = 15;
    Agent agent = new Agent( new LabyrinthAgentProgram( getLanguage() ) );
    TeseoMainFrame frame = new TeseoMainFrame( agent, getLanguage() );
    frame.setVisible(true);
  }
}