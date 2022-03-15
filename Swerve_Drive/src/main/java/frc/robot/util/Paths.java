package frc.robot.util;

import frc.common.math.Vector2;
import java.util.ArrayList;

public class Paths {

  // all points for straight back paths -- stay the same, independent of alliance and # cargo
  public static double[] u0 = {0, 0};
  public static double[] u1 = {-60, 0};

  public static double[][] straightBackPoints = {u0, u1};

  // all points for complex path w/ blue & two cargo
  public static double[] v0 = {-28, -70}; // initial position
  public static double[] v1 = {-15, -152}; // intake one ball, shoot two balls
  public static double[] v2 = {-120, -95}; // intake one ball, shoot one balls
  public static double[] v3 = {-300, -132}; // intake two balls

  public static double[][] complexPointsTwoCargo = {v0, v1, v2, v3};

  // all points for complex path 2/ blue & one cargo
  public static double[] w0 = {-28, 70}; // initial position
  public static double[] w1 = {-121, 92}; // intake one ball, shoot two balls
  public static double[] w2 = v3; // intake two balls

  public static double[][] complexPointsOneCargo = {w0, w1, w2};

  public static Vector2 pointToVector(double[] point) {
    return new Vector2(point[0], point[1]);
  }

  public static ArrayList<Vector2> initVectors(double[][] listOfPoints) {
    ArrayList<Vector2> vectors = new ArrayList<Vector2>();
    for (int i = 0; i < listOfPoints.length; i++) {
      vectors.set(i, pointToVector(listOfPoints[i]));
    }
    return vectors;
  }

  public static ArrayList<Vector2> adjustInitialPosition(
      ArrayList<Vector2> vectors, double[] newInitialPosition) {
    Vector2 newInitialVector = pointToVector(newInitialPosition);
    vectors.set(0, newInitialVector); // only adjusts initial position, not following positions

    return vectors;
  }

  public static ArrayList<Vector2> scaleVectors(ArrayList<Vector2> vectors, int scalar) {
    for (int i = 0; i < vectors.size(); i++) {
      vectors.set(i, vectors.get(i).scale(scalar));
    }
    return vectors;
  }

  public static ArrayList<Vector2> rotate180(ArrayList<Vector2> vectors) {
    for (int i = 0; i < vectors.size(); i++) {
      vectors.set(i, vectors.get(i).inverse());
    }
    return vectors;
  }

  public static double findDegree(Vector2 v1, Vector2 v2){
    double p1Mag = Math.sqrt(Math.pow(v1.x,2) + Math.pow(v1.y,2));
    double p2Mag = Math.sqrt(Math.pow(v2.x,2) + Math.pow(v2.y,2));

    double dotProduct = (v1.x*v2.x) + (v1.y*v2.y);

    return Math.acos((dotProduct)/(p1Mag*p2Mag));
  }
}