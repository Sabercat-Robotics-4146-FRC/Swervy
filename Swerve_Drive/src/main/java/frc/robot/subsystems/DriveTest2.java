package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;

public class DriveTest2 extends Subsystem {
  private static DriveTest2 mInstance;

  public static DriveTest2 getInstance() {
    if (mInstance == null) {
      mInstance = new DriveTest2();
    }

    return mInstance;
  }

  public static class PeriodicIO {
    public double FROrientation;
    public double FLOrientation;
    public double BROrientation;
    public double BLOrientation;
    public double PigeonOrientation;
  }

  private PeriodicIO mPeriodicIO = new PeriodicIO();

  private final TalonFX FROrientation;
  private final TalonFX FLOrientation;
  private final TalonFX BROrientation;
  private final TalonFX BLOrientation;

  private final CANCoder FRCANCoder;
  private final CANCoder FLCANCoder;
  private final CANCoder BRCANCoder;
  private final CANCoder BLCANCoder;

  private final PigeonIMU THEPigeon;

  int loopCount = 0;
  double FRDegrees = 0;
  double FLDegrees = 0;
  double BRDegrees = 0;
  double BLDegrees = 0;

  private DriveTest2() {
    FROrientation = new TalonFX(Constants.kFROrientationId);
    FRCANCoder = new CANCoder(Constants.kFRCANCoderId);

    FLOrientation = new TalonFX(Constants.kFLOrientationId);
    FLCANCoder = new CANCoder(Constants.kFLCANCoderId);

    BROrientation = new TalonFX(Constants.kBROrientationId);
    BRCANCoder = new CANCoder(Constants.kBRCANCoderId);

    BLOrientation = new TalonFX(Constants.kBLOrientationId);
    BLCANCoder = new CANCoder(Constants.kBLCANCoderId);

    THEPigeon = new PigeonIMU(Constants.kPigeonID);
  }

  @Override
  public void writePeriodicOutputs() {
    FROrientation.set(ControlMode.PercentOutput, mPeriodicIO.FROrientation);
    FLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.FLOrientation);
    BROrientation.set(ControlMode.PercentOutput, mPeriodicIO.BROrientation);
    BLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.BLOrientation);
  }

  public synchronized void setSwerveDrive(
      double xComponent,
      double yComponent,
      boolean clearValues,
      boolean NORTH,
      boolean EAST,
      boolean SOUTH,
      boolean WEST) {

    if (clearValues) {
      double idk = THEPigeon.getCompassHeading();
      FRCANCoder.setPosition(idk);
      FLCANCoder.setPosition(idk);
      BRCANCoder.setPosition(idk);
      BLCANCoder.setPosition(idk);
    }

    if (NORTH) {
      if (FRDegrees == 0.0) {
        FROrientation.set(ControlMode.PercentOutput, 0);
      } else {
        FROrientation.set(ControlMode.PercentOutput, 0.1);
      }
    }

    if (EAST) {
      if (FRDegrees == 270.0) {
        FROrientation.set(ControlMode.PercentOutput, 0);
      } else {
        FROrientation.set(ControlMode.PercentOutput, 0.1);
      }
    }

    if (SOUTH) {
      if (FRDegrees == 180.0) {
        FROrientation.set(ControlMode.PercentOutput, 0);
      } else {
        FROrientation.set(ControlMode.PercentOutput, 0.1);
      }
    }

    if (WEST) {
      if (FRDegrees == 90.0) {
        FROrientation.set(ControlMode.PercentOutput, 0);
      } else {
        FROrientation.set(ControlMode.PercentOutput, 0.1);
      }
    }

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

    // double expectedOrientation = (java.lang.Math.atan(yComponent / xComponent)) - 90;

    // if (expectedOrientation == FRDegrees) {
    //   FROrientation.set(ControlMode.PercentOutput, 0.0);
    // } else {
    //   FROrientation.set(ControlMode.PercentOutput, .1);
    // }

    // if (expectedOrientation == FLDegrees) {
    //   FLOrientation.set(ControlMode.PercentOutput, 0.0);
    // } else {
    //   FLOrientation.set(ControlMode.PercentOutput, 0.1);
    // }

    // if (expectedOrientation == BRDegrees) {
    //   BROrientation.set(ControlMode.PercentOutput, 0.0);
    // } else {
    //   BROrientation.set(ControlMode.PercentOutput, 0.1);
    // }

    // if (expectedOrientation == BRDegrees) {
    //   BROrientation.set(ControlMode.PercentOutput, 0.0);
    // } else {
    //   BROrientation.set(ControlMode.PercentOutput, 0.1);
    // }
  }

  public void setOpenLoop(DriveSignal signal) {}

  @Override
  public void stop() {
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
    SmartDashboard.putNumber("Front Right Orientation", mPeriodicIO.FROrientation);
    SmartDashboard.putNumber("Front Left Orientation", mPeriodicIO.FLOrientation);
    SmartDashboard.putNumber("Back Right Orientation", mPeriodicIO.BROrientation);
    SmartDashboard.putNumber("Back Left Orientation", mPeriodicIO.BLOrientation);
    SmartDashboard.putNumber("Pigeon Values", mPeriodicIO.PigeonOrientation);
  }
}
