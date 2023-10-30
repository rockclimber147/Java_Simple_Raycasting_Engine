package model;

import vectors.LineSegment;
import vectors.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    private final double displayWidth;
    private final double displayHeight;
    private double rectangleScalingFactor;
    private final Player player;
    private final GameMap map;
    private final ArrayList<LineSegment> mapEdges;
    private final ArrayList<Ray> rays;
    private int rayCount;
    private double playerDirection;
    private double playerFOV;
    private double colorFactor;

    private ArrayList<ModelData> data;
    private ArrayList<ModelData> activeData;

    public Model(double displayWidth, double displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        rectangleScalingFactor = 25000;
        colorFactor = 500;
        player = new Player(new Vector(displayWidth / 4, displayHeight / 4), 20,0);
        map = new GameMap(displayWidth, displayHeight);
        mapEdges = map.getMapEdges();
        rays = new ArrayList<>();

        data = new ArrayList<>();
        for (int i = 0; i < 500; i++){
            data.add(new ModelData());
        }

        rayCount = 1;
        for (int i = 0; i < rayCount; i++) {
            rays.add(new Ray(this));
        }
        playerDirection = 0;
        playerFOV = 90;
        castRays();
        updateAngles();
    }
    // sets the coefficients for each ray to the values corresponding with the closest valid intersect
    public void castRays(){
        for (Ray ray:rays){
            for (LineSegment l:mapEdges){
                ray.cast(l);
            }
        }
        updateData();
    }

    public void updateAngles(){
        playerDirection = player.getCurrentAngle();
        if (rays.size() == 1){
            rays.get(0).update(playerDirection);
        } else {
            double i = playerDirection - (playerFOV / 2);
            for (Ray r:rays){
                r.update(i);
                i += playerFOV / (rays.size()-1);
            }
        }
    }

    public void updateRayOrigin(){
        for (Ray r:rays){
            r.update(player.getCurrentAngle());
        }
    }

    public void changeRayCount(int newCount){
        int difference = rays.size() - newCount;
        System.out.println("\nrays.size(): " + rays.size());
        System.out.println("newCount: " + newCount);
        System.out.println("difference:" + difference);

        if (difference < 0){
            for (int i = difference; i < 0; i++){
                rays.add(new Ray(this));
            }
        } else if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                rays.remove(rays.size() - 1 - i);
            }
        }
        rayCount = rays.size();
        System.out.println("newSize: " + rayCount);
        updateAngles();
        castRays();
    }

    public void changeFOV(double newFOV){
        playerFOV = newFOV;
        updateAngles();
    }

    public void setRectangleScalingFactor(double newFactor){
        rectangleScalingFactor = newFactor;
    }

    public void setColorFactor(double newFactor){
        this.colorFactor = newFactor;
    }

    public int[][] getFormattedRays(){
        int[][] formattedRays = new int[rays.size()][2];
        for (int i = 0; i < rays.size(); i++){
            formattedRays[i][0] = rays.get(i).getIntersectX();
            formattedRays[i][1] = rays.get(i).getIntersectY();

            data.get(i).rayX = rays.get(i).getIntersectX();
            data.get(i).rayY = rays.get(i).getIntersectY();
        }
        return formattedRays;
    }

    public double[] getDistanceShaders(){
        double[] magnitudes = new double[rayCount];
        for (int i = 0; i < rayCount; i++){
            double brightness = 255 - (255 * (rays.get(i).getMagnitude() / (colorFactor)));
            if (brightness >= 255){
                brightness = 254;
            } else if (brightness < 0){
                brightness = 1;
            }
            magnitudes[rayCount - i - 1] = brightness;

            data.get(rayCount - i - 1).distanceShader = brightness;
        }
        return magnitudes;
    }

    public double[] getDistanceColorCoefficients(){
        double[] colorCoefficients = new double[rayCount];
        for (int i = 0; i < rayCount; i++){
            double coefficient = 1 - rays.get(i).getMagnitude() / (colorFactor);
            if (coefficient > 1){
                coefficient = 1;
            } else if (coefficient < 0){
                coefficient = 0;
            }
            colorCoefficients[rayCount - i - 1] = coefficient;

            data.get(rayCount - i - 1).colorCoefficient = coefficient;
        }
        return colorCoefficients;
    }

    public double[][] getTargetColors(){
        double[][] packedColors = new double[rays.size()][];
        for (int i = 0; i < rays.size(); i++){
            packedColors[rays.size() - i - 1] = rays.get(i).getTargetColor();

            data.get(rays.size() - i - 1).targetColor = rays.get(i).getTargetColor();
        }
        return packedColors;
    }

    public int[][] getRectangleInfo(){
        return new int[][]{
                getRectangleWidths(),
                getRectangleHeights()
        };
    }

    private int[] getRectangleWidths(){
        int[] rectangleWidths = new int[rayCount];
        int spacing = (int) displayWidth / rayCount;

        for(int i = 0; i < rayCount; i++){
            rectangleWidths[i] = spacing;


            data.get(i).rectangleWidth = spacing;
        }
        int remainder = (int) displayWidth % rayCount;
        int remainderDifference = rayCount - remainder;
        int a = 1;
        int b = 1;
        if (remainder != 0){
            for (int i = 0; i < rayCount; i++){
                if ((a * remainder) < (b * remainderDifference)){
                    a++;
                } else {
                    b++;
                    rectangleWidths[i]++;

                    data.get(i).rectangleWidth++;
                }
            }
        }
        return rectangleWidths;
    }

    private int[] getRectangleHeights(){
        int[] rectangleHeights = new int[rayCount];
        for (int i = 0; i < rayCount; i++){
            double currentMagnitude = rays.get(i).getMagnitude();
            rectangleHeights[rayCount - i - 1] = (int) Math.round(rectangleScalingFactor / currentMagnitude);

            data.get(rayCount - i - 1).rectangleHeight = (int) Math.round(rectangleScalingFactor / currentMagnitude);
        }
        return rectangleHeights;
    }

    private void updateData(){
        getFormattedRays();
        getDistanceColorCoefficients();
        getDistanceShaders();
        getTargetColors();
        getRectangleInfo();
    }



    public ArrayList<LineSegment> getMapEdges(){
        return mapEdges;
    }

    public ArrayList<ModelData> getRayData(){
        return data;
    }

    public int getRayCount(){
        return rays.size();
    }

    public Player getPlayer(){
        return player;
    }

    public GameMap getGameMap(){
        return map;
    }

    public ArrayList<Ray> getRays(){
        return rays;
    }

    public static void main(String[] args) {
        Model m = new Model(1002,1000);
        System.out.println(m.getRays());
        m.changeRayCount(5);
        System.out.println(m.getRays());
        m.updateAngles();
        m.castRays();
        System.out.println(Arrays.deepToString(m.getRectangleInfo()));
    }

}
