/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.simple;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author juanpablo
 */
public abstract class SimpleTeseoCompetitorAgentProgram implements AgentProgram{
 
    protected SimpleLanguage language;
  protected Vector<String> cmd = new Vector<String>();
  
  
  public SimpleTeseoCompetitorAgentProgram() {
  }

  public void setLanguage(  SimpleLanguage _language ){
    language = _language;
  }

  public void init(){
    cmd.clear();
  }

  public abstract int accion( boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AI, boolean AA );

  /**
   * execute
   *
   * @param perception Perception
   * @return Action[]
   */
  public Action compute(Percept p){
    if( cmd.size() == 0 ){
      boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0))).//language.getPerceptIndex("front")
          booleanValue();//Front
      boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1))).//language.getPerceptIndex("right")
          booleanValue();//Right
      boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2))).//language.getPerceptIndex("back")
          booleanValue();//Back
      boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3))).//language.getPerceptIndex("left")
          booleanValue();//Left
      boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4))).//language.getPerceptIndex("exit")
          booleanValue();//Exit      
      boolean AF = ( (Boolean) p.getAttribute(language.getPercept(5))).//language.getPerceptIndex("afront")
          booleanValue();//Agente al Frente
      boolean AD = ( (Boolean) p.getAttribute(language.getPercept(6))).//language.getPerceptIndex("aright")
          booleanValue();//Agente Derecha
      boolean AI = ( (Boolean) p.getAttribute(language.getPercept(7))).//language.getPerceptIndex("aback")
          booleanValue();//Agente Izquierda
      boolean AA = ( (Boolean) p.getAttribute(language.getPercept(8))).//language.getPerceptIndex("aleft")
          booleanValue();//Agente Atras
      int d = accion(PF, PD, PA, PI, MT, AF, AD, AI, AA);
      if (0 <= d && d < 4) {
        for (int i = 1; i <= d; i++) {            
            cmd.add(language.getAction(3)); //rotate//language.getActionIndex("rotate")
        }
        
        cmd.add(language.getAction(2)); // advance//language.getActionIndex("advance")
      }
      else {
         
        cmd.add(language.getAction(0)); // no_op//language.getActionIndex("no_op")
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
    return (((Boolean)p.getAttribute(language.getPercept(5))).booleanValue());//language.getPerceptIndex("exit")
  }   
}
