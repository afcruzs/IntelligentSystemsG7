/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.chucho;

import java.util.LinkedList;
import java.util.List;
import unalcol.agents.Action;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

/**
 *
 * @author Chucho
 */
public class MyTeseo extends SimpleTeseoAgentProgram {
    /*De esta forma conocemos hacia que dirección se mueve teseo 0 frente,
     1 derecha, 2 abajo y 3 izquierda
     */

    int direction;
    //La posición x actual
    int positionX;
    //La posición y actual
    int positionY;
    //
    TeseoMemory memory;
    List<Integer> moves;
    Node next;

    public MyTeseo() {
        direction = 0;
        positionX = 0;
        positionY = 0;
        memory = new TeseoMemory();
        moves = new LinkedList<>();

    }

    @Override
    public Action compute(Percept p) {
        if ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("exit")))) {
            return new Action(language.getAction(0));
        }
        if ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("afront")))) {
            return new Action(language.getAction(0));
        }
        if (cmd.size() == 0) {
            boolean PF = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("front"))));
            boolean PD = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("right"))));
            boolean PA = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("back"))));
            boolean PI = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("left"))));
            //d nos indica hacia q direccion avanzara en el siguiente movimiento

            int d = accion(PF, PD, PA, PI, true);
            //caculamos cuantas rotaciones debe realizar
            if (d == 0) {
                this.positionX = this.positionX + 1;
            }
            if (d == 1) {
                this.positionY = this.positionY + 1;
            }
            if (d == 2) {
                this.positionX = this.positionX - 1;
            }
            if (d == 3) {
                this.positionY = this.positionY - 1;
            }
            int rotations = ((4 - this.direction) + d) % 4;
            for (int i = 1; i <= rotations; i++) {
                cmd.add(language.getAction(3)); //rotate
                direction = (direction + 1) % 4;
            }
            cmd.add(language.getAction(2)); // advance
        }
        Node aux = memory.lastNode;
        //memory.pruning(aux);
        memory.lastNode = next;
        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }

    public void addNodes(boolean PF, boolean PD, boolean PA, boolean PI) {
        for (int i = 0; i < this.direction; i++) {
            boolean aux = PD;
            PD = PF;
            PF = PI;
            PI = PA;
            PA = aux;
        }
        if (!PF) {
            memory.addNode(this.positionX + 1, this.positionY);
        }
        if (!PD) {
            memory.addNode(this.positionX, this.positionY + 1);
        }
        if (!PA) {
            memory.addNode(this.positionX - 1, this.positionY);
        }
        if (!PI) {
            memory.addNode(this.positionX, this.positionY - 1);
        }
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        if (this.moves.isEmpty()) {
            boolean flag = false;
            if (this.positionX == 0 && this.positionY == 0) {
                memory.lastNode = new Node(0, 0);
                memory.nodes.add(memory.lastNode);
            }
            addNodes(PF, PD, PA, PI);
            //caso 2
            Arch aux = null;
            for (Arch arch : memory.lastNode.archs) {
                if (!arch.visited) {
                    aux = arch;
                    flag = true;
                    break;
                }
            }
            if (aux == null) {
                for (Arch arch : memory.lastNode.archs) {
                    if (!arch.toReturn) {
                        aux = arch;
                        break;
                    }
                }
            }
            if (memory.lastNode.equals(aux.node1)) {
                moves.addAll(aux.moves);
                next = aux.node2;
            } else {
                for (int i : aux.moves) {
                    moves.add(this.memory.inverseMove(i));
                }
                next = aux.node1;
            }
            if (flag) {
                aux.visited = true;
            }
            else{
                aux.toReturn = true;
            }
        }
        return this.moves.remove(this.moves.size() - 1);
    }
}
