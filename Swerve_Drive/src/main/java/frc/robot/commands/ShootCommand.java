package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Vision;

public class ShootCommand extends CommandBase {
    private final Flywheel m_flywheel;
    private final Vision m_vision;

    public ShootCommand(Flywheel flywheel, Vision vision) {
        this.m_flywheel = flywheel;
        this.m_vision = vision;

        addRequirements(flywheel, vision);
    }

    @Override
    public void initialize() {
        m_vision.setLaunchAngle();
        m_vision.turnRobotToHoop();
    }

    @Override
    public void execute() {
        m_flywheel.setVelocity();
    }

    @Override
    public void end(boolean interrupted) {
        m_flywheel.stop();
    }

}

// TODO figure out how this is called, how long it runs