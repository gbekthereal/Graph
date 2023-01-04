package com.company;

import java.io.File;
import java.io.IOException;

class Main {
    public static boolean fileExists(File f) { return f.exists() && !f.isDirectory(); }

    public static void main(String [] args) throws IOException {
        String typeFileName = "test1.txt";
        File f = new File(typeFileName);

        if (fileExists(f)) {
            ReadDataFromFile fr = new ReadDataFromFile(f);
            GetMaxMinVertex result = fr.findMaxAndMinVertex(f);

            // GRAPH STUFF
            Graph G = new Graph(fr, result.getCountVertices(), result.getMin(), result.getCountLines());
            G.addDummyVertex();

            System.out.println(G);

            // TREE STUFF
            //Tree t = new Tree(G.getMinVertex(), G.getDummyVertex(), G);  // from u to v (u, v)
            //t.edgeUpdate("delete", 6, 7);
            //t.edgeUpdate("insert", 19, 15);
            //t.vertexUpdate("delete", 9);
        }
        else System.err.println("File \"" + typeFileName + "\" does not exist.");
    }
}