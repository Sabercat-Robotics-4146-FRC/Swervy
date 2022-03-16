package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeAndIndexer;

public class IntakeCommand extends CommandBase {
  public IntakeAndIndexer m_IntakeAndIndexer;

  public IntakeCommand(IntakeAndIndexer m_IntakeAndIndexer) {
    this.m_IntakeAndIndexer = m_IntakeAndIndexer;

    addRequirements(m_IntakeAndIndexer);
  }

  @Override
  public void initialize() {
    m_IntakeAndIndexer.extendIntakeSubsystem();
    m_IntakeAndIndexer.toggleIntake();
  }

  @Override
  public void end(boolean interrupted) {
    m_IntakeAndIndexer.toggleIntake();
  }
}
