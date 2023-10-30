package vectors;

public class Vector {

    private double x;
    private double y;

    public Vector(double xDir, double yDir){
        this.x = xDir;
        this.y = yDir;
    }

    public Vector(double[] v){
        this(v[0], v[1]);
    }

    // ######################################## VECTOR PROPERTIES ######################################################

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMagnitude(){
        return Math.sqrt(x * x + y * y);
    }

    public double[] asCoordinateArray(){
        return new double[]{x, y};
    }

    //######################################## RETURN NEW VECTOR #######################################################

    public Vector getVectorSum (Vector v){
        double newX = this.x + v.getX();
        double newY = this.y + v.getY();
        return new Vector(newX, newY);
    }

    public Vector getVectorDifference(Vector v){
        double newX = this.x - v.getX();
        double newY = this.y - v.getY();
        return new Vector(newX, newY);
    }

    public Vector getScaledProduct(double c){
        return new Vector(this.x * c, this.y * c);
    }

    public Vector getTransformationWith(double[][] m){
        double transformedX = x * m[0][0] + x * m[1][0];
        double transformedY = y * m[0][1] + y * m[1][1];
        return new Vector(transformedX, transformedY);
    }

    //####################################### MODIFY THIS VECTOR #######################################################

    public void scaleSelf(double scalar){
        x = x * scalar;
        y = y * scalar;
    }

    public void addToSelf(Vector v){
        x = x + v.getX();
        y = y + v.getY();
    }

    public void subtractFromSelf(Vector v){
        x = x - v.getX();
        y = y - v.getY();
    }

    /**
     * x[a b][x]     [a]      [b]  [ax + by]
     * y[c d][y]  [x][c] + [y][d]  [cx + dy]
     * @param m Transformation Matrix
     */
    public void applyTransformationToSelf(Matrix2D m){
        double tX = x;
        double tY = y;
        x = tX * m.getX1() + tY * m.getX2();
        y = tX * m.getY1() + tY * m.getY2();
    }

    public void changeX(double amount){
        x += amount;
    }

    public void changeY(double amount){
        y += amount;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    // #################################### RETURN OTHER INFORMATION ###################################################

    public double getCrossProductWith(Vector v){
        return (x * v.getY()) - (y * v.getX());
    }
    public double getDotProductWith(Vector v){
        return x * v.getX() + y * v.getY();
    }
    public double getAngleMadeWith(Vector v){
        return Math.acos(
                this.getDotProductWith(v) / (this.getMagnitude() * v.getMagnitude())
        );
    }

    public String toString(){
        return "[" + x + "," + y + "]";
    }

    public static void main(String[] args) {
        Vector v1 = new Vector(1,0);
        Vector v2 = new Vector(0,1);
        System.out.println(v1.getDotProductWith(v2) + " " + Math.toDegrees(v1.getAngleMadeWith(v2)));
        System.out.println(v2.getDotProductWith(v1) + " " + Math.toDegrees(v2.getAngleMadeWith(v1)));
    }

}

