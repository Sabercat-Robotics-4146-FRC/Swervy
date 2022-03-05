package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.common.math.Vector2;
import frc.robot.subsystems.DrivetrainSubsystem;
import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends CommandBase {
  private final DrivetrainSubsystem m_drivetrainSubsystem;

  private final DoubleSupplier m_translationXSupplier;
  private final DoubleSupplier m_translationYSupplier;
  private final DoubleSupplier m_rotationSupplier;

  public DefaultDriveCommand(
      DrivetrainSubsystem drivetrainSubsystem,
      DoubleSupplier translationXSupplier,
      DoubleSupplier translationYSupplier,
      DoubleSupplier rotationSupplier) {
    this.m_drivetrainSubsystem = drivetrainSubsystem;
    this.m_translationXSupplier = translationXSupplier;
    this.m_translationYSupplier = translationYSupplier;
    this.m_rotationSupplier = rotationSupplier;

    addRequirements(drivetrainSubsystem);
  }

  @Override
  public void execute() {
    m_drivetrainSubsystem.drive(
        new Vector2(m_translationXSupplier.getAsDouble(), m_translationYSupplier.getAsDouble()),
        m_rotationSupplier.getAsDouble(),
        true);
    // double check values
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrainSubsystem.drive(Vector2.ZERO, 0, true); // double check 0 values
  }
}
