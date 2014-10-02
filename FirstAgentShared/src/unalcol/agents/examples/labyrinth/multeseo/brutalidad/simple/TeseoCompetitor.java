/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;


import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 *
 * @author juanpablo
 */
public class TeseoCompetitor extends SimpleTeseoCompetitorAgentProgram{

    private int orientation=0;
    //Orientacion inicial
    
    private short x=0;
    private short y=0;
    //Posicion inicial
 
    private final Stack<Node> stack;
    private final WareHouseNode map ;
    private final Stack<Integer> posible_decitions;
    private final Random gen ;
    
    public TeseoCompetitor(SimpleLanguage leng){
    this.setLanguage(leng);
    map = new WareHouseNode();
    stack= new Stack<Node>();
    posible_decitions = new Stack<Integer> ();
    gen= new Random();
 
    
    }
   
    @Override
    public int accion( boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AI, boolean AA ) {
       if(MT){return 5;}
       this.posible_decitions.clear();
       int action=-1;
       Node current = null;
       String current_key=computeKey(x,y);
       //JOptionPane.showMessageDialog(null, "current_key: "+current_key+" Orientation: "+this.orientation);
       if(!map.existNode(current_key)){
          
            boolean[] nwse= {PF,PI,PA,PD};
           
           nwse = TransposeFlagsByOrientation(nwse,4-orientation);//nort, east, south, west
           //Toma la informacion que siente y la transpone en un formato de orientacion universal.
           //JOptionPane.showMessageDialog(null, "|| Norte: "+nwse[0]+" || Occidente: "+nwse[1]+" || Sur: "+nwse[2]+" || Oriente: "+nwse[3]);
           
           current = new Node(x,y,nwse[0],nwse[2],nwse[3],nwse[1]);
           //Crea el nuevo nodo con la informacion de posibles caminos.
           
           map.addNode(current);
           //Agrega el nodo al mapa
           
           stack.push(current);
        //Ubica el nodo actual como el ultimo visitado
       }else{
       //JOptionPane.showMessageDialog(null, "Nodo existe!");     
       current = map.getNode(current_key);
       //Si ya ha sido descubierto carga la información
       
       }
       
       if(!current.getDiscover()[0]){current.getDiscover()[0]=current.getDiscover()[0] || map.existNode(computeKey(x,y+1));};
       if(!current.getDiscover()[1]){current.getDiscover()[1]=current.getDiscover()[1] || map.existNode(computeKey(x-1,y));};
       if(!current.getDiscover()[2]){current.getDiscover()[2]=current.getDiscover()[2] || map.existNode(computeKey(x,y-1));};
       if(!current.getDiscover()[3]){current.getDiscover()[3]=current.getDiscover()[3] || map.existNode(computeKey(x+1,y));};
       
       if(current.isInteresting()){
           //si el nodo es interesante y no ha tomado una decision
       //JOptionPane.showMessageDialog(null, "Nodo interesante! "+current_key);
           
           
       
    
       
       boolean[] flbr = TransposeFlagsByOrientation(current.getDiscover(),orientation);//front, left, back, right
       //Cambia el formato de orientacion universal al formato subjetivo.
       
        //JOptionPane.showMessageDialog(null, "|| Frente: "+flbr[0]+" || Izquierda: "+flbr[1]+" || Atras: "+flbr[2]+" || Derecha: "+flbr[3]);
       
       if (AF || AD || AI || AA){JOptionPane.showMessageDialog(null, " ||AF: "+AF+" ||AI:"+AI+" ||AD:"+AD+" ||AA:"+AA);}
       if(!flbr[0] && !AF){posible_decitions.add(0);}//Si no ha sido descubierto el frente, ir al frente siempre que no haya un agente allí
       if(!flbr[1] && !AI){posible_decitions.add(3);}//Ir a la izquierda
       if(!flbr[3] && !AD){posible_decitions.add(1);}//Ir a la derecha
       if(!flbr[2] && !AA){posible_decitions.add(2);}//Ir a atras  
      
       if(posible_decitions.size()<=0){
        //Si no hay para donde explorar, hechar reversa   
           if(!PF && !AF){posible_decitions.add(0);}
           if(!PI && !AI){posible_decitions.add(3);}
           if(!PD && !AD){posible_decitions.add(1);}
           if(!PA && !AA){posible_decitions.add(2);}
           
           if(posible_decitions.size()<=0) {
               
               //Si no es posible hechar reversa, quedarse quieto hasta que los demas agentes que bloquean el camino se quiten
           posible_decitions.add(5);
           }else{
           stack.push(current);
           }
           
          
           //finalmente no hay posibilidad de moverse
       }
       }else{  
           
           //JOptionPane.showMessageDialog(null, "Nodo aburrido "+current_key);
       //si el nodo actual no es interesante y si no ha tomado una decision,
       //lo debe borrar del mapa y debe regresar al ultimo nodo donde estuvo.
           
           //map.removeNode(current_key);
           stack.pop();
           Node last = stack.peek();
           this.posible_decitions.add(move(current.x,current.y,last.x,last.y));
       
       }
       action=this.posible_decitions.get((int)gen.nextInt(this.posible_decitions.size()));
       
       if(action==0){orientation=(orientation+4)%4;}
       if(action==1){orientation=(orientation+3)%4;}
       if(action==3){orientation=(orientation+1)%4;}
       if(action==2){orientation=(orientation+2)%4;}
       
       if(orientation==0){current.setDiscovered(orientation, true);y++;}//North
       if(orientation==1){current.setDiscovered(orientation, true);x--;}//West
       if(orientation==2){current.setDiscovered(orientation, true);y--;}//South
       if(orientation==3){current.setDiscovered(orientation, true);x++;}//East
        
       //JOptionPane.showMessageDialog(null,"Accion: "+action);
        return action;
    }
   
    
    public String computeKey(int _x, int _y){
             return ""+_x+" "+_y;
         }
    
    public boolean[] TransposeFlagsByOrientation(boolean[] flags, int orientation){
    boolean[] result= new boolean[4];
    for(int i=0;i<4;i++){
    result[i]=flags[(i+orientation)%4];
    }
    
    return result;
    }
    
    
    public int move(int from_x, int from_y,int to_x,int to_y){
        int _orientation;
        int result=-1;
        for(int j=0;j<4;j++){
        _orientation=(orientation+j)%4;
        if(_orientation==0 && from_x==to_x && (from_y+1)==to_y){result=j;}//Frente
        if(_orientation==1 && (from_x-1)==to_x && from_y==to_y){result=j;}//Izquierda
        if(_orientation==2 && from_x==to_x && (from_y-1)==to_y){result= j;}//Atras
        if(_orientation==3 && (from_x+1)==to_x && from_y==to_y){result= j;}//Derecha
        }
        if(result==1){return 3;}
        if(result==3){return 1;}
    return result;
    }
    
 

    
}
