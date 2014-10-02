/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.singrupo;

import java.util.ArrayList;

/**
 *
 * @author Daniel Felipe
 */
public class Graph {

    private Node firstNode;
    private Node currentNode;
    private ArrayList<Node> nodos;
    private ArrayList<Edge> aristas;
    private ArrayList<Node> nodosBorrados;
    private ArrayList<Node> path;

    public Graph() {
        this.nodos = new ArrayList<Node>();
        this.aristas = new ArrayList<Edge>();
        this.path = new ArrayList<Node>();
    }

    public Graph(Node firstNode, ArrayList<Node> nodos, ArrayList<Edge> aristas) {
        this.firstNode = firstNode;
        this.nodos = nodos;
        this.aristas = aristas;
    }

    public int size() {
        return this.nodos.size();
    }

    public void agregarNodo(Node nodo) {
        if (!this.nodos.contains(nodo)) {
            this.nodos.add(nodo);
            if (size() == 1) {
                this.firstNode = nodo;
            }
        }
    }

    public void agregarArista(Edge arista) {
        if (!this.aristas.contains(arista)) {
            this.aristas.add(arista);
        }
    }

    public Node buscarNodo(Node nodo) {
        if (nodos.contains(nodo)) {
            for (Node node : nodos) {
                if (nodo.equals(node)) {
                    return node;
                }
            }
        }
        return null;
    }

    

    public Edge buscarRuta(Node a, Node b) {
        Edge buscar = new Edge(a, b);
        if(aristas.contains(buscar)){
            for (Edge arista : aristas) {
                if(arista.equals(buscar))
                    return arista;
            }
        }
        buscar = new Edge(b,a);
        if(aristas.contains(buscar)){
            for (Edge arista : aristas) {
                if(arista.equals(buscar))
                    return arista;
            }
        }
        return null;
    }

    private void borrarNodo() {
        //@TODO por implementar
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public ArrayList<Node> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Node> nodos) {
        this.nodos = nodos;
    }

    public ArrayList<Edge> getAristas() {
        return aristas;
    }

    public void setAristas(ArrayList<Edge> aristas) {
        this.aristas = aristas;
    }

    public ArrayList<Node> getNodosBorrados() {
        return nodosBorrados;
    }

    public void setNodosBorrados(ArrayList<Node> nodosBorrados) {
        this.nodosBorrados = nodosBorrados;
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }
    //</editor-fold>
}
