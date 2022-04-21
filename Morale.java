/*
 *
 * Version:
 *     $2$
 *
 */

/**
 * CSCI-665
 *
 *  Aim:  Implementations of bellman ford algorithm and detection of
 *      nodes and number of nodes involved in negative cycle.
 *
 *   Complexity of our Implementation: O( vertices * edges )
 *
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Morale {

    // main class
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) ) ;
        int n = Integer.parseInt( br.readLine() );
        int m = Integer.parseInt( br.readLine() );

        Edge[] edge = new Edge[m];

        Graph g = new Graph(n);

        for(int i = 0; i< m; i++){
            String[] temp = br.readLine().strip().split(" ");

            int src = Integer.parseInt( temp[0].strip() );
            int dest = Integer.parseInt( temp[1].strip() );
            int weight = Integer.parseInt( temp[2].strip() );

            edge[i] = new Edge(src, dest, weight); // storing input

            g.addEdge(src, dest); // storing input

        }

//        for(int i = 0; i< m; i++){
//
//            System.out.println(edge[i]);
//
//        }
//        System.out.println("-----------------");
//        System.out.println(g);

        bellmanFord(n, m, edge, 0, g);

    }

    private static void bellmanFord(int vertices, int edges, Edge[] edge, int start, Graph g) {

        int[] distance = new int[vertices];
        int[] parent = new int[vertices];

        for(int i = 0; i < vertices; i++){
            distance[i] = 1000000000;
            parent[i] = -1;
        }

        parent[start] = start;
        distance[start] = 0;

        // running till (n - 1) iterations
        for(int i =0; i < vertices - 1; i++){
            for(int j = 0; j < edges; j++){

                int source = edge[j].source;
                int dest = edge[j].dest;
                int weight = edge[j].weight;

                // Float tempdistance = Float.valueOf( distance[source] + weight );

                if( distance[dest] > distance[source] + weight ){
                    distance[dest] = distance[source] + weight;
                    parent[dest] = source;
                }

            }
        }
        // System.out.println();

        HashSet<Integer> finalAnswer = new HashSet<>();

        boolean[] visited = new boolean[g.graphVert];

        // checking for negative cycle
        for(int j = 0; j < edges; j++){

            int source = edge[j].source;
            int dest = edge[j].dest;
            int weight = edge[j].weight;

            if( distance[dest] > distance[source] + weight  ){
                // negative cycle present

                if(visited[dest] == false){
                    // all nodes reachable from this node in which change is happening
                    // in the nth iteration, are a part of negative cycle
                    HashSet<Integer> a = doDFS(source, g, visited);

                    // union all such sets obtained from DFS of every node in negative cycle
                    finalAnswer.addAll(a);
                }



//                break;
            }

        }

//        System.out.println(finalAnswer);
//        System.out.println("------");
        System.out.println(finalAnswer.size());

    }

    private static HashSet<Integer> doDFS(int start, Graph graph, boolean[] visited) {

        depthFirstSearch(start, graph, visited);

        HashSet<Integer> set = new HashSet<>();

        for(int i = 0; i < visited.length; i++){
            if( visited[i] == true ){
                set.add(i);
            }
        }

        return set;
    }

    private static void depthFirstSearch(int start, Graph graph, boolean[] visited) {
        visited[start] = true;

        LinkedList<Integer> adjList = graph.adjList[start];

        Iterator<Integer> iter = adjList.listIterator();

        for(; iter.hasNext(); ){
            int neighbour = iter.next();
            if( visited[neighbour] == false ){
                depthFirstSearch(neighbour, graph, visited);
            }
        }
    }
}
