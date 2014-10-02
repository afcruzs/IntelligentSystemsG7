package unalcol.agents.examples.labyrinth.multeseo.targetTeam;

import java.util.HashMap;

public class SparseMatrix {

    Node start;
    HashMap<String, String> listNodes;

    public SparseMatrix(Data data) {
        start = new Node(0, 0, data);
        listNodes = new HashMap<>();
        listNodes.put("0,0", "0,0");        
	Node mover = start, newNode;
        for(int i = 1; i < 10; i ++) {
                newNode = new Node(0, i, null);
                newNode.setNext(start);
                mover.setNext(newNode);
                mover = mover.getNext();
        }

        mover = start;
        for(int i = 1; i < 10; i ++) {
                newNode = new Node(i, 0, null);
                newNode.setDown(start);
                mover.setDown(newNode);
                mover = mover.getDown();
        }
        mover = start;
        for(int i = 1; i < 10; i ++) {
                newNode = new Node(i, 0, null);
                newNode.setUp(start);
                mover.setUp(newNode);
                mover = mover.getUp();
        }
        mover = start;
        for(int i = 1; i < 10; i ++) {
                newNode = new Node(i, 0, null);
                newNode.setPrev(start);
                mover.setPrev(newNode);
                mover = mover.getPrev();
        }
    }

    public Data retriveData(int i, int j) {
        Node mover = start;
        if(j<0){
            while (mover.getCol() != j) {
                mover = mover.getPrev();
            }
        }else{
            while (mover.getCol() != j) {
                mover = mover.getNext();
            }
        }
        if(i<0){
            do {
                mover = mover.getUp();
            } while (mover.getRow() != i && mover.getRow() != 0);
        }else{
             do {
                mover = mover.getDown();
            } while (mover.getRow() != i && mover.getRow() != 0);
        }

        if (mover.getRow() == i && mover.getCol() == j) {
            return (Data) mover.getData();
        }
        return null;
    }
    
    public Node retriveNode(int i, int j) {
        Node mover = start;
        if(j<0){
            while (mover.getCol() != j) {
                mover = mover.getPrev();
            }
        }else{
            while (mover.getCol() != j) {
                mover = mover.getNext();
            }
        }
        if(i<0){
            do {
                mover = mover.getUp();
            } while (mover.getRow() != i && mover.getRow() != 0);
        }else{
             do {
                mover = mover.getDown();
            } while (mover.getRow() != i && mover.getRow() != 0);
        }

        if (mover.getRow() == i && mover.getCol() == j) {
            return  mover;
        }
        return null;
    }

    public boolean add(int i, int j, Data data) {
        Node mover, newNode;
        mover = start;
        if(i<0){
            while (mover.getRow() != i && mover.getUp() != mover) {
                mover = mover.getUp();
            }
        }else{
            while (mover.getRow() != i && mover.getDown() != mover) {
                mover = mover.getDown();
            }
        }
        if(j<0){
            while (mover.getPrev().getCol() > j && mover.getPrev() != mover) {
                mover = mover.getPrev();
            }
        }else{
            while (mover.getNext().getCol() < j && mover.getNext()!= mover) {
                mover = mover.getNext();
            }
        }

        if (mover.getCol() == j && mover.getRow() == i) {
            mover.setData(data);
            return true;
        }
        if (j<0 && mover.getPrev().getCol() == j && mover.getPrev().getRow()==i) {
            mover.getPrev().setData(data);
            return true;
        }else if (j>=0 && mover.getNext().getCol() == j && mover.getNext().getRow()==i) {
            mover.getNext().setData(data);
            return true;
        }
        
        newNode = new Node(i, j, data);
        listNodes.put(i+","+j, i+","+j);
        if(j<0){            
            if(mover.getPrev() != mover){
                newNode.setPrev(mover.getPrev());
            }
            mover.setPrev(newNode);
        }else{            
            if(mover.getNext() != mover){
                newNode.setNext(mover.getNext());
            }
            mover.setNext(newNode);
        }

        mover = start;
        if(j<0){ 
            while (mover.getCol() != j) {
                mover = mover.getPrev();
            }
        }else{
            while (mover.getCol() != j) {
                mover = mover.getNext();
            }
        }
        if(i<0){ 
            while (mover.getUp().getRow() > i && mover.getUp() != mover) {
                mover = mover.getUp();
            }
            if(mover.getUp() != mover){
                newNode.setUp(mover.getUp());
            }            
            mover.setUp(newNode);
        }else{
            while (mover.getDown().getRow() < i && mover.getDown() != mover) {
                mover = mover.getDown();
            }
            if(mover.getDown()!=mover){
                newNode.setDown(mover.getDown());
            }
            mover.setDown(newNode);
        }
        return true;
    }

    public Data remove(int i, int j) {
        if (retriveData(i, j) == null) {
            return null;
        }
        Node mover = start;
        if (i == 0 || j == 0) {
            Data temp = retriveData(i, j);
            add(i, j, null);
            listNodes.remove(i+","+j);
            return temp;
        }
        Node temp;
        if(i<0){
            while (mover.getRow() != i) {
                mover = mover.getUp();
            }
        }else{
            while (mover.getRow() != i) {
                mover = mover.getDown();
            }
        }
        if(j<0){
            while (mover.getPrev().getCol() != j) {
                mover = mover.getPrev();
            }
            temp = mover.getPrev();
        }else{
            while (mover.getNext().getCol() != j) {
                mover = mover.getNext();
            }
            temp = mover.getNext();            
        }
        mover = start;
        if(j<0){
            while (mover.getCol() != j) {
                mover = mover.getPrev();
            }
        }else{
            while (mover.getCol() != j) {
                mover = mover.getNext();
            }
        }
        if(i<0){
            while (mover.getUp().getRow() != i) {
                mover = mover.getUp();
            }
            mover.setUp(temp.getUp());
        }else{
            while (mover.getDown().getRow() != i) {
                mover = mover.getDown();
            }
            mover.setDown(temp.getDown());
        }
        listNodes.remove(i+","+j);
        return (Data) temp.getData();
    }

    public Node getStart() {
        return start;
    }

    public HashMap<String, String> getListNodes() {
        return listNodes;
    }

    public void setListNodes(HashMap<String, String> listNodes) {
        this.listNodes = listNodes;
    }
       
}
