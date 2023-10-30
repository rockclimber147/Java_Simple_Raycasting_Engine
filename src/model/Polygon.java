package model;
import vectors.*;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Vector> vertices;
    private final ArrayList<LineSegment> edges;
    int vertexCount;

    public Polygon(){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        vertexCount = 0;
    }

    public void addVertex(double x, double y){
        vertices.add(new Vector(x,y));
        vertexCount++;
    }

    public void compileEdges(){
        if (vertices.size() < 2){
            return;
        }
        for(int i = 1; i < vertexCount; i++){
            edges.add(new LineSegment(vertices.get(i - 1), vertices.get(i)));
        }
        if (vertices.size() > 2) {
            edges.add(new LineSegment(vertices.get(vertexCount - 1), vertices.get(0)));
        }
    }

    /**
     *
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param xOffset the x value of the upper left corner
     * @param yOffset the y value of the upper left corner
     */
    public void setRectangle(int width, int height, int xOffset, int yOffset){
        if (edges.size() != 0){
            return;
        }
        vertices.add(new Vector(xOffset, yOffset));
        vertices.add(new Vector(xOffset + width, yOffset));
        vertices.add(new Vector(xOffset + width, yOffset + height));
        vertices.add(new Vector(xOffset, yOffset + height));
        vertexCount = 4;
        compileEdges();
    }

    public void setEdgeColor(int index, double r, double g, double b){
        edges.get(index).setColor(r,g,b);
    }

    public int[] getXCoordinates(){
        int[] xCoordinates = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++){
            xCoordinates[i] = (int) vertices.get(i).getX();
        }
        return xCoordinates;
    }
    public int[] getYCoordinates(){
        int[] yCoordinates = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++){
            yCoordinates[i] = (int) vertices.get(i).getY();
        }
        return yCoordinates;
    }
    public int getVertexCount(){
        return vertexCount;
    }

    public ArrayList<LineSegment> getEdges(){
        return this.edges;
    }

    public static void main(String[] args) {
        Polygon p = new Polygon();
        p.addVertex(3,2);
        p.addVertex(4,1);
        p.addVertex(5,0);
        p.compileEdges();

    }
}
