package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeAndIndexer;

public class LoadBallCommand extends CommandBase {
  public IntakeAndIndexer m_IntakeAndIndexer;

  public LoadBallCommand(IntakeAndIndexer m_IntakeAndIndexer) {
    this.m_IntakeAndIndexer = m_IntakeAndIndexer;

    addRequirements(m_IntakeAndIndexer);
  }

  @Override
  public void initialize() {
    m_IntakeAndIndexer.loadTopBall();
  }
}
