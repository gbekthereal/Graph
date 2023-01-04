package com.company;

import java.util.*;

class Graph {
    private int V, E, minVertex, dummyVertex;
    private boolean [] visited;
    private Bag<Integer>[] adjacencyList;


    public Graph(ReadDataFromFile fr, int countVertices, int findMinVertex, int countLines)  {
        if (findMinVertex > 0) this.V = countVertices + 2;  // + 1 for dummy vertex r
        else this.V = countVertices + 1;

        this.minVertex = findMinVertex;

        adjacencyList = (Bag<Integer>[]) new Bag[V];
        for (int i=minVertex; i<V; i++) adjacencyList[i] = new Bag<>();

        visited = new boolean[V];
        for (int i=minVertex; i<V; i++) visited[i] = false;

        Set<String> temp = new HashSet<>();
        for (int i=0; i<countLines; i++) {
            int v = fr.readInt(), w = fr.readInt();  // nodes from file

            // in case a duplicate pair v w exists in file (needs O(1) time to check)
            if (!temp.contains(String.valueOf(w) + " " + String.valueOf(v)) && !temp.contains(String.valueOf(v) + " " + String.valueOf(w))) {
                temp.add(String.valueOf(v) + " " + String.valueOf(w));
                addEdge(v, w);
            }
        }
    }
    private void validateVertex(int v) {
        if (v < minVertex || v > V)  throw new IllegalArgumentException("vertex " + v + " isn't between " + minVertex + " and " + (V-1));
    }
    private void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
        E++;
    }

    // add a dummy vertex r into the graph and connect r with all the other vertices, so the graph will be connected
    public void addDummyVertex() {
        dummyVertex = V-1;

        // loop V-1 times to avoid self loop in dummy vertex
        for (int i=minVertex; i<V-1; i++) {
            adjacencyList[dummyVertex].add(i);
            adjacencyList[i].add(dummyVertex);
            E++;
        }

        /*
        System.out.println("dummy vertex id: " + dummyVertex + "\nGraph after dummy insertion:");
        for (int i=minVertex; i<adjacencyList.length; i++) System.out.println(i + ": " + adjacencyList[i]);
        System.out.println("\n");
         */
    }

    public boolean contains(int item) {
        for (int i=minVertex; i<adjacencyList.length; i++) {
            Iterator<Integer> iterator = adjacencyList[i].iterator();
            while (iterator.hasNext())
                if (iterator.next() == item) return true;
        }

        return false;
    }
    public int getMinVertex() { return minVertex; }
    public int getDummyVertex() { return dummyVertex; }
    public Bag<Integer> [] getAdjacencyList() { return adjacencyList; }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("G = (V,E) =  (" +  V + ", " + E + ")\n");

        for (int i=minVertex; i<V; i++) {
            s.append(i + ": ");   // need to use v-1, because of dummy vertex insertion

            for (int w : adjacencyList[i])
                s.append(w + " ");

            s.append("\n");
        }
        return s + "";
    }
}