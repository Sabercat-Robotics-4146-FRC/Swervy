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
    private final Trajectory complexAutoBlueOnePartOne;
    private final Trajectory complexAutoBlueOnePartTwo;

    public static ArrayList<Vector2> straightBackVectors;
    public static ArrayList<Vector2> complexBlueTwoVectors;
    public static ArrayList<Vector2> complexRedTwoVectors;
    public static ArrayList<Vector2> complexBlueOneVectors;
    public static ArrayList<Vector2> complexRedOneVectors;


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
        complexBlueTwoVectors = Paths.scaleVectors(complexBlueTwoVectors, 1); // scale all vectors
        complexBlueTwoVectors = Paths.adjustInitialPosition(complexBlueTwoVectors, new double[] {-28, -70}); // adjust starting position

        // follow
        complexAutoBlueTwoPartOne = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(0), complexBlueTwoVectors.get(0).getAngleToCenter())
                    .lineTo(complexBlueTwoVectors.get(1), Rotation2.fromDegrees(-171))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake second ball
        // rotate to face center
        // shoot first, second ball

        // follow
        complexAutoBlueTwoPartTwo = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(1), complexBlueTwoVectors.get(1).getAngleToCenter())
                    .lineTo(complexBlueTwoVectors.get(2), Rotation2.fromDegrees(61.5))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake third ball
        // rotate to face center
        // shoot third ball

        // follow
        complexAutoBlueTwoPartThree = new Trajectory(
            new SimplePathBuilder(complexBlueTwoVectors.get(2), complexBlueTwoVectors.get(2).getAngleToCenter())
                    .lineTo(complexBlueTwoVectors.get(3), Rotation2.fromDegrees(101.6))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot fourth and fifth balls




        complexRedTwoVectors = Paths.rotate180(complexBlueTwoVectors);

        // follow
        complexAutoRedTwoPartOne = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(0), complexRedTwoVectors.get(0).getAngleToCenter())
                    .lineTo(complexRedTwoVectors.get(1), Rotation2.fromDegrees(1)) // FIXME
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake second ball
        // rotate to face center
        // shoot first and second balls

        complexAutoRedTwoPartTwo = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(1), complexRedTwoVectors.get(1).getAngleToCenter())
                    .lineTo(complexRedTwoVectors.get(2), Rotation2.fromDegrees(1)) // FIXME
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake third ball
        // rotate to face center
        // shoot third ball

        complexAutoRedTwoPartThree = new Trajectory(
            new SimplePathBuilder(complexRedTwoVectors.get(2), complexRedTwoVectors.get(2).getAngleToCenter())
                    .lineTo(complexRedTwoVectors.get(3), Rotation2.fromDegrees(1)) // FIXME
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake fourth and fifth balls
        // ???
        




        complexBlueOneVectors = Paths.initVectors(Paths.complexPointsOneCargo);
        complexBlueOneVectors = Paths.scaleVectors(complexBlueOneVectors, 1); // scale all vectors
        complexBlueOneVectors = Paths.adjustInitialPosition(complexBlueOneVectors, new double[] {-28, 70}); // adjust starting position

        // follow
        complexAutoBlueOnePartOne = new Trajectory(
            new SimplePathBuilder(complexBlueOneVectors.get(0), complexBlueOneVectors.get(0).getAngleToCenter())
                    .lineTo(complexBlueOneVectors.get(1), Rotation2.fromDegrees(38.5505))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake
        // rotate to face center
        // shoot first and second balls

        // follow
        complexAutoBlueOnePartTwo = new Trajectory(
            new SimplePathBuilder(complexBlueOneVectors.get(1), complexBlueOneVectors.get(1).getAngleToCenter())
                    .lineTo(complexBlueOneVectors.get(2), Rotation2.fromDegrees(103.5))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // intake third and fourth balls
        // ???




        complexRedOneVectors = Paths.rotate180(complexBlueOneVectors);

        // FIXME add complex red vectors one paths

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

    public Trajectory getComplexAutoBlueOnePartOne() {
        return complexAutoBlueOnePartOne;
    }

    public Trajectory getComplexAutoBlueOnePartTwo() {
        return complexAutoBlueOnePartTwo;
    }

    // FIXME add get methods for all other autos

}