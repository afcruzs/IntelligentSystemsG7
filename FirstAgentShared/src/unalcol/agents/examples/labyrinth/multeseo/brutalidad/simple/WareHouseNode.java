/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.simple;


import java.util.TreeMap;

/**
 *
 * @author juanpablo
 */
   public class WareHouseNode{
    
    TreeMap<String, Node> values;
    //Se usa un solo arbol balanceado para buscar la informacion de cada nodo
    
   
    public WareHouseNode(){
    values = new TreeMap<String, Node>();
    }
   
    public void addNode(Node _node){
    values.put(_node.getKey(), _node);
    
    }
    
    public Node getNode(String key){
    return values.get(key);
    //Consulta la informacion de un nodo a partir de su indice
    }
    
    public void removeNode(String key){
    values.remove(key);
      //Elimina un nodo del almacen de nodos
    }
    
    public boolean existNode(String key){
    return values.get(key)!=null;
    //Evalua si un nodo ya existe.
    }
    
    public void clear(){
    values.clear();
    }
    
    
    
    }