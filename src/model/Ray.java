package model;

import vectors.LineSegment;
import vectors.Vector;
import vectors.VectorMath;

import java.util.Arrays;

public class Ray {
    Model model;
    private LineSegment unitRay;
    private double[] coefficients = new double[2];
    private double[] targetColor = new double[3];
    private int intersectX;
    private int intersectY;
    private boolean targetIsMirror = false;


    public Ray(Model model){
        this.model = model;
        coefficients[0] = 999999;
        unitRay = new LineSegment(model.getPlayer().getPosition(),
                VectorMath.generateUnitVectorFromAngle(model.getPlayer().getCurrentAngle()).getVectorSum(model.getPlayer().getPosition()));
    }
    public void update(double angle){
        coefficients[0] = 999999;
        unitRay = new LineSegment(model.getPlayer().getPosition(),
                VectorMath.generateUnitVectorFromAngle(angle).getVectorSum(model.getPlayer().getPosition()));
    }

    public void cast(LineSegment target){
        double[] iterationCoefficients = VectorMath.findIntersectCoefficients(unitRay, target);
        if (iterationCoefficients == null){
            return;
        }
        if (iterationCoefficients[0] > 0 // intersect is in front of player
                && -iterationCoefficients[1] > 0 && -iterationCoefficients[1] <= 1 // intersect is on target segment
                && iterationCoefficients[0] < coefficients[0]){ // intersect is closer than last found
            coefficients = iterationCoefficients;
            Vector intersect = VectorMath.getIntersectPointGivenCoefficients(unitRay,coefficients[0]);
            intersectX = (int) intersect.getX();
            intersectY = (int) intersect.getY();
            targetIsMirror = target.isMirror();
            targetColor = target.getColor();
        }
    }

    public double[] getTargetColor(){
        return targetColor;
    }

    public double getMagnitude(){
        return coefficients[0];
    }
    public LineSegment getFinalRay(){
        return new LineSegment(model.getPlayer().getPosition(), VectorMath.getIntersectPointGivenCoefficients(unitRay, coefficients[0]));
    }

    public String toString(){
        return "unitRay: " + unitRay + "\ncoefficients: " + coefficients[0] + "," + coefficients[1];
    }

    public int getIntersectX() {
        return intersectX;
    }

    public int getIntersectY() {
        return intersectY;
    }
}
