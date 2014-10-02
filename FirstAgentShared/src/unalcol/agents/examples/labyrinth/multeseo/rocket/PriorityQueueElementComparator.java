/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rocket;

import java.util.Comparator;

/**
 *
 * @author camilourd
 */
public class PriorityQueueElementComparator implements Comparator<PriorityQueueElement> {

    @Override
    public int compare(PriorityQueueElement a, PriorityQueueElement b) {
        if(a.cost < b.cost) {
            return -1;
        }
        
        if(a.cost > b.cost) {
            return 1;
        }
        return 0;
    }
    
}
