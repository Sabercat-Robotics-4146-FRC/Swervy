package org.frcteam2910.c2020.util;

import org.frcteam2910.common.control.*;
import org.frcteam2910.common.io.PathReader;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class AutonomousTrajectories {

    private static final double SAMPLE_DISTANCE = 0.1;

    private static final String TEN_BALL_AUTO_PART_ONE_NAME = "autos/10BallAuto/10BallAutoPart1.path";
    private static final String TEN_BALL_AUTO_PART_TWO_NAME = "autos/10BallAuto/10BallAutoPart2.path";

    private Trajectory eightBallAutoPartOne;
    private Trajectory eightBallAutoPartTwo;
    private Trajectory eightBallAutoPartThree;
    private Trajectory eightBallAutoPartFour;
    private Trajectory tenBallAutoPartOne;
    private Trajectory tenBallAutoPartTwo;

    private final Trajectory straightAuto;
    private final Trajectory autoPathOnePartOne;
    private final Trajectory autoPathOnePartTwo;
    private final Trajectory autoPathOnePartThree;

    private final Trajectory simpleShootThree;

    public AutonomousTrajectories(TrajectoryConstraint[] trajectoryConstraints) throws IOException {
        TrajectoryConstraint[] slowConstraints = Arrays.copyOf(trajectoryConstraints, trajectoryConstraints.length + 1);
        slowConstraints[slowConstraints.length - 1] = new MaxVelocityConstraint(6.0); // change this to lower speed
        slowConstraints[slowConstraints.length - 2] = new MaxAccelerationConstraint(1); // change this to lower acceleration

        eightBallAutoPartOne = new Trajectory(
                new SimplePathBuilder(new Vector2(509.0, -155.0), Rotation2.ZERO)
                        .lineTo(new Vector2(468.0, -67.34))
                        .build(),
                trajectoryConstraints, SAMPLE_DISTANCE
        );
        eightBallAutoPartTwo = new Trajectory(
                new SimplePathBuilder(new Vector2(468.0, -67.34), Rotation2.ZERO)
                        .lineTo(new Vector2(459.23, -111.87))
                        .arcTo(new Vector2(432.0, -134.25), new Vector2(432.0, -106.5))
                        .lineTo(new Vector2(324.0, -134.25), Rotation2.fromDegrees(0.0))
                        .build(),
                slowConstraints, SAMPLE_DISTANCE
        );
        eightBallAutoPartThree = new Trajectory(
                new SimplePathBuilder(new Vector2(324.0, -134.25), Rotation2.fromDegrees(0.0))
                        .arcTo(new Vector2(468.0, -67.34), new Vector2(324.0, 54.16))
                        .build(),
                trajectoryConstraints, SAMPLE_DISTANCE
        );
        eightBallAutoPartFour = new Trajectory(
                new SimplePathBuilder(new Vector2(468.0, -67.34), Rotation2.fromDegrees(0.0))
                        .arcTo(new Vector2(324, -134.25), new Vector2(324.0, 54.16))
                        .build(),
                trajectoryConstraints, SAMPLE_DISTANCE
        );
        tenBallAutoPartOne = new Trajectory(getPath(TEN_BALL_AUTO_PART_ONE_NAME), trajectoryConstraints, SAMPLE_DISTANCE);
        tenBallAutoPartTwo = new Trajectory(getPath(TEN_BALL_AUTO_PART_TWO_NAME), trajectoryConstraints, SAMPLE_DISTANCE);

        simpleShootThree = new Trajectory(
                new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                        .lineTo(new Vector2(40.0, 0.0))
                        .build(),
                trajectoryConstraints, SAMPLE_DISTANCE
        );

        
        straightAuto = new Trajectory(
                new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                    .lineTo(new Vector2(0.265, 0.0), Rotation2.ZERO)
                    .build(),
                slowConstraints, SAMPLE_DISTANCE
        );

        // follow
        autoPathOnePartOne = new Trajectory(
            new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO) // revert this
                    .lineTo(new Vector2(0.265, 0), Rotation2.ZERO)
                    .lineTo(new Vector2(0, 0.5), Rotation2.ZERO)
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot first ball

        // follow and intake
        autoPathOnePartTwo = new Trajectory(
            new SimplePathBuilder(new Vector2(-67.5, -127.5), Rotation2.fromDegrees(27.897))
                    .lineTo(new Vector2(-15, -158), Rotation2.fromDegrees(120.155))
                    .lineTo(new Vector2(-120, -105), Rotation2.fromDegrees(296.783))
                    .lineTo(new Vector2(-67.5, -127.5), Rotation2.fromDegrees(27.897))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot second and third balls

        // follow and intake
        autoPathOnePartThree = new Trajectory(
            new SimplePathBuilder(new Vector2(-67.5, -127.5), Rotation2.fromDegrees(27.897))
                    .lineTo(new Vector2(-300, -132), Rotation2.fromDegrees(91.109))
                    .lineTo(new Vector2(-67.5, -127.5), Rotation2.fromDegrees(27.897))
                    .build(),
            slowConstraints, SAMPLE_DISTANCE
        );

        // shoot fourth and fifth balls

    }

    private Path getPath(String name) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(name);
        if (in == null) {
            throw new FileNotFoundException("Path file not found: " + name);
        }

        try (PathReader reader = new PathReader(new InputStreamReader(in))) {
            return reader.read();
        }
    }

    public Trajectory getEightBallAutoPartOne() {
        return eightBallAutoPartOne;
    }

    public Trajectory getEightBallAutoPartTwo() {
        return eightBallAutoPartTwo;
    }

    public Trajectory getEightBallAutoPartThree() {
        return eightBallAutoPartThree;
    }

    public Trajectory getEightBallAutoPartFour() {
        return eightBallAutoPartFour;
    }

    public Trajectory getTenBallAutoPartOne() {
        return tenBallAutoPartOne;
    }

    public Trajectory getTenBallAutoPartTwo() {
        return tenBallAutoPartTwo;
    }

    public Trajectory getSimpleShootThree() {
        return simpleShootThree;
    }

    public Trajectory getStraightAuto() {
        return straightAuto;
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
