package frc.robot;

public class Constants {
  public static final double kLooperDt = 0.01;

  // CAN
  public static final int kLongCANTimeoutMs = 100; // use for constructors
  public static final int kCANTimeoutMs = 10; // use for important on the fly updates
  public static final int kPigeonID = 21;

  // Drive
  public static final int kFRSpeedId = 1;
  public static final int kFROrientationId = 2;
  public static final int kFLSpeedId = 3;
  public static final int kFLOrientationId = 4;
  public static final int kBRSpeedId = 5;
  public static final int kBROrientationId = 6;
  public static final int kBLSpeedId = 7;
  public static final int kBLOrientationId = 8;
  public static final int kFRCANCoderId = 9;
  public static final int kFLCANCoderId = 10;
  public static final int kBRCANCoderId = 11;
  public static final int kBLCANCoderId = 12;
  public static final int kFLPidId = 1;
  public static final int kFRPidId = 2;
  public static final int kBLPidId = 3;
  public static final int kBRPidId = 4;
  public static final int kTimeout = 30;
  public static final double kWheelDistanceW = 16.8125;
  public static final double kWheelDistanceL = 23.5;
  public static final double kR =
      Math.sqrt((kWheelDistanceL * kWheelDistanceL) + (kWheelDistanceW * kWheelDistanceW));
  public static final double kPGain = .001;
  public static final double kIGain = 0;
  public static final double KDGain = 0;

  // Xbox Controllers
  public static final int kDriver1USBPort = 0;
  public static final double kJoystickThreshold = 0.2;
}
