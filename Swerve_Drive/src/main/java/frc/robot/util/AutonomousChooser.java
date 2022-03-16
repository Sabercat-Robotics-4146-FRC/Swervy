package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.FollowTrajectoryCommand;
import frc.robot.commands.ShootCommand;
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
        autonomousModeChooser.addOption("Complex Path", AutonomousMode.COMPLEX);

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

        return command;
    }

    public Command getStraightBackShootAutoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getStraightBackAuto());

        // follow straight back path
        follow(command, container, trajectories.getStraightBackAuto());

        // shoot ball
        shoot(command, container, 2.5);

        return command;
    }

    public Command getComplexAutoBlueTwoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoBlueTwoPartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoBlueTwoPartOne());

        // intake second ball

        // shoot first and second balls
        shoot(command, container, 2.5);

        // follow path two
        follow(command, container, trajectories.getComplexAutoBlueTwoPartTwo());
        
        // intake third ball

        // shoot third ball
        shoot(command, container, 2.5);

        // follow path three
        follow(command, container, trajectories.getComplexAutoBlueTwoPartThree());
        
        // intake fourth and fifth balls

        // shoot fourth and fifth balls

        return command;
    }

    public Command getComplexAutoBlueOneCommand(RobotContainer container){
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoBlueOnePartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoBlueOnePartOne());

        // intake second ball

        // shoot first and second balls
        shoot(command, container, 2.5);

        // follow path two
        follow(command, container, trajectories.getComplexAutoBlueOnePartTwo());

        // intake third and fourth balls

        // shoot third and fourth balls
        shoot(command, container, 2.5);

        return command;
    }

    public Command getComplexAutoRedTwoCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoRedTwoPartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoRedTwoPartOne());

        // intake second ball

        // shoot first and second balls
        shoot(command, container, 2.5);

        // follow path two
        follow(command, container, trajectories.getComplexAutoRedTwoPartTwo());
        
        // intake third ball

        // shoot third ball
        shoot(command, container, 2.5);

        // follow path three
        follow(command, container, trajectories.getComplexAutoRedTwoPartThree());
        
        // intake fourth and fifth balls

        // shoot fourth and fifth balls
        shoot(command, container, 2.5);

        return command;
    }

    public Command getComplexAutoRedOneCommand(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        resetRobotPose(command, container, trajectories.getComplexAutoRedOnePartOne());

        // follow path one
        follow(command, container, trajectories.getComplexAutoRedOnePartOne());

        // intake second ball

        // shoot first and second balls
        shoot(command, container, 2.5);

        // follow path two
        follow(command, container, trajectories.getComplexAutoRedOnePartTwo());

        // intake third and fourth balls

        // shoot third and fourth balls
        shoot(command, container, 2.5);

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
        } else if (mode == AutonomousMode.COMPLEX && alliance == AutonomousAlliance.BLUE && numCargo == AutonomousNumCargo.ONE) {
            return getComplexAutoBlueOneCommand(container);
        } else if (mode == AutonomousMode.COMPLEX && alliance == AutonomousAlliance.RED && numCargo == AutonomousNumCargo.ONE) {
            return getComplexAutoRedOneCommand(container);
        } else {
            return getNoneAutoCommand(container);
        }
    }


    private void shoot(SequentialCommandGroup command, RobotContainer container, double timeToWait) { // default time is 2.5
        command.addCommands(new ShootCommand(container.getFlywheel(), container.getVision()));
    }

    private void follow(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
        command.addCommands(new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));
    }

    private void intake(SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
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
