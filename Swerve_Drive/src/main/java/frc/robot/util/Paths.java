package frc.robot.util;

import java.util.ArrayList;

import frc.common.math.Rotation2;
import frc.common.math.Vector2;

public class Paths {

    // all points for straight back paths -- stay the same, independent of alliance and # cargo
    public static double[] u0 = {0, 0};
    public static double[] u1 = {-60, 0};

    public static double[][] straightBackPoints = {u0, u1};

    // all points for complex path w/ blue & two cargo
    public static double[] v0 = {-28, -70};
    public static double[] v1 = {-67.5, -127.5};
    public static double[] v2 = {-15, -158};
    public static double[] v3 = {-120, -105};
    public static double[] v4 = {-300, -132};

    public static double[][] complexPointsTwoCargo = {v0, v1, v2, v3, v4};


    public static Vector2 pointToVector(double[] point) {
        return new Vector2(point[0], point[1]);
    }

    public static ArrayList<Vector2> initVectors(double[][] listOfPoints) {
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        for (int i=0; i<listOfPoints.length; i++) {
            vectors.set(i, pointToVector(listOfPoints[i]));
        }
        return vectors;
    }

    public static ArrayList<Vector2> adjustInitialPosition(ArrayList<Vector2> vectors, double[] newInitialPosition) {
        Vector2 newInitialVector = pointToVector(newInitialPosition);
        vectors.set(0, newInitialVector); // only adjusts initial position, not following positions

        return vectors;
    }

    public static ArrayList<Vector2> scaleVectors(ArrayList<Vector2> vectors, int scalar) {
        for (int i=0; i<vectors.size(); i++) {
            vectors.set(i, vectors.get(i).scale(scalar));
        }
        return vectors;
    }

    public static ArrayList<Vector2> rotate180(ArrayList<Vector2> vectors) {
        for (int i=0; i<vectors.size(); i++) {
            vectors.set(i, vectors.get(i).rotateBy(Rotation2.fromDegrees(180)));
        }
        return vectors;
    }

}