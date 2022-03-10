package frc.robot.util;

import frc.common.control.*;
import frc.common.math.Rotation2;
import frc.common.math.Vector2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import frc.common.control.Trajectory;

public class AutonomousTrajectories {

    private static final double SAMPLE_DISTANCE = 0.1;

    private final Trajectory straightBackAuto;
    private final Trajectory complexAutoBlueTwoPartOne;
    private final Trajectory complexAutoBlueTwoPartTwo;
    private final Trajectory complexAutoBlueTwoPartThree;
    private final Trajectory complexAutoRedTwoPartOne;
    private final Trajectory complexAutoRedTwoPartTwo;
    private final Trajectory complexAutoRedTwoPartThree;

    public static ArrayList<Vector2> straightBackVectors;
    public static ArrayList<Vector2> complexBlueTwoVectors;
    public static ArrayList<Vector2> complexRedTwoVectors;


    public AutonomousTrajectories(TrajectoryConstraint[] trajectoryConstraints) throws IOException {
        TrajectoryConstraint[] slowConstraints = Arrays.copyOf(trajectoryConstraints, trajectoryConstraints.length + 1);
        slowConstraints[slowConstraints.length - 1] = new MaxVelocityConstraint(6.0); // change this to lower speed
        slowConstraints[slowConstraints.length - 2] = new MaxAccelerationConstraint(1); // change this to lower acceleration

        straightBackVectors = Paths.initVectors(Paths.straightBackPoints);
        straightBackVectors = Paths.scaleVectors(straightBackVectors, 1); // scale all vectors
        // note: will not need to adjust initial position of straight back path b/c defined relative to original point, e.g., (0,0) -> (-60, 0)

        straightBackAuto = new Trajectory(
                new SimplePathBuilder(straightBackVectors.get(0), Rotation2.ZERO)
                    .lineTo(straightBackVectors.get(1), Rotation2.ZERO)
                    .build(),
                slowConstraints, SAMPLE_DISTANCE
        );



        complexBlueTwoVectors = Paths.initVectors(Paths.complexPointsTwoCargo);
        Paths.scaleVectors(complexBlueTwoVectors, 1); // scale all vectors
        Paths.adjustInitialPosition(complexBlueTwoVectors, new double[] {-28, -70}); // adjust starting position

        // follow
        complexAutoBlueTwoPartOne = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(0), Rotation2.fromDegrees(21.801))
                    .lineTo(complexBlueTwoVectors.get(1), complexBlueTwoVectors.get(1).getAngleToCenter()) // rotation should evaluate to 27.897 deg
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot first ball

        // follow and intake
        complexAutoBlueTwoPartTwo = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(1), complexBlueTwoVectors.get(1).getAngleToCenter())
                    .lineTo(complexBlueTwoVectors.get(2), Rotation2.fromDegrees(120.155))
                    .lineTo(complexBlueTwoVectors.get(3), Rotation2.fromDegrees(296.783))
                    .lineTo(complexBlueTwoVectors.get(4), complexBlueTwoVectors.get(4).getAngleToCenter())
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot second and third balls

        // follow and intake
        complexAutoBlueTwoPartThree = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(4), complexBlueTwoVectors.get(4).getAngleToCenter())
                    .lineTo(complexBlueTwoVectors.get(5), Rotation2.fromDegrees(91.109))
                    // pause for a few seconds?
                    .lineTo(complexBlueTwoVectors.get(6), complexBlueTwoVectors.get(4).getAngleToCenter())
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot fourth and fifth balls




        complexRedTwoVectors = Paths.rotate180(complexBlueTwoVectors);

        // follow
        complexAutoRedTwoPartOne = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(0), complexRedTwoVectors.get(0).getAngleAwayCenter())
                    .lineTo(complexRedTwoVectors.get(1), complexRedTwoVectors.get(1).getAngleToCenter())
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot first ball

        // follow and intake
        complexAutoRedTwoPartTwo = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(1), complexRedTwoVectors.get(1).getAngleToCenter())
                    .lineTo(complexRedTwoVectors.get(2), Rotation2.fromDegrees(120.155)) // FIXME
                    .lineTo(complexRedTwoVectors.get(3), Rotation2.fromDegrees(296.783)) // FIXME
                    .lineTo(complexRedTwoVectors.get(4), complexRedTwoVectors.get(4).getAngleToCenter())
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot second and third balls

        // follow and intake
        complexAutoRedTwoPartThree = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(4), complexRedTwoVectors.get(4).getAngleToCenter())
                    .lineTo(complexRedTwoVectors.get(5), Rotation2.fromDegrees(91.109)) // FIXME
                    // pause for a few seconds?
                    .lineTo(complexRedTwoVectors.get(6), complexRedTwoVectors.get(4).getAngleToCenter())
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot fourth and fifth balls

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

    public Trajectory getComplexAutoBlueThreePartTwo() {
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
}
