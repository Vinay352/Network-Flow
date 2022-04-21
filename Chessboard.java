/*
 *
 * Version:
 *     $2$
 *
 */

/**
 * CSCI-665
 *
 *  Aim:  To run ford fulkerson algorithm using BFS and check if dominoes can be placed or not
 *
 *   Complexity of our Implementation: O( n*m*m ) where n = vertices and m = edges
 *
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Chessboard {
    public static void main(String[] args) throws Exception {

        // createTestChessboard();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().strip().split(" ");

        int row = Integer.parseInt(temp[0]);
        int col = Integer.parseInt(temp[1]);

        int[][] chessboard = new int[row + 1][col + 1];

        int[][] numberGivenToTheNode = new int[row + 1][col + 1];
        int count = 1;

        int[][] blackIs0AndWhiteIs1 = new int[row + 1][col + 1];

        for (int i = 1; i <= row; i++) {
            temp = br.readLine().strip().split(" ");
            for (int j = 1; j <= col; j++) {

                chessboard[i][j] = Integer.parseInt(temp[j - 1]);
                numberGivenToTheNode[i][j] = count++;

                if( (i % 2 == 1) == (j % 2 == 1) ){
                    blackIs0AndWhiteIs1[i][j] = 0;
                }
                else{
                    blackIs0AndWhiteIs1[i][j] = 1;
                }

                // System.out.print(chessboard[i][j]);
            }
            // System.out.println();
        }

//        System.out.println("------");
//        System.out.println(count);
//        System.out.println("------");
//
//        printBoard(chessboard, row, col);
//
//        printBoard(numberGivenToTheNode, row, col);
//
//        printBoard(blackIs0AndWhiteIs1, row, col);


        createTestAdjacencyMatrix(chessboard, row, col, numberGivenToTheNode, 0, count, blackIs0AndWhiteIs1);


    }

    private static void printAdjMatrix(int[][] chessboard, int row, int col) {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static void printBoard(int[][] chessboard, int row, int col) {

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println();
        }

    }


    private static boolean depthFirstSearch(int[][] adjMatrix, int start, int sink, boolean[] visited){

        visited[start] = true;

        for (int i = 0; i < adjMatrix[start].length; i++) {
            if ( adjMatrix[start][i] == 1 ) {
                if( visited[i] == false ){
                    if( i == sink ){
                        return true;
                    }
                    return depthFirstSearch(adjMatrix, i, sink, visited);
                }
            }
        }

        return false;
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



    private static void canTheDominoesBePlaced(int[][] adjMatrix, int source, int sink, int blackUnoccupied, int whiteUnoccupied) {

        if (blackUnoccupied != whiteUnoccupied) {
            System.out.println("NO");
        }
        else{
            int limit = sink + 1;

            int[][] residual = new int[limit][limit];

            for (int i = 0; i < limit; i++){
                for (int j = 0; j < limit; j++){
                    residual[i][j] = adjMatrix[i][j];
                }
            }

            int parentTracker[] = new int[limit];

            int maxFlow = 0;
            int var = 0;

            while ( true ) {

                int[] visitedTracker = new int[limit];
                visitedTracker[source] = 1;

                parentTracker[source] = -1;

                for (int i = 0; i < limit; ++i) {
                    visitedTracker[i] = 0;
                }

                Deque<Integer> queue = new LinkedList<Integer>();
                queue.add(source);

                for( ; !queue.isEmpty() ; ) {
                    int head = queue.pollFirst();

                    int next = 0;

                    while ( next < limit) {
                        if (visitedTracker[next] != 1) {
                            if( residual[head][next] != 0 ){
                                queue.add(next);
                                parentTracker[next] = head;
                                visitedTracker[next] = 1;
                            }
                            else{

                            }
                        }
                        next++;
                    }
                }
                if( visitedTracker[sink] == 1 ){
                    var = 1;
                }
                else if( visitedTracker[sink] == 0 ){
                    var = 0;
                }

                if(var == 0){
                    break;
                }

                int bottleneck = 1000000000;

                int previous = sink;
                while( previous != source ){
                    int parent = parentTracker[previous];
                    if(bottleneck > residual[parent][previous]){
                        bottleneck = residual[parent][previous];
                    }
//                    bottleneck = Math.min(bottleneck, residual[parent][previous]);

                    previous = parentTracker[previous];
                }

                previous = sink;
                while( previous != source ){
                    int parent = parentTracker[previous];
                    residual[parent][previous] -= bottleneck;
                    residual[previous][parent] += bottleneck;

                    previous = parentTracker[previous];
                }

                maxFlow += bottleneck;
            }

            if(maxFlow == blackUnoccupied && maxFlow == whiteUnoccupied){
                System.out.println("YES");
//                System.out.println("MAX FLOW = " + maxFlow);
            }
            else{
                System.out.println("NO");
            }

        }

    }



    private static void createTestAdjacencyMatrix(int[][] chessboard, int row, int col, int[][] numberGivenToTheNode, int source, int sink, int[][] blackIs0AndWhiteIs1) {

        int[][] adjMatrix = new int[row * col + 2][row * col + 2];

        int adjMatrixCols = row * col + 2;
        int adjMatrixRows = row * col + 2;

        int blackUnoccupied = 0;
        int whiteUnoccupied = 0;

        int blackOccupied = 0;
        int whiteOccupied = 0;


        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {


                if (chessboard[i][j] != 1) {

                    if ( blackIs0AndWhiteIs1[i][j] == 1 ) {
                        // white
                        adjMatrix[numberGivenToTheNode[i][j]][sink] = 1;
                        whiteUnoccupied++;
                    }

                    if ( blackIs0AndWhiteIs1[i][j] == 0 ) {
                        // black
                        adjMatrix[source][numberGivenToTheNode[i][j]] = 1;
                        blackUnoccupied++;
                    }


                    // corners
                    if (i == 1 && j == 1) {
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;
                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;
                        }
                    }

                    if (i == 1 && j == col) {
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;
                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;
                        }
                    }

                    if (i == row && j == 1) {
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;

                        }
                    }

                    if (i == row && j == col) {
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;

                        }
                    }


                    // edges
                    if (i == 1 && j > 1 && j < col) {
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;

                        }
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;

                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;

                        }
                    }

                    if (i == row && j > 1 && j < col) {
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;

                        }
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;
                        }
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                    }

                    if (j == 1 && i > 1 && i < row) {
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;

                        }
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;

                        }
                    }

                    if (j == col && i > 1 && i < row) {
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;

                        }
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;

                        }
                    }


                    // in between
                    if (i > 1 && i < row && j > 1 && j < col) {
                        if (chessboard[i][j - 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j - 1]] = 1;

                        }
                        if (chessboard[i][j + 1] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i][j + 1]] = 1;

                        }
                        if (chessboard[i - 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i - 1][j]] = 1;

                        }
                        if (chessboard[i + 1][j] != 1) {
                            adjMatrix[numberGivenToTheNode[i][j]][numberGivenToTheNode[i + 1][j]] = 1;

                        }
                    }

                } else {
                    // chessboards with value == 1

                    if ( blackIs0AndWhiteIs1[i][j] == 1 ) {
                        // white
                        whiteOccupied++;
                    }

                    if ( blackIs0AndWhiteIs1[i][j] == 0 ) {
                        // black
                        blackOccupied++;
                    }

                }


            }
        }

//        System.out.println("----- blackUnoccupied = " + blackUnoccupied);
//        System.out.println("----- whiteUnoccupied = " + whiteUnoccupied);
//        System.out.println("----- blackOccupied = " + blackOccupied);
//        System.out.println("----- whiteOccupied = " + whiteOccupied);
//
//        printAdjMatrix(adjMatrix, adjMatrixRows, adjMatrixCols);


        canTheDominoesBePlaced(adjMatrix, source, sink, blackUnoccupied, whiteUnoccupied);


    }


    private static void createTestChessboard() {

        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= 5; col++) {
                if ((col + row) % 2 == 0) {
                    System.out.print("Black, ");
                } else {
                    System.out.print("White, ");
                }
            }
            System.out.println();
        }

    }


}
