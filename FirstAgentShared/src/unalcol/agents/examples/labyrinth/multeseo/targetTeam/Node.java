/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.targetTeam;

/**
 *
 * @author windows
 */
public class Node {

    private int x;
    private int y;
    private Object data;
    private Node down, next, up, prev;

    public Node(int n_x, int n_y, Object data) {
        this.x = n_x;
        this.y = n_y;
        this.data = data;
        down = this;
        next = this;
        up = this;
        prev = this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getDown() {
        return this.down;
    }

    public Node getNext() {
        return this.next;
    }

    public int getCol() {
        return this.y;
    }

    public int getRow() {
        return this.x;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
    
}
