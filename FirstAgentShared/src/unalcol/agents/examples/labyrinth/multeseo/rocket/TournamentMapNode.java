/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rocket;

/**
 *
 * @author camilourd
 */
public class TournamentMapNode {
    protected int x, y, parent, idx;
    protected TournamentMapNode [] neighbor;
    protected int [] steps;
    public boolean prct[];
    
    public TournamentMapNode () { x = y = idx = 0; neighbor = new TournamentMapNode[4]; steps = new int[4]; prct = new boolean[5]; }
    
    public TournamentMapNode(int xs, int ys) {
        x = xs; y = ys;
        neighbor = new TournamentMapNode[4];
        steps = new int[4];
        prct = new boolean[5];
    }
        
    public boolean checkDir(int d) {
        return neighbor[d] != null;
    }
    
    public TournamentMapNode getNode(int d) {
        return neighbor[d];
    }
    
    public TournamentMapNode getNeighbor(int d) {
        if(d >= 0 && d < 4) {
            return neighbor[d];
        }
        return null;
    }
    
    public boolean isFull() {
        return (checkDir(0) && checkDir(1) && checkDir(2) && checkDir(3));
    }
    
    public void connect(int d, TournamentMapNode nd) {
        neighbor[d] = nd;
    }
    
    public void setSteps(int d, int stps) {
        steps[d] = stps;
    }
    
    public int getSteps(int d) {
        return steps[d];
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
    
    
}