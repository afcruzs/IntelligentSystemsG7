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
public class Arch {

    //Aqui se va a guardar como llegar desde el node1 hasta el node2
    List<Integer> moves;
    Node node1;
    Node node2;
    //De este modo conoceremos si ya pasamos de node1 a node2 o de node2 a node1
    boolean visited;
    boolean toReturn;

    public Arch() {
        moves = new LinkedList();
        visited = false;
        toReturn = false;
    }

}
