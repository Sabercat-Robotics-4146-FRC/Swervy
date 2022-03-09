package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.FollowTrajectoryCommand;
import frc.common.control.Trajectory;
import frc.common.math.RigidTransform2;
import frc.common.math.Rotation2;

public class AutonomousChooser {
    private final AutonomousTrajectories trajectories;

    private SendableChooser<AutonomousMode> autonomousModeChooser = new SendableChooser<>();

    public AutonomousChooser(AutonomousTrajectories trajectories) {
        this.trajectories = trajectories;

        autonomousModeChooser.addOption("None", AutonomousMode.NONE);
        autonomousModeChooser.addOption("Straight Back", AutonomousMode.STRAIGHT_BACK);
        autonomousModeChooser.addOption("Auto Path One", AutonomousMode.AUTO_PATH_ONE);
    }

    public SendableChooser<AutonomousMode> getAutonomousModeChooser() {
        return autonomousModeChooser;
    }

    // private SequentialCommandGroup get10BallAutoCommand(RobotContainer container) {
    //     SequentialCommandGroup command = new SequentialCommandGroup();

    //     resetRobotPose(command, container, trajectories.getTenBallAutoPartOne());
    //     followAndIntake(command, container, trajectories.getTenBallAutoPartOne());
    //     shootAtTarget(command, container);
    //     //command.addCommands(new FollowTrajectoryCommand(drivetrainSubsystem, trajectories.getTenBallAutoPartTwo()));
    //     //command.addCommands(new TargetWithShooterCommand(shooterSubsystem, visionSubsystem, xboxController));

    //     return command;
    // }

    public Command getNoneAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        return command;
    }

    public Command getStraightBackAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getStraightBackAuto());

        follow(command, container, trajectories.getStraightBackAuto());

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
            case NONE:
                return getNoneAutoCommand(container);
            case STRAIGHT_BACK:
                return getStraightBackAutoCommand(container);
            case AUTO_PATH_ONE:
                return getAutoPathOneCommand(container);
        }
        return getNoneAutoCommand(container);
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
        NONE,
        STRAIGHT_BACK,
        AUTO_PATH_ONE
    }
}
