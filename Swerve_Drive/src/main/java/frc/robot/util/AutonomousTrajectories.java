package frc.robot.util;

import frc.common.control.*;
import frc.common.math.Rotation2;
import frc.common.math.Vector2;
import frc.robot.util.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AutonomousTrajectories {

    private static final double SAMPLE_DISTANCE = 0.1;

    private final Trajectory straightBackAuto;
    private final Trajectory autoPathOnePartOne;
    private final Trajectory autoPathOnePartTwo;
    private final Trajectory autoPathOnePartThree;

    public static ArrayList<Vector2> straightBackVectors;
    public static ArrayList<Vector2> pathOneVectors;


    public AutonomousTrajectories(TrajectoryConstraint[] trajectoryConstraints) throws IOException {
        TrajectoryConstraint[] slowConstraints = Arrays.copyOf(trajectoryConstraints, trajectoryConstraints.length + 1);
        slowConstraints[slowConstraints.length - 1] = new MaxVelocityConstraint(6.0); // change this to lower speed
        slowConstraints[slowConstraints.length - 2] = new MaxAccelerationConstraint(1); // change this to lower acceleration

        straightBackVectors = Paths.initStraightBack();
        pathOneVectors = Paths.initPathOne();
        /*ArrayList<Vector2>[] vectorsList = {straightBackVectors, pathOneVectors};
        for (int i=0; i<vectorsList.length; i++) {
            vectors = vectorsList[i];
            // vectors = Paths.scaleVectors(vectors, .1); // scale all vectors to match field size
            // vectors = Paths.adjustInitialPosition(vectors, new double[] {0, 0}); // adjust starting location so all following points match
        }*/


        straightBackAuto = new Trajectory(
                new SimplePathBuilder(straightBackVectors.get(0), Rotation2.ZERO)
                    .lineTo(straightBackVectors.get(1), Rotation2.ZERO)
                    .build(),
                slowConstraints, SAMPLE_DISTANCE
        );

        // follow
        autoPathOnePartOne = new Trajectory(
            new SimplePathBuilder(pathOneVectors.get(0), Rotation2.ZERO) // revert this
                    .lineTo(pathOneVectors.get(1), Rotation2.ZERO)
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot first ball

        // follow and intake
        autoPathOnePartTwo = new Trajectory(
            new SimplePathBuilder(pathOneVectors.get(1), Rotation2.fromDegrees(27.897))
                    .lineTo(pathOneVectors.get(2), Rotation2.fromDegrees(120.155))
                    .lineTo(pathOneVectors.get(3), Rotation2.fromDegrees(296.783))
                    .lineTo(pathOneVectors.get(4), Rotation2.fromDegrees(27.897))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot second and third balls

        // follow and intake
        autoPathOnePartThree = new Trajectory(
            new SimplePathBuilder(pathOneVectors.get(4), Rotation2.fromDegrees(27.897))
                    .lineTo(pathOneVectors.get(5), Rotation2.fromDegrees(91.109))
                    .lineTo(pathOneVectors.get(6), Rotation2.fromDegrees(27.897))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot fourth and fifth balls

    }


    public Trajectory getStraightBackAuto() {
        return straightBackAuto;
    }

    public Trajectory getAutoPathOnePartOne() {
        return autoPathOnePartOne;
    }

    public Trajectory getAutoPathOnePartTwo() {
        return autoPathOnePartTwo;
    }

    public Trajectory getAutoPathOnePartThree() {
        return autoPathOnePartThree;
    }
}
