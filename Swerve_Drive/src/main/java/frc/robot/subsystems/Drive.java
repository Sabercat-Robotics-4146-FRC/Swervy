package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
// import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;

public class Drive extends Subsystem {
  private static Drive mInstance;

  public static Drive getInstance() {
    if (mInstance == null) {
      mInstance = new Drive();
    }

    return mInstance;
  }

  public static class PeriodicIO {
    public double FRSpeed;
    public double FLSpeed;
    public double BRSpeed;
    public double BLSpeed;
    public double FROrientation;
    public double FLOrientation;
    public double BROrientation;
    public double BLOrientation;
  }

  private PeriodicIO mPeriodicIO = new PeriodicIO();

  private final TalonFX FRSpeed;
  private final TalonFX FROrientation;

  private final TalonFX FLSpeed;
  private final TalonFX FLOrientation;

  private final TalonFX BRSpeed;
  private final TalonFX BROrientation;

  private final TalonFX BLSpeed;
  private final TalonFX BLOrientation;

  private final CANCoder FRCANCoder;
  private final CANCoder FLCANCoder;
  private final CANCoder BRCANCoder;
  private final CANCoder BLCANCoder;

  int loopCount = 0;
  double FRDegrees = 0;
  double FLDegrees = 0;
  double BRDegrees = 0;
  double BLDegrees = 0;

  private Drive() {
    FRSpeed = new TalonFX(Constants.kFRSpeedId);
    FROrientation = new TalonFX(Constants.kFROrientationId);
    FRCANCoder = new CANCoder(Constants.kFRCANCoderId);

    FLSpeed = new TalonFX(Constants.kFLSpeedId);
    FLOrientation = new TalonFX(Constants.kFLOrientationId);
    FLCANCoder = new CANCoder(Constants.kFLCANCoderId);

    BRSpeed = new TalonFX(Constants.kBRSpeedId);
    BROrientation = new TalonFX(Constants.kBROrientationId);
    BRCANCoder = new CANCoder(Constants.kBRCANCoderId);

    BLSpeed = new TalonFX(Constants.kBLSpeedId);
    BLOrientation = new TalonFX(Constants.kBLOrientationId);
    BLCANCoder = new CANCoder(Constants.kBLCANCoderId);
  }

  @Override
  public void writePeriodicOutputs() {
    FRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FRSpeed);
    FLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FLSpeed);
    BRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BRSpeed);
    BLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BLSpeed);
    FROrientation.set(ControlMode.PercentOutput, mPeriodicIO.FROrientation);
    FLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.FLOrientation);
    BROrientation.set(ControlMode.PercentOutput, mPeriodicIO.BROrientation);
    BLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.BLOrientation);
  }

  public synchronized void setSwerveDrive(double xComponent, double yComponent) {
    FRSpeed.set(
        ControlMode.PercentOutput,
        (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    FLSpeed.set(
        ControlMode.PercentOutput,
        (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    BRSpeed.set(
        ControlMode.PercentOutput,
        (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    BLSpeed.set(
        ControlMode.PercentOutput,
        (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));

    if (loopCount++ > 10) {
      loopCount = 0;
      double FRDegreesTotal = FRCANCoder.getPosition();
      double FRDegrees = (java.lang.Math.abs(FRDegreesTotal) % 360);
      double FLDegreesTotal = FLCANCoder.getPosition();
      double FLDegrees = (java.lang.Math.abs(FLDegreesTotal) % 360);
      double BRDegreesTotal = BRCANCoder.getPosition();
      double BRDegrees = (java.lang.Math.abs(BRDegreesTotal) % 360);
      double BLDegreesTotal = BLCANCoder.getPosition();
      double BLDegrees = (java.lang.Math.abs(BLDegreesTotal) % 360);
      SmartDashboard.putNumber("Front Right Encoder Angle", FRDegrees);
      SmartDashboard.putNumber("Front Left Encoder Angle", FLDegrees);
      SmartDashboard.putNumber("Back Right Encoder Angle", BRDegrees);
      SmartDashboard.putNumber("Back Left Encoder Angle", BLDegrees);
    }

    double expectedOrientation = java.lang.Math.atan(yComponent / xComponent);

    if (expectedOrientation == FRDegrees) {
      FROrientation.set(ControlMode.PercentOutput, 0.0);
    } else if (expectedOrientation - FRDegrees > 0 & expectedOrientation - FRDegrees < 180) {
      FROrientation.set(ControlMode.PercentOutput, .1);
    } else {
      FROrientation.set(ControlMode.PercentOutput, -.1);
    }

    if (expectedOrientation == FLDegrees) {
      FLOrientation.set(ControlMode.PercentOutput, 0.0);
    } else if (expectedOrientation - FLDegrees > 0 & expectedOrientation - FLDegrees < 180) {
      FLOrientation.set(ControlMode.PercentOutput, .1);
    } else {
      FLOrientation.set(ControlMode.PercentOutput, -.1);
    }

    if (expectedOrientation == BRDegrees) {
      BROrientation.set(ControlMode.PercentOutput, 0.0);
    } else if (expectedOrientation - BRDegrees > 0 & expectedOrientation - BRDegrees < 180) {
      BROrientation.set(ControlMode.PercentOutput, .1);
    } else {
      BROrientation.set(ControlMode.PercentOutput, -.1);
    }

    if (expectedOrientation == BRDegrees) {
      BROrientation.set(ControlMode.PercentOutput, 0.0);
    } else if (expectedOrientation - BRDegrees > 0 & expectedOrientation - BRDegrees < 180) {
      BROrientation.set(ControlMode.PercentOutput, .1);
    } else {
      BROrientation.set(ControlMode.PercentOutput, -.1);
    }
  }

  public void setOpenLoop(DriveSignal signal) {}

  @Override
  public void stop() {
    FRSpeed.set(ControlMode.PercentOutput, 0.0);
    FLSpeed.set(ControlMode.PercentOutput, 0.0);
    BRSpeed.set(ControlMode.PercentOutput, 0.0);
    BRSpeed.set(ControlMode.PercentOutput, 0.0);
    FROrientation.set(ControlMode.PercentOutput, 0.0);
    FLOrientation.set(ControlMode.PercentOutput, 0.0);
    BROrientation.set(ControlMode.PercentOutput, 0.0);
    BLOrientation.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public boolean checkSystem() {
    return true;
  }

  @Override
  public void outputTelemetry() {
    SmartDashboard.putNumber("Front Right Speed", mPeriodicIO.FRSpeed);
    SmartDashboard.putNumber("Front Left Speed", mPeriodicIO.FLSpeed);
    SmartDashboard.putNumber("Back Right Speed", mPeriodicIO.BRSpeed);
    SmartDashboard.putNumber("Back Left Speed", mPeriodicIO.BLSpeed);
    SmartDashboard.putNumber("Front Right Orientation", mPeriodicIO.FROrientation);
    SmartDashboard.putNumber("Front Left Orientation", mPeriodicIO.FLOrientation);
    SmartDashboard.putNumber("Back Right Orientation", mPeriodicIO.BROrientation);
    SmartDashboard.putNumber("Back Left Orientation", mPeriodicIO.BLOrientation);
  }
}
