// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static double xt = 0;

  public static double yt = 0;
  public static double zt = 0;
  public static double zr = 0;
  public static double launchAngle = 0;
  /**
   * The left-to-right distance between the drivetrain wheels
   *
   * <p>Should be measured from center to center.
   */
  public static final double DRIVETRAIN_TRACKWIDTH_METERS = 23; // FIXME Measure and set trackwidth
  /**
   * The front-to-back distance between the drivetrain wheels.
   *
   * <p>Should be measured from center to center.
   */
  public static final double DRIVETRAIN_WHEELBASE_METERS = 23; // FIXME Measure and set wheelbase

  public static final int DRIVETRAIN_PIGEON_ID = 16; // FIXME Set Pigeon ID

  public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR =
      3; // FIXME Set front left module drive motor ID
  public static final int FRONT_LEFT_MODULE_STEER_MOTOR =
      4; // FIXME Set front left module steer motor ID
  public static final int FRONT_LEFT_MODULE_STEER_ENCODER =
      10; // FIXME Set front left steer encoder ID
  public static final double FRONT_LEFT_MODULE_STEER_OFFSET =
      -Math.toRadians(16.14990234375); // FIXME Measure and set front left steer offset

  public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR =
      7; // FIXME Set front right drive motor ID
  public static final int FRONT_RIGHT_MODULE_STEER_MOTOR =
      8; // FIXME Set front right steer motor ID
  public static final int FRONT_RIGHT_MODULE_STEER_ENCODER =
      12; // FIXME Set front right steer encoder ID
  public static final double FRONT_RIGHT_MODULE_STEER_OFFSET =
      -Math.toRadians(245.99761962890622); // FIXME Measure and set front right steer offset

  public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 1; // FIXME Set back left drive motor ID
  public static final int BACK_LEFT_MODULE_STEER_MOTOR = 2; // FIXME Set back left steer motor ID
  public static final int BACK_LEFT_MODULE_STEER_ENCODER =
      9; // FIXME Set back left steer encoder ID
  public static final double BACK_LEFT_MODULE_STEER_OFFSET =
      -Math.toRadians(190.3656005859375); // FIXME Measure and set back left steer offset

  public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 5; // FIXME Set back right drive motor ID
  public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 6; // FIXME Set back right steer motor ID
  public static final int BACK_RIGHT_MODULE_STEER_ENCODER =
      11; // FIXME Set back right steer encoder ID
  public static final double BACK_RIGHT_MODULE_STEER_OFFSET =
      -Math.toRadians(239.66674804687497); // FIXME Measure and set back right steer offset

  public static final int LED_ID = 0;
  public static final int LEDPerMeter = 60;
  public static final double frameWidth = 28;
  public static final double frameLength = 28;

  public static final int IndexerBottom = 21;
  public static final int IndexerTop = 22;
  public static final int IndexerBottomSensor = 1;
  public static final int IndexerTopSensor = 2;

  public static final int IntakeMotor = 41;
}
