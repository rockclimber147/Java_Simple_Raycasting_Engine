package vectors;

public class LineSegment {
    private final Vector startPoint;
    private final Vector endPoint;
    private final Vector direction;
    private double[] color;
    private boolean isMirror;

    public LineSegment(Vector startPoint, Vector endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        double dX = endPoint.getX() - startPoint.getX();
        double dy = endPoint.getY() - startPoint.getY();
        this.direction = new Vector(dX, dy);

        this.color = new double[] {0,0,0};
        this.isMirror = false;
    }

    public Vector getStartPoint() {
        return startPoint;
    }

    public Vector getEndPoint() {
        return endPoint;
    }

    public Vector getDirection() {
        return direction;
    }

    public double getMagnitude(){
        return direction.getMagnitude();
    }

    /**
     * Finds the coefficients to multiply each LineSegment with to reach the intercept point, if any exist.
     * @param target: The line segment to check for an intercept with.
     * @return double[thisCoefficient, -1 * targetCoefficient]
     */
    public double[] getIntersectionCoefficientsWith(LineSegment target){
        return VectorMath.findIntersectCoefficients(this, target);
    }

    public boolean isMirror(){
        return isMirror;
    }
    public void setMirror(){
        isMirror = true;
    }

    public void setColor(double r, double g, double b){
        color = new double[] {r,g,b};
    }
    public double[] getColor(){
        return color;
    }

    public Vector getScaledEndpoint(double factor){
        return startPoint.getVectorSum((direction.getScaledProduct(factor)));
    }

    public double getAngleMadeWith(LineSegment l){
        return direction.getAngleMadeWith(l.getDirection());
    }

    public String toString(){
        return "startPoint: " + startPoint + "\nendPoint: " + endPoint + "\ndirection: " + direction;
    }
}
