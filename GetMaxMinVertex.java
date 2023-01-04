package com.company;

final class GetMaxMinVertex { // helper class that returns 3 values in once
    private final int countVertices, min, countLines;

    public GetMaxMinVertex(int countVertices, int min, int countLines) {
        this.countVertices = countVertices;
        this.min= min;
        this.countLines = countLines;
    }

    public int getCountVertices() { return countVertices; }
    public int getMin() { return min; }
    public int getCountLines() { return countLines; }
}
