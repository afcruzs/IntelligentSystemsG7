/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.multeseo.sinergia;

import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.list.Stack;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Miguel A. Rodriguez
 */
public class LabyrinthAgentProgram implements AgentProgram {
    
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    public int dir = NORTH;
    protected SimpleLanguage language;
    protected Vector<String> cmd = new Vector<String>();
    protected Stack<String> caminoSolucion = new Stack<String>();
    protected ArrayList<BoxNode> sol;            
    protected BoxNode actual=new BoxNode(0,0);   
    protected static int x = 0; // coordenada x
    protected static int y = 0; // coordenada y
    protected Stack<Integer> stack = new Stack(); 
    protected BoxNode elPadre = new BoxNode(0,0); // lugar de inicio del laberinto
    protected BoxNode padre = new BoxNode(0,0); // padre del actual nodo   
    protected int direcciones = 0;
    
    public LabyrinthAgentProgram(SimpleLanguage _language) {
        this.sol = new ArrayList();
        language = _language;
        sol.add(actual);
    }    
    
    @Override
    public Action compute(Percept p) {
        
        int per=0;
        int nod=0;
        for(int i=0;i<4;i++){
            if(!((Boolean) p.getAttribute(language.getPercept(i))).booleanValue());
                per++;
            if(actual.neighbors[i]!=null)
                nod++;
        }
        if(per==nod)
            actual.bloqueado=true;
                
        
        //    actual.isBifurcacion=true;
        if (cmd.size() == 0) {
            if (goalAchieved(p)) {
                return new Action(language.getAction(0)); // die
            }
            
            direcciones = (direcciones + 1) %4;//lo usaremos para almacenar la direccion
            boolean bandera = true;
            int i = 0;
            do {
                switch (direcciones) {
                    case 0: //Adelante
                        if ((!((Boolean) p.getAttribute(language.getPercept(0))).booleanValue()/*||
                                !((Boolean) p.getAttribute(language.getPercept(5))).booleanValue()*/)&& 
                                (actual.neighbors[(direcciones + dir) % 4] == null || i > 3)) {
                            BoxNode f=new BoxNode(nextX(direcciones), nextY(direcciones));
                            
                            if (actual.neighbors[(direcciones + dir) % 4]==null) {
                                
                                boolean d=false;
                                for(BoxNode t:sol){
                                    if(t.equals(f)){
                                            actual.neighbors[(direcciones + dir) % 4]= t;
                                            d=true;
                                    }
                                }
                                if (!d) {
                                     addNode(direcciones);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   addNode(direcciones);
                                }
                                cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(2));
                            bandera = false; //para que salga 
                            } else {
                                actual = actual.neighbors[(direcciones + dir) % 4];
                            }
                        } else {
                            direcciones++;
                        }
                        break;
                    case 1: //Derecha
                        if ((!((Boolean) p.getAttribute(language.getPercept(1))).booleanValue()/*||
                                !((Boolean) p.getAttribute(language.getPercept(6))).booleanValue()*/)&& 
                                (actual.neighbors[(direcciones + dir) % 4] == null || i > 3)) {
                            BoxNode f=new BoxNode(nextX(direcciones), nextY(direcciones));
                            if (actual.neighbors[(direcciones + dir) % 4]==null) {
                                
//datos.nodoActual.nodosHijos.contains(f)
                                boolean d=false;
                                for(BoxNode t:sol){
                                    if(t.equals(f)){
                                            actual.neighbors[(direcciones + dir) % 4]= t;
                                            d=true;
                                    }
                                }
                                if (!d) {
                                     addNode(direcciones);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   addNode(direcciones);
                                }
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                          
                            bandera = false; //para que salga
                            
                            } else {
                                actual = actual.neighbors[(direcciones + dir) % 4];
                                
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                          
                            bandera = false; //para que salga
                            }
                            dir=(dir + 1) % 4;
                        } else {
                            direcciones++;
                        }
                        break;
                    case 2: //Atras
                        if ((!((Boolean) p.getAttribute(language.getPercept(2))).booleanValue()/*||
                                !((Boolean) p.getAttribute(language.getPercept(7))).booleanValue()*/)&& 
                                (actual.neighbors[(direcciones + dir) % 4] == null || i > 3)) {
                            BoxNode f=new BoxNode(nextX(direcciones), nextY(direcciones));
                            
                            if (actual.neighbors[(direcciones + dir) % 4]==null) {
                                
//datos.nodoActual.nodosHijos.contains(f)
                                boolean d=false;
                                for(BoxNode t:sol){
                                    if(t.equals(f)){
                                            actual.neighbors[(direcciones + dir) % 4]= t;
                                            d=true;
                                    }
                                }
                                if (!d) {
                                     addNode(direcciones);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   addNode(direcciones);
                                }
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                            bandera = false; //para que salga
                           
                            } else {
                                actual = actual.neighbors[(direcciones + dir) % 4];
                                
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                            bandera = false; //para que salga
                           
                            }
                            dir=(dir + 2) % 4;

                        } else {
                            direcciones++;
                        }
                        break;
                    case 3: //Izquierda
                        if ((!((Boolean) p.getAttribute(language.getPercept(3))).booleanValue()/*||
                                !((Boolean) p.getAttribute(language.getPercept(8))).booleanValue()*/)&& 
                                (actual.neighbors[(direcciones + dir) % 4] == null || i > 3)) {
                             BoxNode f=new BoxNode(nextX(direcciones), nextY(direcciones));
                            
                            if (actual.neighbors[(direcciones + dir) % 4]==null) {
//datos.nodoActual.nodosHijos.contains(f)
                                boolean d=false;
                                for(BoxNode t:sol){
                                    if(t.equals(f)){
                                            actual.neighbors[(direcciones + dir) % 4]= t;
                                            d=true;
                                    }
                                }
                                if (!d) {
                                     addNode(direcciones);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   addNode(direcciones);
                                }
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                           
                            bandera = false; //para que salga
                            } else {
                                actual = actual.neighbors[(direcciones + dir) % 4];
                                cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(3)); // rotate
                            cmd.add(language.getAction(2)); // advance
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(3));
                            caminoSolucion.add(language.getAction(2));
                           
                            bandera = false; //para que salga
                            }
                            dir=(dir + 3) % 4;
                            
                        } else {
                            direcciones=0;
                        }
                        break;
                }
                
