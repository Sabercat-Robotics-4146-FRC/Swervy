package org.frcteam2910.c2020.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam2910.c2020.RobotContainer;
import org.frcteam2910.c2020.commands.FollowTrajectoryCommand;
import org.frcteam2910.common.control.Trajectory;
import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Rotation2;

public class AutonomousChooser {
    private final AutonomousTrajectories trajectories;

    private SendableChooser<AutonomousMode> autonomousModeChooser = new SendableChooser<>();

    public AutonomousChooser(AutonomousTrajectories trajectories) {
        this.trajectories = trajectories;

        autonomousModeChooser.setDefaultOption("6 Ball Auto", AutonomousMode.EIGHT_BALL);
        autonomousModeChooser.addOption("Simple Shoot Three", AutonomousMode.SIMPLE_SHOOT_THREE);
        autonomousModeChooser.addOption("Straight Line", AutonomousMode.STRAIGHT_LINE);
        autonomousModeChooser.addOption("Auto Path One", AutonomousMode.AUTO_PATH_ONE);
    }

    public SendableChooser<AutonomousMode> getAutonomousModeChooser() {
        return autonomousModeChooser;
    }

    private SequentialCommandGroup get10BallAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getTenBallAutoPartOne());
        followAndIntake(command, container, trajectories.getTenBallAutoPartOne());
        shootAtTarget(command, container);
        //command.addCommands(new FollowTrajectoryCommand(drivetrainSubsystem, trajectories.getTenBallAutoPartTwo()));
        //command.addCommands(new TargetWithShooterCommand(shooterSubsystem, visionSubsystem, xboxController));

        return command;
    }

    private Command get8BallAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        //reset robot pose
        resetRobotPose(command, container, trajectories.getEightBallAutoPartOne());
        //follow first trajectory and shoot
        follow(command, container, trajectories.getEightBallAutoPartOne());
        // shoot
        //follow second trajectory and shoot
        followAndIntake(command, container, trajectories.getEightBallAutoPartTwo());

        follow(command, container, trajectories.getEightBallAutoPartThree());
        // shoot
        follow(command, container, trajectories.getEightBallAutoPartFour());

        return command;
    }

    public Command getSimpleShootThreeAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getSimpleShootThree());

        // shootAtTarget(command, container);
        follow(command, container, trajectories.getSimpleShootThree());

        return command;
    }

    public Command getStraightAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getStraightAuto());

        follow(command, container, trajectories.getStraightAuto());

        return command;
    }

    public Command getAutoPathOneCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getAutoPathOnePartOne());

        // follow path one
        follow(command, container, trajectories.getAutoPathOnePartOne());

        // shoot first ball
        //shootAtTarget(command, container);

        // follow path two and intake second and third balls
        //follow(command, container, trajectories.getAutoPathOnePartTwo()); // should be follow and intake

        // shoot second and third balls
        //shootAtTarget(command, container);

        // follow path three and intake fourth and fifth balls
        //follow(command, container, trajectories.getAutoPathOnePartThree()); // should be follow and intake

        // shoot fourth and fifth balls
        //shootAtTarget(command, container);

        return command;
    }

    public Command getCommand(RobotContainer container) {
        switch (autonomousModeChooser.getSelected()) {
            case EIGHT_BALL:
                return get8BallAutoCommand(container);
            case TEN_BALL:
                return get10BallAutoCommand(container);
            case SIMPLE_SHOOT_THREE:
                return getSimpleShootThreeAutoCommand(container);
            case STRAIGHT_LINE:
                return getStraightAutoCommand(container);
            case AUTO_PATH_ONE:
                return getAutoPathOneCommand(container);
        }

        return get10BallAutoCommand(container);
    }

    private void shootAtTarget(SequentialCommandGroup command, RobotContainer container) {
        shootAtTarget(command, container, 2.5);
    }

    private void shootAtTarget(SequentialCommandGroup command, RobotContainer container, double timeToWait) {
    }

    private void follow(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));
    }

    private void followAndIntake(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(
                new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));
    }

    private void resetRobotPose(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new InstantCommand(() -> container.getDrivetrainSubsystem().resetGyroAngle(Rotation2.ZERO)));
        command.addCommands(new InstantCommand(() -> container.getDrivetrainSubsystem().resetPose(
                new RigidTransform2(trajectory.calculate(0.0).getPathState().getPosition(), Rotation2.ZERO))));
    }

    private enum AutonomousMode {
        EIGHT_BALL,
        TEN_BALL,
        SIMPLE_SHOOT_THREE,
        STRAIGHT_LINE,
        AUTO_PATH_ONE
    }
}
