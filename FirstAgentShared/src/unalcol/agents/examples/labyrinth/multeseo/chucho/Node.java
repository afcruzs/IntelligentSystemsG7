/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.chucho;

import unalcol.agents.examples.labyrinth.multeseo.chucho.Arch;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author chucho
 */
public class Node {

    int x;
    int y;
    List<Arch> archs;
    
    public int revisedArchs() {
        int aux = 0;
        for (Arch arch : archs) {
            if (arch.visited) {
                aux++;
            }
        
        }
        return aux;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.archs = new LinkedList();
       
    }
    @Override
    public String toString(){
        return ""+this.x+" "+this.y;
    }
    
    public boolean yaEsta(Node node){
        for(Arch arch: archs){
            if(arch.node2.equals(node)||arch.node1.equals(node))
                return true;
        }
        return false;
    }
}
