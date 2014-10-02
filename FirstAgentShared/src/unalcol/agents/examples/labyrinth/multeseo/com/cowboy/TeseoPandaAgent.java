package unalcol.agents.examples.labyrinth.multeseo.com.cowboy;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author JuanFelipe
 */
public class TeseoPandaAgent extends SimpleTeseoAgentProgram {

    protected Stack<StringBuilder> edge;
    private Node node;
    private StringBuilder path;
    private int posX;
    private int posY;
    Map<Integer, Map<Integer, Node> > map;
    private int dir;
    private static int movX[] = {-1, 0, 1, 0};
    private static int movY[] = {0, 1, 0, -1};
    private String aux;
    private int index;
    private int estado;
    
    public TeseoPandaAgent(SimpleLanguage language) {
        this();
        this.language = language;
    }

    public TeseoPandaAgent() {
        node = null;
        cmd = new Vector();
        posX = 0;
        posY = 0;
        map = new TreeMap<Integer, Map<Integer, Node>>();
        edge = new Stack<StringBuilder>();
        estado = 0;
        index = 0;
        
        init();
    }

    public void init() {
        cmd.clear();
        path = new StringBuilder("FIN");
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        
        if(estado == 1){
            return goBack();
        }

        System.out.println("Posicion: " + posX + " " + posY);

        int k, bitAux, nOpen, tam, kaux;
        Node temp;
        boolean percepts[] = {PF, PD, PA, PI, MT};

        bitAux = nOpen = 0;
        tam = percepts.length;
        if(percepts[4] == true){
            System.out.println("Termineee :3!!!!");
            return 10;
        }
        for (k = 0; k < tam; k++) {
            if (k < tam - 1 && !percepts[k]) {
                nOpen++;
                kaux = getGlobalOrientation(k);
                bitAux |= (1 << kaux);
                
            }
        }

        System.out.println("caminos disponibles: " + nOpen);
        if (map.containsKey(posX) && map.get(posX).containsKey(posY)) {
            //Obtener el nodo de esta posicion
            temp = map.get(posX).get(posY);
            //Marcar camino desde donde se llego
            temp.unCheckMuro((dir + 2) % 4);

            System.out.println("Nodo Recuperado: " + posX + " " + posY + " " + temp.getMuro());
            for (k = 0; k < percepts.length - 1; k++) {
                kaux = getGlobalOrientation(k);
                if (!percepts[k] && temp.canMove(kaux)) {
                    break;
                }
            }

            if (!path.toString().equals("")) {
                edge.add(path);

                path = new StringBuilder("");
            }

            if (k < 4) {
//                    if( !path.toString().equals("") )
//                        edge.add( path );
//                    
//                    path = new StringBuilder("");
                kaux = getGlobalOrientation(k);
                temp.unCheckMuro(kaux);
                posX = (posX + movX[ (dir + k) % 4]);
                posY = (posY + movY[ (dir + k) % 4]);
                dir = (dir + k) % 4;
                path.append(dir);
                return k;
                

            } else {
                StringBuilder sb = edge.pop();
                if (sb.toString().equals("FIN")) {
                    //cmd.add(language.getAction(0));
                    edge.push(sb);
                    return 10;
                } else {
                    estado = 1;
                    index = 0;
                    aux = (sb.reverse().toString());
                    path = new StringBuilder("");
                    return goBack();
                }
            }

        } else if (nOpen > 2 || node == null) {

            temp = new Node(posX, posY, dir, 4);
            System.out.println("Nodo creado: " + posX + " " + posY + " " + bitAux);
            edge.push(path);
            path = new StringBuilder("");
            //Si hay nodo Raiz
            //relacionar el nodo con el que tenia antes
            if (node != null) {
                //La ultima direccion que tomo node
                kaux = getGlobalOrientation(dir);
                node.addHijo(temp, node.getDir());
                temp.addHijo(node, kaux);
                //Marcar desde donde llego
                bitAux &= ~(1 << ((dir + 2) % 4));
                System.out.println("ACA BITAUX" + bitAux);
            }

            if (!map.containsKey(posX)) {
                map.put(posX, new TreeMap<Integer, Node>());
            }

            if (!map.get(posX).containsKey(posY)) {
                map.get(posX).put(posY, temp);
            }

            node = temp;
            //Marcamos donde puede o no moverse
            node.setMuro(bitAux);

            for (k = 0; k < percepts.length - 1; k++) {
                if (!percepts[k]) {
                    break;
                }
            }

            //Este ya esta transformado
            node.unCheckMuro(getGlobalOrientation(k));
            System.out.println("ACA");
//            mov(k);
            posX = (posX + movX[ (dir + k) % 4]);
            posY = (posY + movY[ (dir + k) % 4]);
            dir = (dir + k) % 4;
            path.append(dir);
            return k;

        } else {

            //Verificar a donde nos podemos mover
            //Usar LB para saber q camino coger
            //bitAux &= ~(1 << ( 2 ) );
            bitAux &= ~(1 << ((dir + 2) % 4));
            System.out.println("***ACA" + bitAux);
            for (k = 0; k < percepts.length - 1; k++) {
                kaux = getGlobalOrientation(k);
                if (!percepts[k] && (bitAux & (1 << kaux)) != 0) {
                    break;
                }
            }

            if (k >= 0 && k < 4) {
                posX = (posX + movX[ (dir + k) % 4 ]);
                posY = (posY + movY[ (dir + k) % 4 ]);
                dir = (dir + k) % 4;
                path.append(dir);
                return k;
            } else {
                //Segundo caso donde se devuelve
                index = 0;
                estado = 1;
                aux = path.reverse().toString();
                path = new StringBuilder("");
                
                return goBack();
            }
        }

    }

    public Stack<StringBuilder> getEdge() {
        return edge;
    }

    public void setEdge(Stack<StringBuilder> edge) {
        this.edge = edge;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public StringBuilder getPath() {
        return path;
    }

    public void setPath(StringBuilder path) {
        this.path = path;
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

    public Map<Integer, Map<Integer, Node>> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Map<Integer, Node>> map) {
        this.map = map;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    private int getGlobalOrientation(int dirNC) {
        return (dir + dirNC) % 4;
    }
    
    private int  goBack( ) {
        
        
            int aux2 = aux.charAt(index) - '0';
            
            aux2  = (aux2 + 2) % 4;
            System.out.print("AUX:"+aux);
            //calcular giro respecto a direccion actual
            aux2 = ( aux2 - dir + 4 ) % 4;
            index++;
            
            if(index == aux.length())
                estado = 0;
            
            posX = (posX + movX[ (dir + aux2) % 4 ]);
            posY = (posY + movY[ (dir + aux2) % 4 ]);
            dir = (dir + aux2) % 4;
            
            System.out.println(", AUXF:"+aux);
            return aux2;
        
    }
}
