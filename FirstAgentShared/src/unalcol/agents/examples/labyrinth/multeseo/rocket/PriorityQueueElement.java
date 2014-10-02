/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rocket;

/**
 *
 * @author camilourd
 */
public class PriorityQueueElement {
    int cost;
    TournamentMapNode nd;
    
    PriorityQueueElement() { cost = 0; nd = null; }
    
    PriorityQueueElement(int c, TournamentMapNode tmn) {
        cost = c;
        nd = tmn;
    }
}