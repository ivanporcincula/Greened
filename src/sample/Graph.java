/***
 * Graph.java
 * Date created: January 29, 2021
 * Date last modified: February 2, 2021
 * Additional code by: Ivan Porcincula (removeEdge())
 */



package sample;

import java.util.*;

class Graph {

    HashMap<String, LinkedList<String>> connection = new HashMap<>();

    //to connect two vertices
    void addEdge(String src, String dest){
        if(!connection.containsKey(src)){
            LinkedList<String> nodes= new LinkedList<>();
            nodes.add(dest);
            connection.put(src,nodes);
        }
        else{
            LinkedList<String> nodes = connection.get(src);
            nodes.add(dest);
            connection.put(src,nodes);
        }
    }

    ////to disconnect two vertices
    void removeEdge(String src, String dest){

        LinkedList<String> nodes = connection.get(src); //get the values of the current key
        connection.remove(src);                         //remove the key to update
        nodes.remove(nodes.indexOf(dest));               //remove the vertex to be disconnected from the origin
        connection.put(src,nodes);                     //update the graph and put the new graph

    }

}