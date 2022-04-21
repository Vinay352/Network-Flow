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

public class Edge {

    int source;
    int dest;
    int weight;

    Edge(int source, int dest, int weight){
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source = " + this.source +
                ", dest = " + this.dest +
                ", weight = " + this.weight +
                '}';
    }
}
