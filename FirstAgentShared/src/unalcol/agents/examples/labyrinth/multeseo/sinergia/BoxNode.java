package unalcol.agents.examples.labyrinth.multeseo.sinergia;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Miguel A. Rodriguez
 */

public class BoxNode  {

    int x;
    int y;
    int anterior;
    BoxNode padre;
    BoxNode[]  neighbors = new BoxNode[5];
    boolean bloqueado;

    public BoxNode(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 5; i++) {
            neighbors[i]=null;
        }
        bloqueado = false;
    }

    public BoxNode searchNode(int a, int b, BoxNode act) {
 
        for (BoxNode child : neighbors) {
            if (child != null) {
                if (child.x != act.x || child.y != act.y) {
                    
                    if (child.x == a && child.y == b) {
                        return child;
                    } else {
                        return  child.searchNode(a, b, this);
                    }
                }
            }
        }
        return null;
    }
    public BoxNode search(BoxNode s){
        for (BoxNode child : neighbors) {
            if (child!=null){
                if(this.equals(child))
                    return child;
                else
                        for(BoxNode n:child.neighbors){
                            if(n!=null){
                            if(!this.equals(n))
                                return n.search(s);
                            }
                        }
            } 
        }
        return null;
    }    
    
    public boolean equals(BoxNode o){
        if (o==null)
            return false;
        if(o.x==this.x&&o.y==this.y)
            return true;
        else
            return false;
    
    }
          public void es(){
          System.out.println("s");
          }
          
          public int size(BoxNode act){
        int i =0;
        for (BoxNode child : neighbors) {
            if (child!=null){
                i++;
            }
        }
        return i;
    }
}
