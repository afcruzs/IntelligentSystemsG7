package unalcol.agents.examples.labyrinth.multeseo.cai;

public class Node {

    
    private Node right;
    private Node left;
    private Node front;
    private Node parent;
    
    private int coordX;
    private int coordY;
    private int dir;            //Un indicador que muesta que direccion se tomo en este nodo

    /*
     * Constructor que inicializa el grafo
     * 
     * @param PF si tiene pared al frente
     */
     public Node(Node actual, int direction, boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {       

        if (actual == null) {
            parent=null;
            coordX = 0;
            coordY = 0;
            //referencia circular provisional para permitir el cálculo de las coordenadas para el primer nodo
            actual=this;
        }
        else{
            this.parent = actual.getParent();
            coordX=actual.getCoord_X();
            coordY=actual.getCoord_Y();            
        }
        if (PF) {
            front = null;
        } else {
            front = new Node(this);
            switch (direction) {
                case 0:
                    front.setCoord_Y(actual.getCoord_Y() + 1);
                    front.setCoord_X(actual.getCoord_X());
                    break;
                case 1:
                    front.setCoord_Y(actual.getCoord_Y());
                    front.setCoord_X(actual.getCoord_X() + 1);
                    break;
                case 2:
                    front.setCoord_Y(actual.getCoord_Y() - 1);
                    front.setCoord_X(actual.getCoord_X());
                    break;
                case 3:
                    front.setCoord_Y(actual.getCoord_Y());
                    front.setCoord_X(actual.getCoord_X() - 1);
                    break;
            }

        }
        if (PD) {
            right = null;
        } else {
            right = new Node(this);
            switch (direction) {
                case 0:
                    right.setCoord_Y(actual.getCoord_Y());
                    right.setCoord_X(actual.getCoord_X() + 1);
                    break;
                case 1:
                    right.setCoord_Y(actual.getCoord_Y() - 1);
                    right.setCoord_X(actual.getCoord_X());
                    break;
                case 2:
                    right.setCoord_Y(actual.getCoord_Y());
                    right.setCoord_X(actual.getCoord_X() - 1);
                    break;
                case 3:
                    right.setCoord_Y(actual.getCoord_Y() + 1);
                    right.setCoord_X(actual.getCoord_X());
                    break;
            }
        }

        if (PI) {
            left = null;
        } else {
            left = new Node(this);
            switch (direction) {
                case 0:
                    left.setCoord_Y(actual.getCoord_Y());
                    left.setCoord_X(actual.getCoord_X() - 1);
                    break;
                case 1:
                    left.setCoord_Y(actual.getCoord_Y() + 1);
                    left.setCoord_X(actual.getCoord_X());
                    break;
                case 2:
                    left.setCoord_Y(actual.getCoord_Y());
                    left.setCoord_X(actual.getCoord_X() + 1);
                    break;
                case 3:
                    left.setCoord_Y(actual.getCoord_Y() - 1);
                    left.setCoord_X(actual.getCoord_X());
                    break;
            }
        }
    }

    public Node(Node parent) {
        this.parent = parent;

    }
    /*
     * Busca la existencia de un bucle desde la casilla actual
     * hasta un estado anterior, podando la rama del árbol que
     * implica un bucle.
     * 
     * @param node el nodo donde se empieza la búsqueda del bucle
     * @return el nodo padre desde donde empezaba el bucle eliminando
     * la rama hija que corresponde al bucle; null si no existe bucle
     */
    public Node findLoop(Node node) {

        Node _parent = node.getParent();
        while (_parent != null) {

            if (node.getCoord_X() == _parent.getCoord_X()
                    && node.getCoord_Y() == _parent.getCoord_Y()) {

                //poda de rama que contiene el bucle                
                
                if(node.getParent().getFront()!=null){
                    int frontX=node.getParent().getFront().getCoord_X();
                    int frontY=node.getParent().getFront().getCoord_Y();
                    if(frontX==_parent.getCoord_X()&&frontY==_parent.getCoord_Y()){
                        Node aux=node.getParent();
                        aux.setFront(null);
                        return aux;
                    }
                }                
                 if(node.getParent().getRight()!=null){
                    int rightX=node.getParent().getRight().getCoord_X();
                    int rightY=node.getParent().getRight().getCoord_Y();
                    if(rightX==_parent.getCoord_X()&&rightY==_parent.getCoord_Y()){
                        Node aux=node.getParent();
                        aux.setRight(null);
                        return aux;
                    }
                }                 
                 if(node.getParent().getLeft()!=null){
                    int leftX=node.getParent().getLeft().getCoord_X();
                    int leftY=node.getParent().getLeft().getCoord_Y();
                    if(leftX==_parent.getCoord_X()&&leftY==_parent.getCoord_Y()){
                        Node aux=node.getParent();
                        aux.setLeft(null);
                        return aux;
                    }
                }                
            }
            _parent = _parent.getParent();
        }
        return null;
    }

    public int getCoord_X() {
        return coordX;
    }

    public void setCoord_X(int coord_X) {
        this.coordX = coord_X;
    }

    public int getCoord_Y() {
        return coordY;
    }

    public void setCoord_Y(int coord_Y) {
        this.coordY = coord_Y;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getFront() {
        return front;
    }

    public void setFront(Node front) {
        this.front = front;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public int getDir(){
	return this.dir;
    }
    
    public void marcarDir(int dir){
	this.dir = dir;
    }
}