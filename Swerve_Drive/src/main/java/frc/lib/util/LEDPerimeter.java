/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package frc.lib.util;

public class LEDPerimeter {

  double frameWidth;
  double frameLength;
  int LEDPerMeter;
  int LEDNum;
  double frameAngle;
  double edgePointX;
  double edgePointY;
  double nearestCornerX;
  double nearestCornerY;
  double distanceCompleated;
  int region;
  boolean invert;
  double headingAngle;

  public LEDPerimeter(double frameWidth, double frameLength, int LEDPerMeter, boolean invert) {
    this.frameWidth = frameWidth;
    this.frameLength = frameLength;
    this.LEDPerMeter = LEDPerMeter;
    this.LEDNum = (int) Math.round(((4 * (frameLength + frameWidth)) / 39.87) * LEDPerMeter);
    this.frameAngle = Math.atan2(frameWidth / 2, frameLength / 2);
    this.invert = invert;
  }

  public int getLEDAmount() {
    return LEDNum;
  }

  private double remapAngle(double headingAngle) {
    return (Math.atan2(
        ((frameWidth / 2) / Math.sin(Math.PI / 4)) * Math.sin(headingAngle),
        ((frameLength / 2) / Math.cos(Math.PI / 4)) * Math.cos(headingAngle)));
  }

  private void setregion(double angle) {
    if (((angle > 0) && (angle <= Math.PI / 4))) {
      region = 1;
    } else if ((angle > Math.PI / 4) && (angle <= (Math.PI - Math.PI / 4))) {
      region = 2;
    } else if ((angle > (Math.PI - Math.PI / 4)) && (angle <= (Math.PI + Math.PI / 4))) {
      region = 3;
    } else if ((angle > (Math.PI + Math.PI / 4)) && (angle <= (2 * Math.PI - Math.PI / 4))) {
      region = 4;
    } else {
      region = 5;
    }
  }

  private void setEdgePoint(double headingAngle) {
    int xFactor = 0;
    int yFactor = 0;
    double tanTheta = Math.tan(remapAngle(headingAngle));
    switch (region) {
      case 1:
        yFactor = 1;
        xFactor = 1;
        break;
      case 2:
        yFactor = 1;
        xFactor = 1;
        break;
      case 3:
        xFactor = -1;
        yFactor = -1;
        break;
      case 4:
        xFactor = -1;
        yFactor = -1;
        break;
      case 5:
        yFactor = 1;
        xFactor = 1;
        break;
    }
    if ((region == 1) || (region == 5) || (region == 3)) {
      edgePointX = xFactor * (frameLength / 2); // "Z0"
      edgePointY = yFactor * (frameLength / 2) * tanTheta;
    } else {
      edgePointX = xFactor * (frameWidth / (2 * tanTheta)); // "Z1"
      edgePointY = yFactor * (frameWidth / 2);
    }
  }

  private void setNearestCorner() {
    switch (region) {
      case 1:
        nearestCornerX = frameLength / 2;
        nearestCornerY = 0;
        distanceCompleated = 0;
        break;
      case 2:
        distanceCompleated = frameWidth / 2;
        nearestCornerX = frameLength / 2;
        nearestCornerY = frameWidth / 2;
        break;
      case 3:
        distanceCompleated = frameLength + frameWidth / 2;
        nearestCornerX = -frameLength / 2;
        nearestCornerY = frameWidth / 2;
        break;
      case 4:
        distanceCompleated = frameLength + (3 * frameWidth) / 2;
        nearestCornerX = -frameLength / 2;
        nearestCornerY = -frameWidth / 2;
        break;
      case 5:
        nearestCornerX = frameLength / 2;
        nearestCornerY = -frameWidth / 2;
        distanceCompleated = (2 * frameLength) + ((3 * frameWidth) / 2);
    }
  }

  public double fetchDistanceTravled() {
    return distanceCompleated
        + Math.sqrt(
            Math.pow(nearestCornerX - edgePointX, 2) + Math.pow(nearestCornerY - edgePointY, 2));
  }

  public double getNearestPointX() {
    return nearestCornerX;
  }

  public double getNearestPointY() {
    return nearestCornerY;
  }

  public double getEdgePointX() {
    return edgePointX;
  }

  public double getEdgePointY() {
    return edgePointY;
  }

  public boolean testy() {
    return true;
  }

  public int getRegion() {
    return region;
  }

  public void invert() {
    invert = !invert;
  }

  public int getLEDID(double headingAngle) {
    headingAngle = headingAngle % 360;
    if (headingAngle < 0) {
      headingAngle += 360;
    }
    headingAngle = Math.toRadians(headingAngle);
    setregion(headingAngle);
    setEdgePoint(headingAngle);
    setNearestCorner();

    int ledID =
        (int) Math.round(((LEDNum / (2 * (frameLength + frameWidth))) * fetchDistanceTravled()));

    if (!invert) {
      return ledID;
    } else {
      return LEDNum - ledID;
    }
  }
}
