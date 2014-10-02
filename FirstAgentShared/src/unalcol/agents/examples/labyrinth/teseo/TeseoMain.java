package unalcol.agents.examples.labyrinth.teseo;
import unalcol.agents.Agent;
import unalcol.agents.AgentProgram;
import unalcol.agents.examples.labyrinth.Labyrinth;
import unalcol.agents.examples.labyrinth.LabyrinthDrawer;
import unalcol.agents.examples.labyrinth.multeseo.grupo7.Debug;
import unalcol.agents.examples.labyrinth.multeseo.grupo7.Grupo7Agent;
import unalcol.agents.examples.labyrinth.multeseo.grupo7.LabyrinthMap;
import unalcol.agents.examples.labyrinth.multeseo.grupo7.SecondAgentGrupo7;
import unalcol.agents.examples.labyrinth.teseo.simple.RandomReflexTeseo;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.SimpleLanguage;

public class TeseoMain {
  private static SimpleLanguage getLanguage(){
    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "exit",
        "afront", "aright", "aback", "aleft"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }
 
  public static void main( String[] argv ){
    //  InteractiveAgentProgram p = new InteractiveAgentProgram( getLanguage() );
    //TeseoSimple p = new TeseoSimple();
	  SecondAgentGrupo7 p = new SecondAgentGrupo7(); 
    //RandomReflexTeseo p = new RandomReflexTeseo();
	//SimpleTeseoAgentProgram p = new FirstAgent(); 
    p.setLanguage(getLanguage());
    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
    LabyrinthDrawer.CELL_SIZE = 40;
    Labyrinth.DEFAULT_SIZE = 15;
    Agent agent = new Agent( p );
    TeseoMainFrame frame = new TeseoMainFrame( agent, getLanguage() );
    frame.setVisible(true); 
  }
}
