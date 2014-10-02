/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.singrupo;

/**
 *
 * @author Daniel Felipe
 */
public class Location {
    private int direccion;
    private int x;
    private int y;

    public Location(int direction, int x, int y) {
        this.direccion = direction;
        this.x = x;
        this.y = y;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

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
    
}