                i++;
            } while (bandera && i < 8);
        }
        String x = (String) cmd.get(0);
        System.out.println(x);
        if(!cmd.isEmpty())
            cmd.remove(0);

        return new Action(x);
    }

    @Override
    public void init() {
        //cmd.clear();
    }
    
    public boolean goalAchieved(Percept p) {
        return (((Boolean) p.getAttribute(language.getPercept(4))).booleanValue());
    }
    
    public void setPerception(Percept p) {
        boolean temp = false;
        switch (dir) {
            case (EAST):
                for (int i = 0; i < 3; i++) {
                    temp = ((Boolean) p.getAttribute(language.getPercept((i + 1) % 4))).booleanValue();
                    p.setAttribute(language.getPercept((i + 1) % 4), language.getPercept(i));
                    p.setAttribute(language.getPercept(i), temp);
                }
                p.setAttribute(language.getPercept(3), temp);
                break;
            case (SOUTH):
                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < 3; i++) {
                        temp = ((Boolean) p.getAttribute(language.getPercept((i + 1) % 4))).booleanValue();
                        p.setAttribute(language.getPercept((i + 1) % 4), language.getPercept(i));
                        p.setAttribute(language.getPercept(i), temp);
                    }
                    p.setAttribute(language.getPercept(3), temp);
                }
                break;
            case (WEST):
                for (int i = 3; i <= 0; i++) {
                    temp = ((Boolean) p.getAttribute(language.getPercept((i - 1) % 4))).booleanValue();
                    p.setAttribute(language.getPercept((i - 1) % 4), language.getPercept(i));
                    p.setAttribute(language.getPercept(i), temp);
                }
                p.setAttribute(language.getPercept(3), temp);
                break;
        }
    }
    
    public boolean isBifurcacion(Percept p){
        int per=0;
        for(int i=0;i<4;i++){
            if(!((Boolean) p.getAttribute(language.getPercept(i))).booleanValue());
                per++;
        }
        if(per>2)
            return true;
        else
            return false;
    }

    public void addNode(int d) {

        switch (dir) {
            case (NORTH):
                joinNode(d, (d + 2) % 4, d);
                break;
            case (EAST):
                joinNode((d + 1) % 4, (d + 3) % 4, d);
                break;
            case (SOUTH):
                joinNode((d + 2) % 4, (d + 4) % 4, d);
                break;
            case (WEST):
                joinNode((d + 3) % 4, (d + 5) % 4, d);
                break;
        }
    }

    public void joinNode(int a, int b, int d) {

        int x = 0;
        int y = 0;
        int c = (d + dir) % 4;
        switch (c) {
            case (0):
                x = actual.x;
                y = actual.y + 1;
                break;
            case (1):
                x = actual.x + 1;
                y = actual.y;
                break;
            case (2):
                x = actual.x;
                y = actual.y - 1;
                break;
            case (3):
                x = actual.x - 1;
                y = actual.y;
                break;
        }
        actual.neighbors[a]= new BoxNode(x, y);
        actual.neighbors[a].neighbors[b]=actual;
        actual.bloqueado = true;
        actual = actual.neighbors[a];
        sol.add(actual);
    }

    public int nextX(int d) {
        int c = (d + dir) % 4;
        switch (c) {
            case (0):
                return actual.x;
            case (1):
                return actual.x + 1;
            case (2):
                return actual.x;
            case (3):
                return actual.x - 1;
        }
        return -1;
    }

    public int nextY(int d) {
        int c = (d + dir) % 4;
        switch (c) {
            case (0):
                return actual.y + 1;
            case (1):
                return actual.y;
            case (2):
                return actual.y - 1;
            case (3):
                return actual.y;
        }
        return -1;
    }

    public void regresar(BoxNode actual, Percept p){
        int cambio;
        BoxNode temp = null;
        cmd.add(language.getAction(3));
        cmd.add(language.getAction(3));
        while(/*actual.isBifurcacion = false*/isBifurcacion(p) == false){
            if(stack.get() == 1 || stack.get() == 3){
                cambio = (stack.pop() + 2) % 4;
            }else{
                cambio = stack.pop();
            }
            switch (cambio){
                case 0:
                    cmd.add(language.getAction(2));
                    break;
                case 1:
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(2)); // advance
                    break;
                case 2: //Atras
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(2)); // advance
                    break;
                case 3: //Izquierda
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(3)); // rotate
                    cmd.add(language.getAction(2)); // advance
                    break;
            }
            temp = actual;
            actual = actual.neighbors[0];
            caminoSolucion.pop();
        }
        temp.bloqueado = true;
    }      
}