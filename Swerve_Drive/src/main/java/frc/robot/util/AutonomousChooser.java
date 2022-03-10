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
    private SendableChooser<AutonomousAlliance> autonomousAllianceChooser = new SendableChooser<>();
    private SendableChooser<AutonomousNumCargo> autonomousNumCargoChooser = new SendableChooser<>();

    public AutonomousChooser(AutonomousTrajectories trajectories) {
        this.trajectories = trajectories;

        autonomousModeChooser.setDefaultOption("None", AutonomousMode.NONE);
        autonomousModeChooser.addOption("Straight Back", AutonomousMode.STRAIGHT_BACK);
        autonomousModeChooser.addOption("Straight Back", AutonomousMode.STRAIGHT_BACK_SHOOT);
        autonomousModeChooser.addOption("Auto Path One", AutonomousMode.COMPLEX);

        autonomousAllianceChooser.setDefaultOption("Blue", AutonomousAlliance.BLUE);
        autonomousAllianceChooser.addOption("Red", AutonomousAlliance.RED);

        autonomousNumCargoChooser.setDefaultOption("2 cargo", AutonomousNumCargo.TWO);
        autonomousNumCargoChooser.addOption("1 cargo", AutonomousNumCargo.ONE);
    }

    public SendableChooser<AutonomousMode> getAutonomousModeChooser() {
        return autonomousModeChooser;
    }

    public SendableChooser<AutonomousAlliance> getAutonomousAllianceChooser() {
        return autonomousAllianceChooser;
    }

    public SendableChooser<AutonomousNumCargo> getAutonomousNumCargo() {
        return autonomousNumCargoChooser;
    }


    public Command getNoneAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        return command;
    }

    public Command getStraightBackAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getStraightBackAuto());

        // follow straight back path
        follow(command, container, trajectories.getStraightBackAuto());
        //command.addCommands(new FollowTrajectoryCommand(drivetrainSubsystem, trajectories.getStraightBackAuto()));

        return command;
    }

    public Command getStraightBackShootAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getStraightBackAuto());

        // follow straight back path
        follow(command, container, trajectories.getStraightBackAuto());
        //command.addCommands(new FollowTrajectoryCommand(drivetrainSubsystem, trajectories.getStraightBackAuto()));

        // shoot
        //shootAtTarget(command, container);
        //command.addCommands(new TargetWithShooterCommand(shooterSubsystem, visionSubsystem, xboxController));

        return command;
    }

    public Command getComplexAutoBlueTwoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoBlueTwoPartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoBlueTwoPartOne());

        // shoot first ball
        //shootAtTarget(command, container);

        // follow path two and intake second and third balls
        //followAndIntake(command, container, trajectories.getAutoPathOnePartTwo());

        // shoot second and third balls
        //shootAtTarget(command, container);

        // follow path three and intake fourth and fifth balls
        //followAndIntake(command, container, trajectories.getAutoPathOnePartThree());

        // shoot fourth and fifth balls
        //shootAtTarget(command, container);

        return command;
    }

    public Command getComplexAutoRedTwoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoRedTwoPartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoRedTwoPartOne());

        // shoot first ball
        //shootAtTarget(command, container);

        // follow path two and intake second and third balls
        //followAndIntake(command, container, trajectories.getAutoPathOnePartTwo());

        // shoot second and third balls
        //shootAtTarget(command, container);

        // follow path three and intake fourth and fifth balls
        //followAndIntake(command, container, trajectories.getAutoPathOnePartThree());

        // shoot fourth and fifth balls
        //shootAtTarget(command, container);

        return command;
    }

    public Command getCommand(RobotContainer container) {
        AutonomousMode mode = autonomousModeChooser.getSelected();
        AutonomousAlliance alliance = autonomousAllianceChooser.getSelected();
        AutonomousNumCargo numCargo = autonomousNumCargoChooser.getSelected();
        if (mode == AutonomousMode.NONE) {
            return getNoneAutoCommand(container);
        } else if (mode == AutonomousMode.STRAIGHT_BACK) {
            return getStraightBackAutoCommand(container);
        } else if (mode == AutonomousMode.STRAIGHT_BACK_SHOOT) {
            return getStraightBackShootAutoCommand(container);
        } else if (mode == AutonomousMode.COMPLEX && alliance == AutonomousAlliance.BLUE && numCargo == AutonomousNumCargo.TWO) {
            return getComplexAutoBlueTwoCommand(container);
        } else if (mode == AutonomousMode.COMPLEX && alliance == AutonomousAlliance.RED && numCargo == AutonomousNumCargo.TWO) {
            return getComplexAutoRedTwoCommand(container);
        } else {
            return getNoneAutoCommand(container);
        }
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
        STRAIGHT_BACK_SHOOT,
        COMPLEX
    }

    private enum AutonomousAlliance {
        BLUE,
        RED
    }

    private enum AutonomousNumCargo {
        TWO,
        ONE
    }
}
