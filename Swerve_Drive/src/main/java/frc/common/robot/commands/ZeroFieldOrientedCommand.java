package frc.common.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.common.robot.subsystems.HolonomicDrivetrain;

@Deprecated
public class ZeroFieldOrientedCommand extends Command {
  private final HolonomicDrivetrain drivetrain;

  public ZeroFieldOrientedCommand(HolonomicDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  protected void initialize() {
    drivetrain.getGyroscope().setAdjustmentAngle(drivetrain.getGyroscope().getUnadjustedAngle());
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}
