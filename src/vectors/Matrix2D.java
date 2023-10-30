package vectors;

public class Matrix2D {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Matrix2D(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Matrix2D(Vector v1, Vector v2){
        this.x1 = v1.getX();
        this.y1 = v1.getY();
        this.x2 = v2.getX();
        this.y2 = v2.getY();
    }

    public void invertSelf(){
        double crossProduct = VectorMath.crossProduct2D(x1, y1, x2, y2);

        if (crossProduct == 0){
            System.out.println("Matrix cannot be inverted");
            return;
        }

        double newX1 = y2 / crossProduct;
        double newY1 = -y1 / crossProduct;
        double newX2 = -x2 / crossProduct;
        double newY2 = x1 / crossProduct;

        x1 = newX1;
        y1 = newY1;
        x2 = newX2;
        y2 = newY2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public String toString(){
        return "x: " + x1 + " " + x2 + "\ny: " + y1 + " " + y2;
    }

    public static void main(String[] args) {
        Matrix2D m = new Matrix2D(5, -7, 2, -3);
        System.out.println(m);
        m.invertSelf();
        System.out.println(m);
    }
}
