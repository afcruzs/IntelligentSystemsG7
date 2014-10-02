package unalcol.agents.examples.labyrinth.multeseo.rolos;


import unalcol.agents.examples.labyrinth.multeseo.rolos.Grafo;
import unalcol.agents.examples.labyrinth.multeseo.rolos.Nodo;
import unalcol.agents.examples.labyrinth.multeseo.rolos.Stack;
import java.util.LinkedList;
import unalcol.agents.*;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.Language;
import unalcol.agents.simulate.util.SimpleLanguage;

public class TheAgentLosRolos extends SimpleTeseoAgentProgram {

    public int numGiros = 0;
    public int posicion = 0;
    public int posicionAnterior = -1;
    public int x = 0;
    public int y = 0;
    public Grafo grafo = new Grafo(new Nodo(0, 0));
    public Stack pilaAcciones;
    public boolean podaCaminoSinSalida = false;

    public TheAgentLosRolos(Language lenguaje) {
        super.setLanguage(language);
        language = (SimpleLanguage) lenguaje;
        pilaAcciones = new Stack();
    }

    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        boolean percepciones[] = {PF, PD, PA, PI};
        int opciones = 4;
        for (int i = 0; i < percepciones.length; i++) {
            if (percepciones[i]) {
                opciones--;
            }
        }
        if (opciones == 1) {
            grafo.getRoot().setPeso(grafo.getRoot().getPeso() + 1);
            podaCaminoSinSalida = true;
        }
        if (opciones > 2 && podaCaminoSinSalida) {
            grafo.getRoot().getHojas()[posicionAnterior]=true;
            grafo.getRoot().getNodos()[posicionAnterior]=null;
            podaCaminoSinSalida=false;
        }
        percepciones[(0 + posicion) % 4] = PF;
        percepciones[(1 + posicion) % 4] = PD;
        percepciones[(2 + posicion) % 4] = PA;
        percepciones[(3 + posicion) % 4] = PI;
        for (int v = 0; v < 4; v++) {
            if (percepciones[v] == false) {
                grafo.crearNodo(x, y, v);
            }
        }
        int movimiento = 0;
        // devuelve la primera posicion libre a la que se puede mover
        int posicionDeMenosPeso = -1;
        int i = posicion;
        int j=0;
        while(j<4){
        //for (int j = posicion; j < Math.abs((posicion-1)%4); j++) {
            if (!percepciones[i] && grafo.getRoot().getNodos()[i] != null) {
               
                if (posicionDeMenosPeso == -1 ){
                    if(!grafo.getRoot().getNodos()[i].isNodo_hoja()){
                        posicionDeMenosPeso = i;
                    }
                } else {
                    if((grafo.getRoot().getNodos()[posicionDeMenosPeso].getPeso() > grafo.getRoot().getNodos()[i].getPeso())){
                        posicionDeMenosPeso = i;
                    }
                }
            }
            i=(i+1)%4;
            j++;
        }
        //}
        if((posicionDeMenosPeso+1)%4==posicion){
            return 3;
        }
        if((posicionDeMenosPeso+2)%4==posicion){
            return 2;
        }
        if((posicionDeMenosPeso+3)%4==posicion){
            return 1;
        }
        return 0;              

    }

    @Override
    public void init() {
        cmd.clear();
        // se crea el primer nodo del sparse
    }

    @Override
    public Action compute(Percept p) {
        if (cmd.size() == 0) {
            boolean PF = ((Boolean) p.getAttribute("front")).
                    booleanValue();
            if(!PF){
                PF = ((Boolean) p.getAttribute("afront")).
                    booleanValue();
            }
            boolean PD = ((Boolean) p.getAttribute("right")).
                    booleanValue();
            if(!PD){
                PD= ((Boolean)p.getAttribute("aright")).
                    booleanValue();
            }
            boolean PA = ((Boolean) p.getAttribute("back")).
                    booleanValue();
            if(!PA){
                PA= ((Boolean)p.getAttribute("aback")).
                    booleanValue();
            }
            boolean PI = ((Boolean) p.getAttribute("left")).
                    booleanValue();
            
            if(!PI){
                PI= ((Boolean)p.getAttribute("aleft")).
                    booleanValue();
            }
            boolean MT = ((Boolean) p.getAttribute("exit")).
                    booleanValue();

            if(MT){
                return new Action("die");
            }
            numGiros = accion(PF, PD, PA, PI, MT);
            if (0 <= numGiros && numGiros < 4) {
                for (int i = 1; i <= numGiros; i++) {
                    cmd.add("rotate");
                    pilaAcciones.push("rotate");
                }
                cmd.add("advance"); // advance
                pilaAcciones.push("advance");

            } else {
                cmd.add("no_op"); // die
            }
        }
        String x = cmd.get(0);
        switch (x) {
            case "advance":
                switch (posicion) {
                    case 0:
                        this.y++;
                        posicionAnterior=2;
                        break;
                    case 1:
                        this.x++;
                        posicionAnterior=3;
                        break;
                    case 2:
                        this.y--;
                        posicionAnterior=0;
                        break;
                    case 3:
                        this.x--;
                        posicionAnterior=1;
                        break;
                }
                grafo.setRoot(grafo.getRoot().getNodos()[posicion]);
                grafo.getRoot().setPeso(grafo.getRoot().getPeso() + 1);                
                break;
            case "rotate":
                posicionAnterior=posicion;
                posicion = (posicion + 1) % 4;//rotate
                break;

        }
        cmd.remove(0);
        return new Action(x);
        }
    
    }

