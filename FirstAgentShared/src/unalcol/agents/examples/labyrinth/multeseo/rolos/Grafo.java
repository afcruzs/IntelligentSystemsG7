/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rolos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Admin
 */
public class Grafo {

    private Nodo root = null;
    private int size=0;

    public Grafo(Nodo _nodo) {
        root = _nodo;
        root.setPeso(root.getPeso() + 1);
        size++;
    }

    public Nodo getNodo(int _x, int _y) {
        return null;
    }

    public boolean crearNodo(int _x, int _y, int posicion) {
        Nodo aux;
        switch (posicion) {
            case 0:
                if(!root.getHojas()[0]){
                    aux = hasNodo(new Nodo(_x, _y + 1));
                    if (aux == null ) {
                        root.getNodos()[0] = new Nodo(_x, _y + 1);
                        root.getNodos()[0].getNodos()[2] = root;
                        size++;
                    } else {
                        root.getNodos()[0] = aux;
                        if(!aux.isNodo_hoja()){
                            aux.getNodos()[2] = root;
                        }
                    }
                }
                break;
            case 1:
                if(!root.getHojas()[1]){
                    aux = hasNodo(new Nodo(_x + 1, _y));
                    if (aux == null) {
                        root.getNodos()[1] = new Nodo(_x + 1, _y);
                        root.getNodos()[1].getNodos()[3] = root;
                        size++;
                    } else {
                        root.getNodos()[1] = aux;
                        if(!aux.isNodo_hoja()){
                            aux.getNodos()[3] = root;
                        }
                    }
                }
                break;
            case 2:
                if(!root.getHojas()[2]){
                    aux = hasNodo(new Nodo(_x, _y - 1));
                    if (aux == null) {
                        root.getNodos()[2] = new Nodo(_x, _y - 1);
                        root.getNodos()[2].getNodos()[0] = root;
                        size++;
                    } else {
                        root.getNodos()[2] = aux;
                        if(!aux.isNodo_hoja()){
                            aux.getNodos()[0] = root;
                        }
                    }
                }
                break;
            case 3:
                if(!root.getHojas()[3]){
                    aux = hasNodo(new Nodo(_x - 1, _y));
                    if (aux == null) {
                        root.getNodos()[3] = new Nodo(_x - 1, _y);
                        root.getNodos()[3].getNodos()[1] = root;
                        size++;
                    } else {
                        root.getNodos()[3] = aux;
                        if(!aux.isNodo_hoja()){
                            aux.getNodos()[1] = root;
                        }
                    }
                }
                break;
        }
        return true;

    }

    public Nodo hasNodo(Nodo _nodo) {
        Stack pila = new Stack();
        ArrayList visitados = new ArrayList();
        pila.push(root);
        Nodo auxiliar;
        while (!pila.isEmpty()) {
            auxiliar = (Nodo) pila.pop();
            for (int i = 0; i < 4; i++) {
                if (auxiliar.getNodos()!=null && auxiliar.getNodos()[i] != null && !visitados.contains(auxiliar)) {
                    if (auxiliar.getNodos()[i].getX() == _nodo.getX() && auxiliar.getNodos()[i].getY() == _nodo.getY()) {
                        return auxiliar.getNodos()[i];
                    } else {
                        pila.push(auxiliar.getNodos()[i]);
                    }
                }
            }
            visitados.add(auxiliar);
        }
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public Nodo getRoot() {
        return root;
    }

    public void setRoot(Nodo root) {
        this.root = root;
    }
}
