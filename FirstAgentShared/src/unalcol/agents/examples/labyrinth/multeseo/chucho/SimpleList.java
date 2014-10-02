/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.chucho;

/**
 *
 * @author Chucho
 */
public class SimpleList {

    private boolean list[];
    private int head;

    public SimpleList() {
        this.list = new boolean[4];
        this.head = 0;
    }

    public void updateUp(boolean value) {
        this.list[this.head] = value;
    }

    public void updateRight(boolean value) {
        this.list[(this.head + 1) % 4] = value;
    }

    public void updateDown(boolean value) {
        this.list[(this.head + 2) % 4] = value;
    }

    public void updateLeft(boolean value) {
        this.list[(this.head + 3) % 4] = value;
    }

    public boolean getUp() {
        return this.list[this.head];
    }

    public boolean getRight() {
        return this.list[(this.head + 1) % 4];
    }

    public boolean getDown() {
        return this.list[(this.head + 2) % 4];
    }

    public boolean getLeft() {
        return this.list[(this.head + 3) % 4];
    }

    public void leftShift() {
        if (this.head == 0) {
            this.head = 3;
        } else {
            this.head = this.head - 1;
        }
    }

    public void rightShift() {
        this.head = (this.head + 1) % 4;
    }
}
