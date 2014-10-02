/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.rocket;

import unalcol.types.collection.vector.sparse.SparseVector;

/**
 *
 * @author camilourd
 */
public class SparseMatrix {
    SparseVector<SparseVector<TournamentMapNode>> matrix;
    
    SparseMatrix() {
        matrix = new SparseVector<SparseVector<TournamentMapNode>>();
    }
    
    public boolean set(int xs, int ys, TournamentMapNode data) {
        try {
            return matrix.get(xs).set(ys, data);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            matrix.set(xs, new SparseVector<TournamentMapNode>());
            return matrix.get(xs).set(ys, data);
        }
    }
    
    public boolean del(int xs, int ys) {
        try {
            return matrix.get(xs).del(ys);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }
        
    public TournamentMapNode get(int xs, int ys) {
        try {
            return matrix.get(xs).get(ys);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}