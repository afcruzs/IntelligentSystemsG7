package unalcol.agents.examples.labyrinth.teseo.simple;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import unalcol.agents.Agent;
import unalcol.agents.examples.labyrinth.Labyrinth;
import unalcol.agents.examples.labyrinth.Labyrinth;
import unalcol.agents.examples.labyrinth.LabyrinthDrawer;
import unalcol.agents.examples.labyrinth.LabyrinthDrawer;
import unalcol.agents.examples.labyrinth.teseo.TeseoMainFrame;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 *
 * @author juanpablo
 */
public class TestTeseoCompetitor {
    
    private static SimpleLanguage getLanguage(){
    return  new SimpleLanguage( new String[]{"exit","front", "right", "back", "left", "afront", "aright", "aleft", "aback"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
    LabyrinthDrawer.CELL_SIZE = 40;
    Labyrinth.DEFAULT_SIZE = 15;
    
    Agent agent = new Agent( new TeseoCompetitor( getLanguage() ) );
    TeseoMainFrame frame = new TeseoMainFrame( agent, getLanguage() );
    frame.setVisible(true);
        
        
        
        
    }
    
}
