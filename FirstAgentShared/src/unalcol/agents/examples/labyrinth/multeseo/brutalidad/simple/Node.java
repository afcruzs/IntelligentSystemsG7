/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package unalcol.agents.examples.labyrinth.multeseo.brutalidad.simple;
package unalcol.agents.examples.labyrinth.teseo.brutalidad.simple;

/**
 *
 * @author juanpablo
 */
   public class Node{
    
    public short y;
    public short x;
    private boolean[] discover;
    
    
    
        public Node(short _x, short _y, boolean north, boolean south, boolean east, boolean west){
        discover= new boolean[4];    
        discover[0]=north;
        discover[1]=west;
        discover[2]=south;
        discover[3]=east;
        y=_y;
        x=_x;
        }
         
        
        public String getKey(){
        return ""+x+" "+y;
        //La llave de cada nodo es un indice compuesto de los dos enteros.
        }
        
        
        public void setDiscovered(int orientation, boolean value){
        discover[orientation]=value;
        }
        
        public boolean[] getDiscover(){
        return discover;
        }
        
        
        
        public boolean isInteresting(){
         return !discover[0] || !discover[1] || !discover[2] || !discover[3];
         //Todo nodo es interesante cuando tiene al menos un camino inexplorado.
        }
        
        
    }