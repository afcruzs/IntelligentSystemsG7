/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.chucho;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author chucho
 */
public class TeseoMemory {

    List<ints> ints;
    List<Node> nodes;
    //El nodo en el cual estaba nuestro teseo
    Node lastNode;

    public TeseoMemory() {
        nodes = new LinkedList<>();
        ints = new LinkedList<>();
    }

    public int inverseMove(int move) {
        return (move + 2) % 4;
    }

    public void addNode(int x, int y) {
        for (ints i : this.ints) {
            if (i.x == x && i.y == y) {
                return;
            }
        }
        Node aux = null;
        for (Node node : nodes) {
            if (node.x == x && node.y == y) {
                aux = node;
                break;
            }
        }
        if (aux == null) {
            aux = new Node(x, y);
            this.nodes.add(aux);
            ints.add(new ints(x, y));
        }
        if (aux.yaEsta(this.lastNode)) {
            return;
        }
        Arch arch = new Arch();
        arch.node1 = lastNode;
        arch.node2 = aux;
        int aux2;
        if (lastNode.x - aux.x == 1) {
            aux2 = 2;
        } else if (lastNode.x - aux.x == -1) {
            aux2 = 0;
        } else if (lastNode.y - aux.y == 1) {
            aux2 = 3;
        } else {
            aux2 = 1;
        }
        arch.moves.add(aux2);
        aux.archs.add(arch);
        lastNode.archs.add(arch);
    }
    /*Este metodo se usa para podar nuestro grafo, solo hay una regla
     Si no es un nodo interesante se quita, para ser un nodo interesante 
     no se a explorado por completo*/

    public void pruning(Node node) {
        if (node.archs.size() == node.revisedArchs()) {
            for (Arch arch : node.archs) {
                for (Arch arch2 : node.archs) {
                    if (!arch.equals(arch2)) {
                        int aux = 0;
                        if (arch.node1.equals(node)) {
                            aux += 1;
                        }
                        if (arch2.node1.equals(node)) {
                            aux += 2;
                        }
                        switch (aux) {
                            case 0:
                                for (int move : arch2.moves) {
                                    move = this.inverseMove(move);
                                }
                                arch.moves.addAll(arch2.moves);
                                arch.node2 = arch2.node1;
                                arch2.node1.archs.remove(arch2);
                                arch.node2.archs.add(arch);
                                arch.visited = false;
                                break;
                            case 1:
                                arch2.moves.addAll(arch.moves);
                                arch2.node2 = arch.node2;
                                arch2.node2.archs.remove(arch);
                                arch2.node2.archs.add(arch2);
                                arch2.visited = false;
                                break;
                            case 2:
                                arch.moves.addAll(arch2.moves);
                                arch.node2 = arch2.node2;
                                arch.node2.archs.remove(arch2);
                                arch.node2.archs.add(arch);
                                arch.visited = false;
                                break;
                            case 3:
                                for (int move : arch.moves) {
                                    move = this.inverseMove(move);
                                }
                                arch.moves.addAll(arch2.moves);
                                arch.node1 = arch.node2;
                                arch.node2 = arch2.node2;
                                arch.node2.archs.remove(arch2);
                                arch.node2.archs.add(arch);
                                arch.visited = false;
                                break;
                        }
                    }
                }
            }
            for (Arch arch : node.archs) {
                if (arch.node1.equals(node)) {
                    arch.node2.archs.remove(arch);
                } else {
                    arch.node1.archs.remove(arch);
                }
            }
            nodes.remove(node);
        }
    }

    private class ints {

        int x;
        int y;

        public ints(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    @Override
    public String toString() {
        String aux = "";
        for (Node nodo : this.nodes) {
            aux += "    " + nodo;
        }
        return aux;
    }
}
