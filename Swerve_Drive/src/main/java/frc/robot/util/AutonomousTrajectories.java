package frc.robot.util;

import frc.common.control.*;
import frc.common.control.Trajectory;
import frc.common.math.Rotation2;
import frc.common.math.Vector2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AutonomousTrajectories {

  private static final double SAMPLE_DISTANCE = 0.1;

  private final Trajectory straightBackAuto;
  private final Trajectory complexAutoBlueTwoPartOne;
  private final Trajectory complexAutoBlueTwoPartTwo;
  private final Trajectory complexAutoBlueTwoPartThree;
  private final Trajectory complexAutoRedTwoPartOne;
  private final Trajectory complexAutoRedTwoPartTwo;
  private final Trajectory complexAutoRedTwoPartThree;
  private final Trajectory complexAutoBlueOnePartOne;
  private final Trajectory complexAutoBlueOnePartTwo;
  private final Trajectory complexAutoRedOnePartOne;
  private final Trajectory complexAutoRedOnePartTwo;

  public static ArrayList<Vector2> straightBackVectors;
  public static ArrayList<Vector2> complexBlueTwoVectors;
  public static ArrayList<Vector2> complexRedTwoVectors;
  public static ArrayList<Vector2> complexBlueOneVectors;
  public static ArrayList<Vector2> complexRedOneVectors;

  public AutonomousTrajectories(TrajectoryConstraint[] trajectoryConstraints) throws IOException {
    TrajectoryConstraint[] slowConstraints =
        Arrays.copyOf(trajectoryConstraints, trajectoryConstraints.length + 1);
    slowConstraints[slowConstraints.length - 1] =
        new MaxVelocityConstraint(6.0); // change this to lower speed
    slowConstraints[slowConstraints.length - 2] =
        new MaxAccelerationConstraint(1); // change this to lower acceleration

    straightBackVectors = Paths.initVectors(Paths.straightBackPoints);
    straightBackVectors = Paths.scaleVectors(straightBackVectors, 1); // scale all vectors
    // note: will not need to adjust initial position of straight back path b/c defined relative to
    // original point, e.g., (0,0) -> (-60, 0)

    straightBackAuto =
        new Trajectory(
            new SimplePathBuilder(straightBackVectors.get(0), Rotation2.ZERO)
                .lineTo(straightBackVectors.get(1), Rotation2.ZERO)
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexBlueTwoVectors = Paths.initVectors(Paths.complexPointsTwoCargo);
    complexBlueTwoVectors = Paths.scaleVectors(complexBlueTwoVectors, 1); // scale all vectors
    complexBlueTwoVectors =
        Paths.adjustInitialPosition(
            complexBlueTwoVectors, new double[] {-28, -70}); // adjust starting position

    complexAutoBlueTwoPartOne =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueTwoVectors.get(0),
                    Rotation2.ZERO) // assuming that robot starts facing center
                .lineTo(
                    complexBlueTwoVectors.get(1),
                    Rotation2.fromDegrees(
                        171 - complexBlueTwoVectors.get(0).getAngleToCenter().toDegrees()))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoBlueTwoPartTwo =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueTwoVectors.get(1),
                    Rotation2.ZERO) // assuming that robot starts facing center
                .lineTo(complexBlueTwoVectors.get(2), Rotation2.fromDegrees(67))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoBlueTwoPartThree =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueTwoVectors.get(2),
                    Rotation2.ZERO) // assuming that robot starts facing center
                .lineTo(complexBlueTwoVectors.get(3), Rotation2.fromDegrees(153))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexRedTwoVectors = Paths.rotate180(complexBlueTwoVectors);

    complexAutoRedTwoPartOne =
        new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(0), Rotation2.ZERO)
                .lineTo(
                    complexRedTwoVectors.get(1),
                    Rotation2.fromDegrees(
                        171 - complexBlueTwoVectors.get(0).getAngleToCenter().toDegrees()))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoRedTwoPartTwo =
        new Trajectory(
            new SimplePathBuilder(
                    complexRedTwoVectors.get(1), complexRedTwoVectors.get(1).getAngleToCenter())
                .lineTo(complexRedTwoVectors.get(2), Rotation2.fromDegrees(61.5))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoRedTwoPartThree =
        new Trajectory(
            new SimplePathBuilder(
                    complexRedTwoVectors.get(2), complexRedTwoVectors.get(2).getAngleToCenter())
                .lineTo(complexRedTwoVectors.get(3), Rotation2.fromDegrees(101.6))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexBlueOneVectors = Paths.initVectors(Paths.complexPointsOneCargo);
    complexBlueOneVectors = Paths.scaleVectors(complexBlueOneVectors, 1); // scale all vectors
    complexBlueOneVectors =
        Paths.adjustInitialPosition(
            complexBlueOneVectors, new double[] {-28, 70}); // adjust starting position

    complexAutoBlueOnePartOne =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueOneVectors.get(0), complexBlueOneVectors.get(0).getAngleToCenter())
                .lineTo(complexBlueOneVectors.get(1), Rotation2.fromDegrees(38.5505))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoBlueOnePartTwo =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueOneVectors.get(1), complexBlueOneVectors.get(1).getAngleToCenter())
                .lineTo(complexBlueOneVectors.get(2), Rotation2.fromDegrees(103.5))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexRedOneVectors = Paths.rotate180(complexBlueOneVectors);

    complexAutoRedOnePartOne =
        new Trajectory(
            new SimplePathBuilder(
                    complexBlueOneVectors.get(0), complexBlueOneVectors.get(0).getAngleToCenter())
                .lineTo(complexBlueOneVectors.get(1), Rotation2.fromDegrees(38.5505))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);

    complexAutoRedOnePartTwo =
        new Trajectory(
            new SimplePathBuilder(
                    complexRedOneVectors.get(1), complexRedOneVectors.get(1).getAngleToCenter())
                .lineTo(complexRedOneVectors.get(2), Rotation2.fromDegrees(103.5))
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);
  }

  public Trajectory getStraightBackAuto() {
    return straightBackAuto;
  }

  public Trajectory getComplexAutoBlueTwoPartOne() {
    return complexAutoBlueTwoPartOne;
  }

  public Trajectory getComplexAutoBlueTwoPartTwo() {
    return complexAutoBlueTwoPartTwo;
  }

  public Trajectory getComplexAutoBlueTwoPartThree() {
    return complexAutoBlueTwoPartThree;
  }

  public Trajectory getComplexAutoRedTwoPartOne() {
    return complexAutoRedTwoPartOne;
  }

  public Trajectory getComplexAutoRedTwoPartTwo() {
    return complexAutoRedTwoPartTwo;
  }

  public Trajectory getComplexAutoRedTwoPartThree() {
    return complexAutoRedTwoPartThree;
  }

  public Trajectory getComplexAutoBlueOnePartOne() {
    return complexAutoBlueOnePartOne;
  }

  public Trajectory getComplexAutoBlueOnePartTwo() {
    return complexAutoBlueOnePartTwo;
  }

  public Trajectory getComplexAutoRedOnePartOne() {
    return complexAutoRedOnePartOne;
  }

  public Trajectory getComplexAutoRedOnePartTwo() {
    return complexAutoRedOnePartTwo;
  }
}
