/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rocket;

import java.util.ArrayList;
import java.util.PriorityQueue;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;
import unalcol.types.collection.vector.sparse.SparseVector;

/**
 *
 * @author camilourd
 */
public class TournamentAgent implements AgentProgram {
    
    protected SimpleLanguage language;
    protected Vector<String> cmd = new Vector<String>();
    protected int dir, x, y, idx, dd;
    protected int [] dx;
    protected int [] dy;
    protected TournamentMapNode act;
    protected SparseMatrix map;
    protected int [][] dirs;
    protected int [] opp;
    protected SparseVector<Integer> cost;
    
    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;
    static final int EXIT = 4;
    
    public TournamentAgent() { init(); }
    
    public void setLanguage(  SimpleLanguage _language ){
        language = _language;
    }

    public void rotate(int d) {
        while(dir != d) {
            dir = (dir + 1) % 4;
            cmd.add(language.getAction(3)); //rotate
        }
    }

    public void advance(int stps) {
        for(int i = 0;i < stps; ++i) {
            x += dx[dir];
            y += dy[dir];
            cmd.add(language.getAction(2)); // advance
        }
    }

    public void orderPercept(Percept prcpt) {
        for(int i = 0;i < 4; ++i) {
            act.prct[dirs[dir][i]] = ((Boolean)prcpt.getAttribute(language.getPercept(i))).booleanValue();
        }
        act.prct[EXIT] = ((Boolean)prcpt.getAttribute(language.getPercept(EXIT))).booleanValue();
    }
    
    public boolean isFakeNode(TournamentMapNode nd) {
        return (nd.prct[NORTH] == nd.prct[SOUTH] && nd.prct[EAST] == nd.prct[WEST]
                && nd.prct[NORTH] != nd.prct[EAST]);
    }
    
    public int isFullyExplored(TournamentMapNode nd) {
        for(int i = 0;i < 4; ++i) {
            if(nd.prct[i] == nd.checkDir(i)) {
                return i;
            }
        }
        return -1;
    }
    
    public int bestDir() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        for(int i = 0;i < 4; ++i) {
            if(act.prct[dirs[dir][i]] == act.checkDir(dirs[dir][i])){
                //return dirs[dir][i];
                a.add(dirs[dir][i]);
            }
        }
        if(a.size() > 0) {
            int i;
            for(i = 0;i < (Math.random()*1000); ++i);
            return a.get(i % a.size());
        }
        return -1;
    }
    
    public void link(TournamentMapNode nd, int d) {
        int steps = 1;
        
        if(isFakeNode(act)) {
            TournamentMapNode s = act.getNeighbor(opp[d]);
            steps += s.getSteps(d);
            map.del(act.x, act.y);
            act = s;
        }

        act.connect(d, nd);
        act.setSteps(d, steps);

        nd.connect(opp[d], act);
        nd.setSteps(opp[d], steps);
    }
    
    @Override
    public Action compute(Percept prcpt) {
        orderPercept(prcpt);
        
        if( cmd.size() == 0 ){
            if(act.prct[EXIT]) {
                cmd.add(language.getAction(1)); // die
            } else {
                int nxtd = bestDir();

                if(nxtd >= 0) {
                    // Explorar el camino
                    rotate(nxtd);
                    advance(1);
                    TournamentMapNode nxtnd = new TournamentMapNode(x, y);
                    nxtnd.setIdx(++idx);

                    for(int i = 0;i < 4; ++i) {
                        act = map.get(x + dx[i], y + dy[i]);
                        if(act != null && act.prct[opp[i]] == false) {
                            link(nxtnd, opp[i]);
                        }
                    }
                    
                    map.set(x, y, nxtnd);
                    act = nxtnd;
                } else {
                    // Buscar el nodo más cercano que le falte por explorar algún camino
                    TournamentMapNode t = null, s = null;
                    act.parent = -1;
                    cost.set(act.idx, 0);
                    PriorityQueueElement elm = new PriorityQueueElement(0, act);
                    PriorityQueueElementComparator comparator = new PriorityQueueElementComparator();
                    
                    PriorityQueue<PriorityQueueElement> Q = new PriorityQueue<PriorityQueueElement>(10, comparator);
                    Q.add(elm);
                    int sum = 0;
                    
                    while((elm = Q.poll()) != null) {
                      if(isFullyExplored(elm.nd) >= 0) {
                          if(t == null || cost.get(elm.nd.idx) < cost.get(t.idx)) {
                              t = elm.nd;
                          }
                      }
                      
                      if(elm.cost <= cost.get(elm.nd.idx)) {
                          for(int i = 0;i < 4; ++i) {
                              if(elm.nd.checkDir(i)) {
                                  sum = cost.get(elm.nd.idx) + elm.nd.getSteps(i);
                                  s = elm.nd.getNeighbor(i);
                                  
                                  if(s.parent >= 0) {
                                      if(i == s.parent) {
                                          sum += 2;
                                      } else if(i == dirs[s.parent][1]) {
                                          sum += 3;
                                      } else if(i == dirs[s.parent][3]) {
                                          sum += 1;
                                      }
                                  }
                                  
                                  try {
                                    if(sum < cost.get(s.idx)) {
                                        cost.set(s.idx, sum);
                                        s.parent = opp[i];
                                        Q.add(new PriorityQueueElement(cost.get(s.idx), s));
                                    }
                                  } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                                      cost.set(s.idx, sum);
                                      s.parent = opp[i];
                                      Q.add(new PriorityQueueElement(cost.get(s.idx), s));
                                  }
                              }
                          }
                      }
                    }
                    
                    cost.clear();
                    ArrayList<Integer> path = new ArrayList<Integer>();
                    while(t.parent >= 0) {
                        path.add(0, opp[t.parent]);
                        t = t.getNeighbor(t.parent);
                    }
                    
                    for(int i = 0;i < path.size();++i) {
                        rotate(path.get(i));
                        advance(act.getSteps(path.get(i)));
                        act = act.getNeighbor(path.get(i));
                    }
                }
            }
        }
                
        // Esquivar agentes
        /*if(cmd.get(0).equals(language.getAction(2)) && act.prct[dd] == true) {
            return new Action(language.getAction(0));
        }*/
        String x = cmd.get(0);
        if(x.equals(language.getAction(3))) dd = (dd + 1) % 4;
        cmd.remove(0);
        return new Action(x);
    }

    @Override
    public void init() {
        cmd.clear();
        dir = x = y = 0;
        dx = new int[4];
        dy = new int[4];
        dirs = new int[4][4];
        act = new TournamentMapNode();
        map = new SparseMatrix();
        opp = new int[4]; // lado opuesto
        cost = new SparseVector<Integer>();
        dd = 0;
        idx = 0;
        map.set(x, y, act);
        
        dx[NORTH] = 0; dx[EAST] = 1; dx[SOUTH] = 0; dx[WEST] = -1;
        dy[NORTH] = 1; dy[EAST] = 0; dy[SOUTH] = -1; dy[WEST] = 0;
        
        for(int i = 0;i < 4; ++i) {
            opp[i] = (i + 2) % 4;
            for(int j = 0;j < 4; ++j) {
                dirs[i][j] = (i + j) % 4;
            }
        }
    }
    
}