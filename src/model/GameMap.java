package model;

import vectors.LineSegment;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
    private final ArrayList<Polygon> polygons;
    private double mapHeight;
    private double mapWidth;

    /**
     * Constructs an empty GameMap. By convention the first polygon is the map bounds
     * @param displayHeight height of parent frame
     * @param displayWidth width of parent frame
     */
    public GameMap(double displayWidth, double displayHeight){
        this.mapHeight = displayHeight / 2;
        this.mapWidth = displayWidth / 2;
        this.polygons = new ArrayList<>();

        // initialize map borders
        Polygon mapBorders = new Polygon();
        mapBorders.addVertex(0,0);
        mapBorders.addVertex(mapWidth, 0);
        mapBorders.addVertex(mapWidth, mapHeight);
        mapBorders.addVertex(0,mapHeight);
        mapBorders.compileEdges();
        mapBorders.setEdgeColor(0,0,0,255);
        mapBorders.setEdgeColor(1,0,0,255);
        mapBorders.setEdgeColor(2,0,0,255);
        mapBorders.setEdgeColor(3,0,0,255);
        polygons.add(mapBorders);

        Polygon toAdd = new Polygon();
        toAdd.setRectangle((int) mapWidth / 4, (int) mapHeight / 4, 0,0);
        toAdd.setEdgeColor(0,0,200,200);
        toAdd.setEdgeColor(1,200,0,200);
        toAdd.setEdgeColor(2,60,180,40);
        toAdd.setEdgeColor(3,200,100,100);
        polygons.add(toAdd);

        toAdd = new Polygon();
        toAdd.setRectangle((int) mapWidth / 10, (int) mapHeight / 10, (int) mapWidth / 3, (int) mapHeight / 3);
        toAdd.setEdgeColor(0,200,200,0);
        toAdd.setEdgeColor(1,200,50,0);
        toAdd.setEdgeColor(2,200,200,0);
        toAdd.setEdgeColor(3,200,0,120);
        polygons.add(toAdd);

        toAdd = new Polygon();
        toAdd.addVertex(1200,0);
        toAdd.addVertex(1000,0);
        toAdd.addVertex(1000,50);
        toAdd.addVertex(1100,50);
        toAdd.addVertex(1100,100);
        toAdd.addVertex(900,100);
        toAdd.addVertex(900,150);
        toAdd.addVertex(1200,150);
        toAdd.compileEdges();
        toAdd.setEdgeColor(0,200,200,100);
        toAdd.setEdgeColor(1,200,200,200);
        toAdd.setEdgeColor(2,200,200,255);
        toAdd.setEdgeColor(3,200,200,0);
        toAdd.setEdgeColor(4,200,200,50);
        toAdd.setEdgeColor(5,200,200,150);
        toAdd.setEdgeColor(6,200,200,70);
        polygons.add(toAdd);

        toAdd = new Polygon();
        for (int i = 0; i < 10; i++) {
            double xOffset = 900;
            double yOffset = 400;
            double radius;
            if (i % 2 == 0){
                radius = 50;
            } else {
                radius = 150;
            }
            toAdd.addVertex(xOffset + radius * Math.cos(2 * Math.PI * i / 10),
                    yOffset + radius * Math.sin(2 * Math.PI * i / 10));
        }
        toAdd.compileEdges();
        for (int i = 0; i < 10; i++){
            if (i % 2 == 0){
                toAdd.setEdgeColor(i,255,255,0);
            } else {
                toAdd.setEdgeColor(i,255,0,255);
            }
        }
        polygons.add(toAdd);

    }

    // [[[Ax1,Ax2,...,],[Ay1,Ay2,...]],[[Bx1,Bx2,...][By1,By2,...]],...]
    public int[][][] exportPolygons(){
        Polygon current;
        int[][][] packedVertices = new int[polygons.size()][2][];
        for (int i = 0; i < polygons.size(); i++){
            current = polygons.get(i);
            packedVertices[i][0] = current.getXCoordinates();
            packedVertices[i][1] = current.getYCoordinates();
        }
        return packedVertices;
    }

    public ArrayList<Polygon> getPolygons(){
        return polygons;
    }

    public ArrayList<LineSegment> getMapEdges(){
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (Polygon p: polygons){
            segments.addAll(p.getEdges());
        }
        return segments;
    }

    public static void main(String[] args) {
        GameMap map = new GameMap(1000,1000);
        System.out.println(map.getMapEdges());
        System.out.println(map.getPolygons().get(0).getVertexCount());
    }
}
