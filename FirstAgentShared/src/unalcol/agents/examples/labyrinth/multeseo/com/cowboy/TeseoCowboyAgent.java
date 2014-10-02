
package unalcol.agents.examples.labyrinth.multeseo.com.cowboy;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author JuanFelipe
 */
public class TeseoCowboyAgent implements AgentProgram {
    protected SimpleLanguage language;
    protected Vector<String> cmd;
    protected Stack<StringBuilder> edge;
    private Node node;
    private StringBuilder path;
    private int posX;
    private int posY;
    Map<Integer, Map<Integer,Node> > map;
    private int dir;
    private static int movX[] = {-1,0,1,0};
    private static int movY[] = {0,1,0,-1};
    
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
    
    public TeseoCowboyAgent(SimpleLanguage language) {
        this();
        this.language = language;
    }
    
    public TeseoCowboyAgent(){
        node = null;
        cmd = new Vector();
        posX = 0;
        posY = 0;
        map = new TreeMap<Integer, Map<Integer, Node> >();
        edge = new Stack<StringBuilder>();
        init();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    @Override
    public Action compute(Percept p) {
        //interpretar lo que percibe
        if( cmd.size() == 0 ) {
            
            System.out.println("Posicion: " + posX +" "+ posY);
            
            int k, bitAux, nOpen, tam, kaux;
            Node temp;
            boolean percepts[] = new boolean[5];//PF PD PA PI MT
            
            bitAux = nOpen = 0;
            tam = percepts.length;
            for(k = 0; k < tam; k ++){
                percepts[k] = ( (Boolean) p.getAttribute(language.getPercept(k))).
                    booleanValue();
                System.out.print(" " + percepts[k]);
                if( k<tam-1 && !percepts[k] ) {
                    nOpen++;
                    kaux = getGlobalOrientation(k);
                    bitAux |= (1<<kaux);
                }
            }
            
            System.out.println("caminos disponibles: " + nOpen);
            if ( map.containsKey(posX) && map.get(posX).containsKey(posY) ) {  
                //Obtener el nodo de esta posicion
                temp = map.get(posX).get(posY);
                //Marcar camino desde donde se llego
                temp.unCheckMuro( (dir+2)%4 );
                
                System.out.println("Nodo Recuperado: " + posX +" "+ posY +" "+ temp.getMuro());
                for(k = 0; k < percepts.length - 1; k++ ) {
                    kaux = getGlobalOrientation( k );
                    if( !percepts[k] && temp.canMove(kaux) ) {
                        break;
                    }
                }
                
                if( !path.toString().equals("") ){
                    edge.add( path );
                    
                    path = new StringBuilder("");
                }
                
               if( k < 4  ) {
//                    if( !path.toString().equals("") )
//                        edge.add( path );
//                    
//                    path = new StringBuilder("");
                    kaux = getGlobalOrientation( k );
                    temp.unCheckMuro( kaux );
                    mov( k );
               } else {
                    StringBuilder sb = edge.pop();
                    if( sb.toString().equals("FIN") ){
                        cmd.add( language.getAction(0) );
                        edge.push(sb);
                    } else {
                       goBack( sb );
                       path = new StringBuilder("");
                    }
               }
                
            } else if( nOpen > 2 || node == null ) {
                
                temp = new Node(posX, posY, dir, 4);
                System.out.println("Nodo creado: " + posX +" "+ posY +" "+ bitAux);
                edge.push(path);
                path = new StringBuilder("");
                //Si hay nodo Raiz
                //relacionar el nodo con el que tenia antes
                if( node!=null ) {
                    //La ultima direccion que tomo node
                    kaux = getGlobalOrientation(dir);
                    node.addHijo( temp, node.getDir() );
                    temp.addHijo( node, kaux );
                    //Marcar desde donde llego
                    bitAux &= ~(1 << ( (dir+2)%4 ) );
                    System.out.println("ACA BITAUX"+bitAux);
                }
                
                if( !map.containsKey(posX) )
                    map.put(posX, new TreeMap<Integer, Node>());
                
                if( !map.get(posX).containsKey(posY) )
                    map.get(posX).put(posY, temp);
                
                node = temp;
                //Marcamos donde puede o no moverse
                node.setMuro( bitAux );
                
                for(k = 0; k < percepts.length - 1; k++ ){
                    if( !percepts[k] ) {
                        break;
                    }
                }
                
                
                //Este ya esta transformado
                node.unCheckMuro( getGlobalOrientation(k) );
                System.out.println("ACA");
                mov( k );

            } else { 
            
                //Verificar a donde nos podemos mover
                //Usar LB para saber q camino coger
                //bitAux &= ~(1 << ( 2 ) );
                bitAux &= ~(1 << ( (dir+2)%4 ) );
                System.out.println("***ACA"+bitAux);
                for(k = 0; k < percepts.length - 1; k++ ){
                    kaux = getGlobalOrientation(k);
                    if( !percepts[k] && (bitAux & (1<<kaux))!=0 ) {
                        break;
                    }
                }

                if( k >= 0 && k < 4 ) {    
                    mov(k);
                } else {
                    //Segundo caso donde se devuelve
                   goBack( path );
                   path = new StringBuilder("");
                }
            }
            
        }
        String x = cmd.get( 0 );
        cmd.remove( 0 );
        return new Action( x );
    }

    @Override
    public void init() {
        cmd.clear();
        path = new StringBuilder("FIN");
    }

    public SimpleLanguage getLanguage() {
        return language;
    }

    public void setLanguage(SimpleLanguage language) {
        this.language = language;
    }

    public Vector<String> getCmds() {
        return cmd;
    }

    public void setCmds(Vector<String> cmds) {
        this.cmd = cmds;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    private boolean checkIfNodeExist(int k) {
        boolean exist = false;
        //con la posicion actual del agente y la direccion candidata
        int auxX = (posX + movX[ (dir + k) % 4 ]);
        int auxY = (posY + movY[ (dir + k) % 4 ]);
        
        if(map.containsKey(auxX) && map.get(auxX).containsKey(auxY)){
            exist = true;
        }
        
        return exist;
    }
    
    public void mov( int k ){
        //Acciones q puede realizar en casilla actual
        //System.out.println("Movimiento a " + k);
        
        for (int i = 1; i <= k; i++) {
            cmd.add(language.getAction(3)); //rotate    
        }
        cmd.add( language.getAction( 2 ) ); // advance

        posX = (posX + movX[ (dir + k) % 4 ]);
        posY = (posY + movY[ (dir + k) % 4 ]);
        dir = (dir + k) % 4;
        path.append(dir);
    }
    
    private void goBack( StringBuilder sb ) {
        System.out.println("going back " + sb);
        sb = sb.reverse();
        for( char c: sb.toString().toCharArray() ){
            int aux = c - '0';
            
            aux  = (aux + 2) % 4;
            System.out.print("AUX:"+aux);
            //calcular giro respecto a direccion actual
            aux = ( aux-dir+4 ) % 4;
            
            System.out.println(", AUXF:"+aux);
            mov((aux));
        }
    }
    private int getGlobalOrientation(int dirNC) {
        return ( dir + dirNC )%4;
    }
    
}
