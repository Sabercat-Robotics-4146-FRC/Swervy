package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.controller.PIDController;
// import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;

public class Swerve extends Subsystem {
  private static Swerve mInstance;

  public static Swerve getInstance() {
    if (mInstance == null) {
      mInstance = new Swerve();
    }

    return mInstance;
  }

  public static class PeriodicIO {
    // outputs
    public double FRSpeed;
    public double FLSpeed;
    public double BRSpeed;
    public double BLSpeed;
    public double FROrientation;
    public double FROrientationFinal;
    public double FLOrientation;
    public double FLOrientationFinal;
    public double BROrientation;
    public double BROrientationFinal;
    public double BLOrientation;
    public double BLOrientationFinal;

    // inputs
    public double FRAngle;
    public double FLAngle;
    public double BRAngle;
    public double BLAngle;
  }

  private PeriodicIO mPeriodicIO = new PeriodicIO();

  private final TalonFX FRSpeed;
  private final TalonFX FROrientation;
  private final PIDController FRPid;

  private final TalonFX FLSpeed;
  private final TalonFX FLOrientation;
  private final PIDController FLPid;

  private final TalonFX BRSpeed;
  private final TalonFX BROrientation;
  private final PIDController BRPid;

  private final TalonFX BLSpeed;
  private final TalonFX BLOrientation;
  private final PIDController BLPid;

  private final CANCoder FRCANCoder;
  private final CANCoder FLCANCoder;
  private final CANCoder BRCANCoder;
  private final CANCoder BLCANCoder;

  private Swerve() {
    FRSpeed = new TalonFX(Constants.kFRSpeedId);
    FROrientation = new TalonFX(Constants.kFROrientationId);
    FRCANCoder = new CANCoder(Constants.kFRCANCoderId);
    FRPid = new PIDController(Constants.kPGain, Constants.kIGain, Constants.KDGain);
    // FRPid.enableContinuousInput(0, 360);
    // FROrientation.configFactoryDefault();
    // FROrientation.configNeutralDeadband(0.001);
    // FROrientation.configRemoteFeedbackFilter(FRCANCoder, 0, Constants.kTimeout);
    // FROrientation.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
    // FROrientation.configNominalOutputForward(0, Constants.kTimeout);
    // FROrientation.configNominalOutputReverse(0, Constants.kTimeout);
    // FROrientation.configPeakOutputForward(1, Constants.kTimeout);
    // FROrientation.configPeakOutputReverse(-1, Constants.kTimeout);

    FLSpeed = new TalonFX(Constants.kFLSpeedId);
    FLOrientation = new TalonFX(Constants.kFLOrientationId);
    FLCANCoder = new CANCoder(Constants.kFLCANCoderId);
    FLPid = new PIDController(Constants.kPGain, Constants.kIGain, Constants.KDGain);
    // FLPid.enableContinuousInput(0, 360);
    // FLOrientation.configFactoryDefault();
    // FLOrientation.configNeutralDeadband(0.001);
    // FLOrientation.configRemoteFeedbackFilter(FLCANCoder, 0, Constants.kTimeout);
    // FLOrientation.configNominalOutputForward(0, Constants.kTimeout);
    // FLOrientation.configNominalOutputReverse(0, Constants.kTimeout);
    // FLOrientation.configPeakOutputForward(1, Constants.kTimeout);
    // FLOrientation.configPeakOutputReverse(-1, Constants.kTimeout);

    BRSpeed = new TalonFX(Constants.kBRSpeedId);
    BROrientation = new TalonFX(Constants.kBROrientationId);
    BRCANCoder = new CANCoder(Constants.kBRCANCoderId);
    BRPid = new PIDController(Constants.kPGain, Constants.kIGain, Constants.KDGain);
    // BRPid.enableContinuousInput(0, 360);
    // BROrientation.configFactoryDefault();
    // BROrientation.configNeutralDeadband(0.001);
    // BROrientation.configRemoteFeedbackFilter(BRCANCoder, 0, Constants.kTimeout);
    // BROrientation.configNominalOutputForward(0, Constants.kTimeout);
    // BROrientation.configNominalOutputReverse(0, Constants.kTimeout);
    // BROrientation.configPeakOutputForward(1, Constants.kTimeout);
    // BROrientation.configPeakOutputReverse(-1, Constants.kTimeout);

    BLSpeed = new TalonFX(Constants.kBLSpeedId);
    BLOrientation = new TalonFX(Constants.kBLOrientationId);
    BLCANCoder = new CANCoder(Constants.kBLCANCoderId);
    BLPid = new PIDController(Constants.kPGain, Constants.kIGain, Constants.KDGain);
    // BLPid.enableContinuousInput(0, 360);
    // BLOrientation.configFactoryDefault();
    // BLOrientation.configNeutralDeadband(0.001);
    // BLOrientation.configRemoteFeedbackFilter(BLCANCoder, 0, Constants.kTimeout);
    // BLOrientation.configNominalOutputForward(0, Constants.kTimeout);
    // BLOrientation.configNominalOutputReverse(0, Constants.kTimeout);
    // BLOrientation.configPeakOutputForward(1, Constants.kTimeout);
    // BLOrientation.configPeakOutputReverse(-1, Constants.kTimeout);
  }

