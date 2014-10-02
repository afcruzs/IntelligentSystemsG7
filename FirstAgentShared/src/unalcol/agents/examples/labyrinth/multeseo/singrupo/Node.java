/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.singrupo;

/**
 *
 * @author Daniel Felipe
 */
public class Node {
    
     private int x;
     private int y;
     private int d;
     /*
      * Variable que me dice los caminos posibles desde el nodo,
      * sin explorar(S), si es invalido(I) o si ya pase por ese 
      * camino(R). Las posiciones se asocian a la posición global
      */
     private char[] caminos;

    public Node(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.caminos = new char[4];
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }
    
    public char[] getCaminos() {
        return caminos;
    }
    
    public void setCaminos(char[] caminos) {
        this.caminos = caminos;
    }

    //</editor-fold>
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.x;
        hash = 89 * hash + this.y;
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
        final Node other = (Node) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
}
