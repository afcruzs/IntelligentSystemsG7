/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.targetTeam;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Camilo
 */
public class AgentTarget extends SimpleTeseoAgentProgram{
    
//  protected SimpleLanguage language = new SimpleLanguage( new String[]{"front", "right", "back", "left", "exit", "afront", "aright", "aback", "aleft",},
//                                   new String[]{"no_op", "die", "advance", "rotate"}
//                                   );
  public SparseMatrix matrix;
  public int x;
  public int y;
  public int dir;
  
@Override
  public void init(){
    cmd.clear();
    int[] paths = {0,0,0,0};
    Data data = new Data(paths);
    matrix = new SparseMatrix(data);
    x=0;
    y=0;
    dir=0;
    language = new SimpleLanguage( new String[]{"front", "right", "back", "left", "exit", "afront", "aright", "aback", "aleft",},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }
  /**
   * execute
   *
   * @param perception Perception
   * @return Action[]
   */
  @Override
  public Action compute(Percept p){
    if( cmd.size() == 0 ){

      boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0))).
          booleanValue();
      boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1))).
          booleanValue();
      boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2))).
          booleanValue();
      boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3))).
          booleanValue();
      boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4))).
          booleanValue();

      int d = accion(PF, PD, PA, PI, MT);
      if (0 < d && d < 4) {
        for (int i = 1; i <= d; i++) {
          cmd.add(language.getAction(3)); //rotate
        }
        cmd.add(language.getAction(2)); // advance
      }
      else {
        cmd.add(language.getAction(0)); // die
      }
    }
    String x = cmd.get(0);
    cmd.remove(0);
    return new Action(x);
  }

  /**
   * goalAchieved
   *
   * @param perception Perception
   * @return boolean
   */
  public boolean goalAchieved( Percept p ){
    return (((Boolean)p.getAttribute(language.getPercept(4))).booleanValue());
  }
  
   public int accion( boolean PF, boolean PD, boolean PA, boolean PI, boolean MT ){
       System.out.println("Hola");  
       if(MT)return 10;
         int pathcount = 1;
	 if(PF) pathcount++;
	 if(PD) pathcount++;
	 if(PI) pathcount++;
	 
	 if(pathcount == 1){
		dir = (dir+2)%4;
		return 2;
	 }
	 if(pathcount == 2){
		if(!PF){ 
			return 0;
		}
		else if(!PD){
			dir = (dir+1)%4;
			return 1;
		}
		else if (!PI){ 
			dir = (dir+3)%4;
			return 3;
		}
	 }
	 if(pathcount > 3){
	 
		Node actualNode = matrix.retriveNode(x,y);
		Data actualData;
		if(actualNode == null){
			Data newData = new Data();
			newData.changePaths((dir+2)%4,Data.VISITED);
			if(PF){
                            newData.changePaths(dir%4,Data.CLOSED);
                            
                        }else if(PD){
                            newData.changePaths((dir+1)%4,Data.CLOSED);
                        }else if(PI){
                            newData.changePaths((dir+3)%4,Data.CLOSED);
                        }
			//Node newNode = new Node(newData);
			matrix.add(x,y,newData);
			actualNode = matrix.retriveNode(x,y);
		}else{
			actualData = (Data)actualNode.getData();
                        actualData.changePaths((dir+2)%4,Data.VISITED);
		}
		actualData = (Data)actualNode.getData();
		if(actualData.getPaths(dir%4)==0){
			actualData.changePaths(dir%4,Data.CLOSED);
			return 0;
		}
		if(actualData.getPaths((dir+1)%4)==0){
			actualData.changePaths(dir%4,Data.CLOSED);
			dir = (dir+1)%4;
			return 1;
		}
		if(actualData.getPaths((dir+3)%4)==0){
			actualData.changePaths(dir%4,Data.CLOSED);
			dir = (dir+3)%4;
			return 3;
		}
		
		if(actualData.getPaths(dir%4)==1){
			actualData.changePaths(dir%4,Data.CLOSED);
			return 0;
		}
		if(actualData.getPaths((dir+1)%4)==1){
			actualData.changePaths(dir%4,Data.CLOSED);
			dir = (dir+1)%4;
			return 1;
		}
		if(actualData.getPaths((dir+3)%4)==1){
			actualData.changePaths(dir%4,Data.CLOSED);
			dir = (dir+3)%4;
			return 3;
		}
		
		matrix.remove(x,y);
		return 2;
	}
      return 0;
    }
}
