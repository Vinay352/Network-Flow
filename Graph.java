/*
 *
 * Version:
 *     $2$
 *
 */

/**
 * CSCI-665
 *
 *   Helper class for Morale.java
 *
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */

import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    LinkedList<Integer>[] adjList;
    int graphVert;

    Graph(int v){
        this.graphVert = v;
        this.adjList = new LinkedList[v];
        for(int i = 0; i < v; i++){
            this.adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int dest){
        this.adjList[source].add( dest );
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjList=" + Arrays.toString(adjList) +
                ", graphVert=" + graphVert +
                '}';
    }
}
