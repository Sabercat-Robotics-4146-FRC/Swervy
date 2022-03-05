package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import frc.common.drivers.Gyroscope;
import frc.common.math.Rotation2;

public class Pigeon extends Gyroscope {
  private final Pigeon2 pigeon;

  public Pigeon(int id) {
    this.pigeon = new Pigeon2(id);
  }

  @Override
  public void calibrate() {}

  @Override
  public Rotation2 getUnadjustedAngle() {
    return Rotation2.fromDegrees(pigeon.getYaw());
  }

  @Override
  public double getUnadjustedRate() {
    return 0.0;
  }
}
