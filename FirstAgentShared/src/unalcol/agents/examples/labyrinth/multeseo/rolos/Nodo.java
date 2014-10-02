/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.multeseo.rolos;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Nodo {
    private Nodo[] nodos = {null,null,null,null};
    private boolean [] hojas = {false,false,false,false};
    private int peso;
    private int x;
    private int y;
    private boolean nodo_hoja=false;

    public Nodo(int x, int y) {
        this.peso = 0;
        this.x = x;
        this.y = y;
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

    public Nodo[] getNodos() {
        return nodos;
    }

    public void setNodos(Nodo[] nodos) {
        this.nodos = nodos;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean isNodo_hoja() {
        return nodo_hoja;
    }

    public void setNodo_hoja(boolean nodo_hoja) {
        this.nodo_hoja = nodo_hoja;
    }

    public boolean[] getHojas() {
        return hojas;
    }

    public void setHojas(boolean[] hojas) {
        this.hojas = hojas;
    }


   
    
    
    
}
