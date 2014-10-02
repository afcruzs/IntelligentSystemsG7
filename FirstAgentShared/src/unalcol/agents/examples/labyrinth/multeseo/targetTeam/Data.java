/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.targetTeam;

/**
 *
 * @author Camilo
 */
public class Data {
    
    public static final int OPEN = 0;
    public static final int VISITED = 1;
    public static final int CLOSED = 2;
    public static final int AGENT = 3;
    
    private int[] paths = {OPEN,OPEN,OPEN,OPEN};
    private String key;
    
    public Data(){
        
    }
    
    public Data(int[] p){
        for(int i=0; i<p.length;i++){
            paths[i]=p[i];
        }
    }
    
    public int getPaths(int path) {
        return paths[path];
    }

    public String getKey() {
        return key;
    }
    
    public void changePaths(int path, int state){
        paths[path] = state;
    }

    public int[] getPaths() {
        return paths;
    }

    public void setPaths(int[] paths) {
        this.paths = paths;
    }
    
}
