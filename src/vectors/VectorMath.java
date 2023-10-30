package vectors;

import java.util.Arrays;

public class VectorMath {
    private VectorMath(){}

    // LINE SEGMENT INTERSECTS

    /**
     * Finds both scaling coefficients needed to multiply line segments AB and CD with to have them intersect on the
     * same point. If t is between 1 and 0, then the intersect point lies on that line segment. If t < 0 or t > 1,
     * the intersect lies outside the line segment, depending on where it's facing.
     * a:  line AB start point
     * ab: Line AB segment vector (from a to b)
     * c:  line CD start point
     * cd: Line CD segment
     * a + ab * t1 = c + cd * t2 where t is a scaling factor
     * @param AB The first line segment
     * @param CD The second line segment
     * @return [t1, t2] such that t1 * AB = -t2 * CD. null if lines are parallel with no intersect.
     */
    public static double[] findIntersectCoefficients(LineSegment AB, LineSegment CD){
        double[] coefficients;

        Vector a = AB.getStartPoint();
        Vector ab = AB.getDirection();

        Vector c = CD.getStartPoint();
        Vector cd = CD.getDirection();

        Vector ac = c.getVectorDifference(a); // Vector from a to c


        //System.out.println("a: " + a + "\nab:" + ab + "\nc: " + c + "\ncd: " + cd + "\nac: " + ac);

        // co-linear and parallel lines have no intersect
        double crossProductS1S2 = crossProduct2D(ab, cd); // Used for inverse Matrix

        //System.out.println("ab x cd: " + crossProductS1S2);

        if (crossProductS1S2 == 0){
            // there will be no intersect if both slopes are equal,
            // happens when cross product is zero
            return null;
        }

        double[][] matrix = new double[][]{ab.asCoordinateArray(),cd.asCoordinateArray()};
        //System.out.println("Original Matrix:");
        //printMatrix(matrix);

        double[][] inverseMatrix = invertMatrix2D(matrix);
        //System.out.println("Inverse Matrix:");
        //printMatrix(inverseMatrix);

        coefficients = multiplyMatrixWithVector2D(inverseMatrix, ac.asCoordinateArray());

        return coefficients;
    }

    public static Vector getIntersectPointGivenCoefficients(LineSegment s1, double coefficient){
        Vector abt = s1.getDirection().getScaledProduct(coefficient);
        abt = abt.getVectorSum(s1.getStartPoint());

        return abt;
    }

    // CROSS PRODUCT

    public static double crossProduct2D(double v1X, double v1Y, double v2X, double v2Y){
        return (v1X * v2Y) - (v1Y * v2X);
    }

    public static double crossProduct2D(double[] v1, double[] v2){
        return crossProduct2D(v1[0], v1[1], v2[0], v2[1]);
    }

    public static double crossProduct2D(Vector v1, Vector v2){
        return crossProduct2D(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }

    // VECTOR ARITHMETIC

    public static double[] addVectors2D(double[] v1, double[] v2){
        return new double[] {
                v1[0] + v2[0],
                v1[1] + v2[1]
        };
    }

    public static double[][] invertMatrix2D(double[][] m){
        return invertMatrix2D(m[0], m[1]);
    }

    public static double[][] invertMatrix2D(double[] v1, double[] v2){
        double crossProduct = crossProduct2D(v1[0], v1[1], v2[0], v2[1]);
        return new double[][] {
                new double[] {v2[1] / crossProduct, -v1[1] / crossProduct},
                new double[] {-v2[0] / crossProduct, v1[0] / crossProduct}
        };
    }

    public static double[] multiplyMatrixWithVector2D(double[][] m, double[] v){
        return new double[]{
                m[0][0] * v[0] + m[1][0] * v[1],
                m[0][1] * v[0] + m[1][1] * v[1]
        };
    }

    private static void printMatrix(double[][] m){
        System.out.println("i: " + m[0][0] + " " + m[1][0] + "\nj: " + m[0][1] + " " + m[1][1]);
    }

    private static boolean slopeMatch(Vector v1, Vector v2){
        return (v1.getX() == v2.getX()) && (v1.getY() == v2.getY());
    }

    public static Matrix2D generateRotationMatrixFromAngle(double angleInDegrees){
        double angleInRadians = Math.toRadians(angleInDegrees);
        /*
        x: cos(theta)  -sin(theta)
        y: sin(theta)  cos(theta)
         */
        double cosTheta = Math.cos(angleInRadians);
        double sinTheta = Math.sin(angleInRadians);

        return new Matrix2D(cosTheta, -sinTheta, sinTheta, cosTheta);
    }

    public static Vector generateUnitVectorFromAngle(double angleInDegrees){
        double angleInRadians = Math.toRadians(angleInDegrees);
        return new Vector(Math.sin(angleInRadians), Math.cos(angleInRadians));
    }

    public static void main(String[] args) {
//        LineSegment l1 = new LineSegment(new Vector(50,150), new Vector(336,278));
//        LineSegment l2 = new LineSegment(new Vector(50,250), new Vector(200,100));
//
//        double[] coefficients = findIntersectCoefficients(l1,l2);
//        System.out.println("Coefficients: \n" + Arrays.toString(coefficients) + "\n");
//        System.out.println(getIntersectPointGivenCoefficients(l1,coefficients[0]));
//        System.out.println(getIntersectPointGivenCoefficients(l2,-coefficients[1]));
//
//        double[][] m = new double[][]{
//                new double[]{2,6},
//                new double[]{4,8}
//        };
//
//        Vector v = new Vector(-1, 7);
//        printMatrix(m);
//        System.out.println(v);
//        System.out.println(Arrays.toString(multiplyMatrixWithVector2D(m, v.asCoordinateArray())));

        System.out.println(generateUnitVectorFromAngle(75));
        System.out.println(generateUnitVectorFromAngle(89));
        System.out.println(generateUnitVectorFromAngle(90));
        System.out.println(generateUnitVectorFromAngle(91));
        System.out.println(generateUnitVectorFromAngle(105));


    }
}
