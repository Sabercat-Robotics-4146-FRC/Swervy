package frc.robot.util;

import java.util.ArrayList;

import frc.common.math.Rotation2;
import frc.common.math.Vector2;

public class Paths {
    // all points for auto path one, assuming blue and bottom left

    public static Vector2 pointToVector(double[] point) {
        return new Vector2(point[0], point[1]);
    }

    public static ArrayList<Vector2> adjustInitialPositionAndRotation(ArrayList<Vector2> vectors, double[] newInitialPosition) {
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

    public static Rotation2 getRotationToCenter(Vector2 vector, boolean faceCenter) { // assumes robot gyroscope is zeroed facing positive y axis
        Vector2 posYVector = new Vector2(0, 1);
        Rotation2 rotation = Vector2.getAngleBetween(posYVector, vector);

        if (faceCenter) {
            rotation.rotateBy(new Rotation2(-1, 0, true));
        }

        return rotation;
    }

    public static double[] v0 = {-28, -70};
    public static double[] v1 = {-67.5, -127.5};
    public static double[] v2 = {-15, -158};
    public static double[] v3 = {-120, -105};
    public static double[] v4 = {-300, -132};

    public static ArrayList<Vector2> initPathOne() {
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        vectors.add(pointToVector(v0));
        vectors.add(pointToVector(v1));
        vectors.add(pointToVector(v2));
        vectors.add(pointToVector(v3));
        vectors.add(pointToVector(v1));
        vectors.add(pointToVector(v4));
        vectors.add(pointToVector(v1));

        return vectors;
    }

    public static double[] u0 = {0, 0};
    public static double[] u1 = {-60, 0};

    public static ArrayList<Vector2> initStraightBack() {
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        vectors.add(pointToVector(u0));
        vectors.add(pointToVector(u1));

        return vectors;
    }

}