  @Override
  public void writePeriodicOutputs() {
    FRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FRSpeed);
    FLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.FLSpeed);
    BRSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BRSpeed);
    BLSpeed.set(ControlMode.PercentOutput, mPeriodicIO.BLSpeed);
    FROrientation.set(ControlMode.PercentOutput, mPeriodicIO.FROrientationFinal);
    FLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.FLOrientationFinal);
    BROrientation.set(ControlMode.PercentOutput, mPeriodicIO.BROrientationFinal);
    BLOrientation.set(ControlMode.PercentOutput, mPeriodicIO.BLOrientationFinal);
  }

  @Override
  public void readPeriodicInputs() {
    mPeriodicIO.FRAngle = FRCANCoder.getAbsolutePosition();
    mPeriodicIO.FLAngle = FLCANCoder.getAbsolutePosition();
    mPeriodicIO.BRAngle = BRCANCoder.getAbsolutePosition();
    mPeriodicIO.BLAngle = BLCANCoder.getAbsolutePosition();
  }

  public synchronized void setSwerveDrive(double x1, double y1, double x2) {
    double a = x1 - x2 * (Constants.kWheelDistanceL / Constants.kR);
    double b = x1 + x2 * (Constants.kWheelDistanceL / Constants.kR);
    double c = y1 - x2 * (Constants.kWheelDistanceW / Constants.kR);
    double d = y1 + x2 * (Constants.kWheelDistanceW / Constants.kR);

    mPeriodicIO.BRSpeed = Math.sqrt((a * a) + (d * d));
    mPeriodicIO.BLSpeed = Math.sqrt((a * a) + (c * c));
    mPeriodicIO.FRSpeed = Math.sqrt((b * b) + (d * d));
    mPeriodicIO.FLSpeed = Math.sqrt((b * b) + (c * c));

    mPeriodicIO.BROrientation = Math.atan2(a, d) / Math.PI;
    mPeriodicIO.BLOrientation = Math.atan2(a, c) / Math.PI;
    mPeriodicIO.FROrientation = Math.atan2(b, d) / Math.PI;
    mPeriodicIO.FLOrientation = Math.atan2(b, c) / Math.PI;

    double FRDegrees = FRCANCoder.getAbsolutePosition();
    double FLDegrees = FLCANCoder.getAbsolutePosition();
    double BRDegrees = BRCANCoder.getAbsolutePosition();
    double BLDegrees = BLCANCoder.getAbsolutePosition();
    SmartDashboard.putNumber("Front Right Encoder Angle", FRDegrees);
    SmartDashboard.putNumber("Front Left Encoder Angle", FLDegrees);
    SmartDashboard.putNumber("Back Right Encoder Angle", BRDegrees);
    SmartDashboard.putNumber("Back Left Encoder Angle", BLDegrees);

    mPeriodicIO.FROrientationFinal =
        FRPid.calculate(FRCANCoder.getAbsolutePosition(), mPeriodicIO.FROrientation * 180);
    mPeriodicIO.FLOrientationFinal =
        FLPid.calculate(FLCANCoder.getAbsolutePosition(), mPeriodicIO.FLOrientation * 180);
    mPeriodicIO.BROrientationFinal =
        BRPid.calculate(BRCANCoder.getAbsolutePosition(), mPeriodicIO.BROrientation * 180);
    mPeriodicIO.BLOrientationFinal =
        BLPid.calculate(BLCANCoder.getAbsolutePosition(), mPeriodicIO.BLOrientation * 180);
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
    SmartDashboard.putNumber("FR Encoder", FRCANCoder.getAbsolutePosition());
    SmartDashboard.putNumber("FL Encoder", FLCANCoder.getAbsolutePosition());
    SmartDashboard.putNumber("BR Encoder", BRCANCoder.getAbsolutePosition());
    SmartDashboard.putNumber("BL Encoder", BLCANCoder.getAbsolutePosition());
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
