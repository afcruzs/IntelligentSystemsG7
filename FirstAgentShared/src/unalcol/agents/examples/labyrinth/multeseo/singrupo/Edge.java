/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.singrupo;

import java.util.Objects;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Daniel Felipe
 */
public class Edge {
    private Node nodoA;
    private Node nodoB;
    private Vector<String> cmds;
    
    public Edge(Node _a, Node _b){
        this.nodoA = _a;
        this.nodoB = _b;
        this.cmds = new Vector<>();
    }
    
    public Edge(Node _a, Node _b, Vector<String> _c) {
        this.nodoA = _a;
        this.nodoB = _b;
        this.cmds = _c;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Node getNodoA() {
        return nodoA;
    }
    
    public void setNodoA(Node nodoA) {
        this.nodoA = nodoA;
    }
    
    public Node getNodoB() {
        return nodoB;
    }
    
    public void setNodoB(Node nodoB) {
        this.nodoB = nodoB;
    }
    
    public Vector<String> getCmds() {
        return cmds;
    }
    
    public void setCmds(Vector<String> cmds) {
        this.cmds = cmds;
    }

    //</editor-fold>
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.nodoA);
        hash = 67 * hash + Objects.hashCode(this.nodoB);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.nodoA, other.nodoA)) {
            return false;
        }
        if (!Objects.equals(this.nodoB, other.nodoB)) {
            return false;
        }
        return true;
    }
    
}
