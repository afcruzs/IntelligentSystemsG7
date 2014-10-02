package unalcol.agents.examples.labyrinth.multeseo.com.cowboy;

import java.util.TreeMap;
import unalcol.types.collection.vector.Vector;

/**
 ** @author JuanFelipe
 */
class Node {
    public static int NORTH = 0;
    public static int EAST  = 1;
    public static int SOUTH = 2;
    public static int WEST  = 3;
    //Direccion actual del agente (a donde esta mirando el angente en ese momento)
    //Se deberia cambiar cada vez que explora un nuevo hijo, o crear una nueva variable
    private int dir;
    private int x, y        //Coordenadas relativas del nodo
                , muro      //Verifica los movs que puede hacer
                , nHijo;    //numero de hijos del nodo
    private Vector<Node> hijos;     //Hijos del nodo
    private Vector<String> caminos; //Path del nodo a sus hijos

    public Node() {
        Node aux[] = new Node[4];
        String auxS[] = new String[4];
        hijos = new Vector<Node>();
        caminos = new Vector<String>();
//        caminos = new Vector<String>();
//        hijos = new Vector<Node>( aux );
//        caminos = new Vector<String>( auxS );
        x = 0;
        y = 0;
        muro = 0;
        dir = NORTH;
    }

    public Node( int nHijo ) {
        this();
        this.nHijo = nHijo;
        for( int i = 0 ; i < nHijo ; i++ ){
            hijos.add(null);
            caminos.add("");
        }
    }
    
    /**
     * Constructor cuando se conoce el maximo numero de Hijos
     * @param x
     * @param y
     * @param dir
     * @param nHijo 
     */
    public Node(int x, int y, int dir, int nHijo) {
        this( nHijo );
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    
    public Node(int x, int y, int dir) {
        this( x, y, dir, 0 );
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getMuro() {
        return muro;
    }

    public void setMuro(int muro) {
        this.muro = muro;
    }

    public Vector<Node> getHijos() {
        return hijos;
    }

    public void setHijos( Vector<Node> hijos ) {
        this.hijos = hijos;
    }
    
    /**
     * Agrega o remplaza el nodo ubicado en la posicion dirNC
     * @param hijo
     * @param dirNC 
     */
    public void addHijo( Node nodo, int dirNC ) {
        //direccion nueva coneccion
        //dirNC = getGlobalOrientation( dirNC );
        
        if( hijos.get( dirNC ) == null )
            hijos.set(dirNC,  nodo );
    }

//    private int getGlobalOrientation(int dirNC) {
//        return ( dir + dirNC )%nHijo;
//    }
    
    /**
     * Marca el bit en la posicion indice, en un principio siempre va a ser uno
     * @param idx
     * @param val 
     */
    public void checkMuro( int idx, int val ){
        
        //System.out.println("ACA: "+idx+" "+val);
        //idx = getGlobalOrientation(idx);
        muro |= (val<<idx);
    }

    /**
     * revisa si se puede mover hacia la posicion bit
     * @param bit
     * @return 
     */
    boolean canMove(int bit ) {
        //bit = getGlobalOrientation(bit);
        int aux = (muro & (1<<bit));
        //System.out.println("***");
        return aux != 0;
    }
    
    /**
     * desmarca el bit de muro en la posicion bit
     * @param bit 
     */
    
    public void unCheckMuro( int bit ){
        //Limpiar Bit
        muro &= ~(1 << bit);
    }
    
}
