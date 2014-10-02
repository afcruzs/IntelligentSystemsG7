/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.multeseo.singrupo;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.LabyrinthPercept;
import unalcol.agents.simulate.util.Language;
import unalcol.agents.simulate.util.SimpleLanguage;

import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Daniel Felipe
 */
public class AgenteSinGrupo implements AgentProgram {

    protected Graph memoria;
    protected Location miUbicacion;
    protected Vector<String> cmds;
    protected Language language;
    protected Vector<String> pasosPrevios;

    public AgenteSinGrupo(SimpleLanguage _language) {
        this.language = _language;
        this.memoria = new Graph();
        this.cmds = new Vector<>();
        this.pasosPrevios = new Vector<>();
    }

    @Override
    public void init() {
        this.cmds.clear();
    }

    @Override
    public Action compute(Percept perception) {
        LabyrinthPercept p = (LabyrinthPercept) perception;
        miUbicacion = null; // Modified by Jonatan Gomez --> p.getDireccion();
        String sigAccion = language.getAction(language.getActionIndex("no_op"));
        Action respuesta = new Action(sigAccion);

        if (!cmds.isEmpty()) {
            sigAccion = cmds.get(0);
            if (sigAccion.equals(language.getAction(language.getActionIndex("advance"))) 
                    && p.getAttribute(language.getPercept(language.getPerceptIndex("oponente"))) == true) {
                //return respuesta;
            } else {
                cmds.remove(0);
                return new Action(sigAccion);
            }
        }

        Node nodoActual = new Node(miUbicacion.getX(), miUbicacion.getY(), miUbicacion.getDireccion());
        if (!memoria.getNodos().contains(nodoActual)) {
            nodoActual.setCaminos(explorarNodo(p));
            if (memoria.size() != 0) {
                Edge arco = crearArco(nodoActual);
                memoria.agregarArista(arco);
            }
            memoria.agregarNodo(nodoActual);
            memoria.setCurrentNode(nodoActual);
            agregarAlPath(nodoActual);
            tomarDecision(nodoActual, p);
        } else {
            nodoActual = memoria.buscarNodo(nodoActual);
            actualizarNodo(nodoActual, p);
            memoria.setCurrentNode(nodoActual);
            tomarDecision(nodoActual, p);
        }

        if (cmds.isEmpty()) {
            return respuesta;
        } else {
            sigAccion = cmds.get(0);
            cmds.remove(0);
            return new Action(sigAccion);
        }
    }

    private char[] explorarNodo(LabyrinthPercept p) {
        char[] caminos = new char[4];
        int orientacion = miUbicacion.getDireccion();

        if (memoria.size() != 0) {
            if (orientacion == 0 || orientacion == 1) {
                caminos[orientacion + 2] = 'R';
            } else {
                caminos[orientacion - 2] = 'R';
            }
        }

        for (int i = 0; i < 4; i++) {
            int num = (orientacion + language.getPerceptIndex(language.getPercept(i))) % 4;
            if (!(Boolean) p.getAttribute(language.getPercept(i))) {
                if (caminos[num] != 'R') {
                    caminos[num] = 'S';
                }
            } else {
                if (caminos[num] != 'R') {
                    caminos[num] = 'I';
                }
            }
        }
        return caminos;
    }

    private Edge crearArco(Node nodoActual) {
        Node nodoA = memoria.getCurrentNode();
        Node nodoB = nodoActual;
        Edge arco = new Edge(nodoA, nodoB, pasosPrevios);
        return arco;
    }

    private void agregarAlPath(Node nodo) {
        char[] caminos = nodo.getCaminos();
        if (opciones(caminos) > 1) {
            memoria.getPath().add(nodo);
        }
    }

    private int opciones(char[] caminos) {
        int cont = 0;
        for (char estado : caminos) {
            if (estado == 'S') {
                cont++;
            }
        }
        return cont;
    }

    private void tomarDecision(Node nodoActual, LabyrinthPercept p) {
        int opciones = opciones(nodoActual.getCaminos());
        if (!termino(p)) {
            if (opciones == 0) {
                accionesANodo(memoria.getPath().get(memoria.getPath().size() - 1), p);
                //primeraLibre(p);
            } else if (opciones == 4) {
                cmds.add(language.getAction(2));
            } else {
                primeraOpcion(p);
            }
        }
        pasosPrevios.clear();
        //Modified by Jonatan
        //for (unalcol.types.collection.Iterator<String> it = cmds.elements(); it.hasNext();) {
        //    pasosPrevios.add(it.next());
        //}
    }

    private boolean termino(LabyrinthPercept p) {
        return (((Boolean) p.getAttribute(language.getPercept(4))).booleanValue());
    }

    private void accionesANodo(Node nodo, LabyrinthPercept p) {
        Edge buscarRuta = memoria.buscarRuta(nodo, memoria.getCurrentNode());
        //accionesContrarias(buscarRuta.getCmds());
        accionesContrarias();
    }

    private void primeraLibre(LabyrinthPercept p) {
        int direccion = miUbicacion.getDireccion();
        char[] caminos = memoria.getCurrentNode().getCaminos();
        int primeraOpcion = 0;
        for (int i = 0; i < caminos.length; i++) {
            if (caminos[i] == 'R') {
                primeraOpcion = i;
                break;
            }
        }
        int limite = primeraOpcion - direccion;
        if (limite < 0) {
            limite = limite + 4;
        }
        for (int j = 0; j < limite; j++) {
            cmds.add(language.getAction(3));
        }
        cmds.add(language.getAction(2));
    }

    private void primeraOpcion(LabyrinthPercept p) {
        int direccion = miUbicacion.getDireccion();
        char[] caminos = memoria.getCurrentNode().getCaminos();
        int primeraOpcion = 0;
        for (int i = 0; i < caminos.length; i++) {
            if (caminos[i] == 'S') {
                primeraOpcion = i;
                break;
            }
        }
        int limite = primeraOpcion - direccion;
        if (limite < 0) {
            limite = limite + 4;
        }
        for (int j = 0; j < limite; j++) {
            cmds.add(language.getAction(3));
        }
        cmds.add(language.getAction(2));
    }

    private void actualizarNodo(Node nodoActual, LabyrinthPercept p) {
        int orientacion = miUbicacion.getDireccion();
        char[] caminos = nodoActual.getCaminos();

        if (memoria.size() != 0) {
            if (orientacion == 0 || orientacion == 1) {
                caminos[orientacion + 2] = 'R';
            } else {
                caminos[orientacion - 2] = 'R';
            }
        }

        for (int i = 0; i < 4; i++) {
            int num = (orientacion + language.getPerceptIndex(language.getPercept(i))) % 4;
            if (!(Boolean) p.getAttribute(language.getPercept(i))) {
                if (caminos[num] != 'R') {
                    caminos[num] = 'S';
                }
            } else {
                if (caminos[num] != 'R') {
                    caminos[num] = 'I';
                }
            }
        }
    }

    private void accionesContrarias() {
        cmds.add(language.getAction(3));
        cmds.add(language.getAction(3));
        cmds.add(language.getAction(2));
        //memoria.getPath().remove(memoria.getPath().size()-1);
    }

}
