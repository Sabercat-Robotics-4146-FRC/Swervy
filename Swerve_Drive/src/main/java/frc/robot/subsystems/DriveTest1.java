package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;

public class DriveTest1 extends Subsystem {
  private static DriveTest1 mInstance;

  public static DriveTest1 getInstance() {
    if (mInstance == null) {
      mInstance = new DriveTest1();
    }

    return mInstance;
  }

  public static class PeriodicIO {
    public double FRSpeed;
    public double FLSpeed;
    public double BRSpeed;
    public double BLSpeed;
  }

  private PeriodicIO mPeriodicIO = new PeriodicIO();

  private final TalonFX FRSpeed;
  private final TalonFX FLSpeed;
  private final TalonFX BRSpeed;
  private final TalonFX BLSpeed;

  private DriveTest1() {
    FRSpeed = new TalonFX(Constants.kFRSpeedId);
    FLSpeed = new TalonFX(Constants.kFLSpeedId);
    BRSpeed = new TalonFX(Constants.kBRSpeedId);
    BLSpeed = new TalonFX(Constants.kBLSpeedId);
  }

  @Override
  public void writePeriodicOutputs() {
    FRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FRSpeed);
    FLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FLSpeed);
    BRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BRSpeed);
    BLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BLSpeed);
  }

  public synchronized void setSwerveDrive(double xComponent, double yComponent) {
    // FRSpeed.set(
    //     ControlMode.PercentOutput,
    //     (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    // FLSpeed.set(
    //     ControlMode.PercentOutput,
    //     (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    BRSpeed.set(
        ControlMode.PercentOutput,
        (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
    // BLSpeed.set(
    //     ControlMode.PercentOutput,
    //     (java.lang.Math.sqrt((xComponent * xComponent) + (yComponent * yComponent))));
  }

  public void setOpenLoop(DriveSignal signal) {}

  @Override
  public void stop() {
    FRSpeed.set(ControlMode.PercentOutput, 0.0);
    FLSpeed.set(ControlMode.PercentOutput, 0.0);
    BRSpeed.set(ControlMode.PercentOutput, 0.0);
    BRSpeed.set(ControlMode.PercentOutput, 0.0);
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
  }
}
