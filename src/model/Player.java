package model;
import vectors.*;

public class Player {
    private final Vector playerPosition;
    private final Vector playerDirection;
    private final Vector leftWing;
    private final Vector rightWing;
    private int positionX;
    private int positionY;
    private double currentAngle;

    public Player(Vector position, double scalingFactor, double angle){
        this.currentAngle = angle;
        this.playerPosition = position;
        this.playerDirection = VectorMath.generateUnitVectorFromAngle(currentAngle).getScaledProduct(scalingFactor);
        this.leftWing = VectorMath.generateUnitVectorFromAngle(120).getScaledProduct(scalingFactor);
        this.rightWing = VectorMath.generateUnitVectorFromAngle(240).getScaledProduct(scalingFactor);

        this.positionX = (int) playerPosition.getX();
        this.positionY = (int) playerPosition.getY();
    }

    public int[] getXArray(){
        double finalPlayerDirection = playerDirection.getX() + playerPosition.getX();
        double finalLeftWing = leftWing.getX() + playerPosition.getX();
        double finalRightWing = rightWing.getX() + playerPosition.getX();

        return new int[] {(int) finalPlayerDirection,
                (int) finalLeftWing,
                (int) playerPosition.getX(),
                (int) finalRightWing};
    }

    public int[] getYArray(){
        double finalPlayerDirection = playerDirection.getY() + playerPosition.getY();
        double finalLeftWing = leftWing.getY() + playerPosition.getY();
        double finalRightWing = rightWing.getY() + playerPosition.getY();

        return new int[] {(int) finalPlayerDirection,
                (int) finalLeftWing,
                (int) playerPosition.getY(),
                (int) finalRightWing};
    }

    public void rotateSelf(double angleInDegrees){
        Matrix2D rotationMatrix = VectorMath.generateRotationMatrixFromAngle(angleInDegrees);

        // rotate Vectors
        playerDirection.applyTransformationToSelf(rotationMatrix);
        leftWing.applyTransformationToSelf(rotationMatrix);
        rightWing.applyTransformationToSelf(rotationMatrix);

        currentAngle = (currentAngle + angleInDegrees) % 360;
    }

    public void advance(double distance){
        playerPosition.addToSelf(playerDirection.getScaledProduct(distance / playerDirection.getMagnitude()));
    }

    public void strafe(double distance){

        Vector strafeVector = VectorMath.generateUnitVectorFromAngle(currentAngle + 90);
        if (distance < 0){
            strafeVector.getScaledProduct(-1);
        }
        playerPosition.addToSelf(strafeVector.getScaledProduct(distance));
    }

    public double getCurrentAngle(){
        return currentAngle;
    }

    public Vector getPosition(){
        return playerPosition;
    }

}
