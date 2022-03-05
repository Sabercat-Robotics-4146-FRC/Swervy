package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.sensors.Pigeon2;
import com.swervedrivespecialties.swervelib.*;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.common.control.CentripetalAccelerationConstraint;
import frc.common.control.FeedforwardConstraint;
import frc.common.control.HolonomicMotionProfiledTrajectoryFollower;
import frc.common.control.MaxAccelerationConstraint;
import frc.common.control.PidConstants;
import frc.common.control.TrajectoryConstraint;
import frc.common.drivers.Gyroscope;
import frc.common.kinematics.ChassisVelocity;
import frc.common.kinematics.SwerveKinematics;
import frc.common.kinematics.SwerveOdometry;
import frc.common.math.RigidTransform2;
import frc.common.math.Rotation2;
import frc.common.math.Vector2;
import frc.common.robot.UpdateManager;
import frc.common.util.DrivetrainFeedforwardConstants;
import frc.common.util.HolonomicDriveSignal;
import frc.common.util.HolonomicFeedforward;
import frc.common.util.InterpolatingDouble;
import frc.common.util.InterpolatingTreeMap;
import frc.robot.Constants;
import frc.robot.Pigeon;
import java.util.Optional;

public class DrivetrainSubsystem implements Subsystem, UpdateManager.Updatable {

  public static final double MAX_VELOCITY_METERS_PER_SECOND =
      6380.0
          / 60.0
          * SdsModuleConfigurations.MK3_STANDARD.getDriveReduction()
          * SdsModuleConfigurations.MK3_STANDARD.getWheelDiameter()
          * Math.PI;

  public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND =
      MAX_VELOCITY_METERS_PER_SECOND
          / Math.hypot(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0);

  public static final double TRACKWIDTH = 24.0;
  public static final double WHEELBASE = 24.0;

  public static final DrivetrainFeedforwardConstants FEEDFORWARD_CONSTANTS =
      new DrivetrainFeedforwardConstants( // tune with sysid, view w/ .3190 meters per rotation
          0.70067, 2.2741, 0.16779);

  public static final TrajectoryConstraint[] TRAJECTORY_CONSTRAINTS = {
    new FeedforwardConstraint(
        11.0,
        FEEDFORWARD_CONSTANTS.getVelocityConstant(),
        FEEDFORWARD_CONSTANTS.getAccelerationConstant(),
        false),
    new MaxAccelerationConstraint(12.5 * 12.0),
    new CentripetalAccelerationConstraint(15 * 12.0)
  };

  private static final int MAX_LATENCY_COMPENSATION_MAP_ENTRIES = 25;

  private final HolonomicMotionProfiledTrajectoryFollower follower =
      new HolonomicMotionProfiledTrajectoryFollower(
          new PidConstants(0.0014832, 0.0, 0.000065074),
          new PidConstants(0.0014832, 0.0, 0.000065074),
          new HolonomicFeedforward(FEEDFORWARD_CONSTANTS));

  private final SwerveKinematics swerveKinematics =
      new SwerveKinematics(
          new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0), // front left
          new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0), // front right
          new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0), // back left
          new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0) // back right
          );

  private final SwerveModule[] modules;

  private final Object sensorLock = new Object();
  private final Gyroscope gyroscope = new Pigeon(Constants.DRIVETRAIN_PIGEON_ID);

  private final Object kinematicsLock = new Object();
  private final SwerveOdometry swerveOdometry =
      new SwerveOdometry(swerveKinematics, RigidTransform2.ZERO);
  private RigidTransform2 pose = RigidTransform2.ZERO;
  private final InterpolatingTreeMap<InterpolatingDouble, RigidTransform2> latencyCompensationMap =
      new InterpolatingTreeMap<>();
  private Vector2 velocity = Vector2.ZERO;
  private double angularVelocity = 0.0;

  private final Object stateLock = new Object();
  private HolonomicDriveSignal driveSignal = null;

  // Logging
  private final NetworkTableEntry odometryXEntry;
  private final NetworkTableEntry odometryYEntry;
  private final NetworkTableEntry odometryAngleEntry;

  public DrivetrainSubsystem() {
    synchronized (sensorLock) {
      gyroscope.setInverted(false);
    }

    ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

    SwerveModule frontLeftModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Front Left Module", BuiltInLayouts.kList)
                .withPosition(2, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            Constants.FRONT_LEFT_MODULE_DRIVE_MOTOR,
            Constants.FRONT_LEFT_MODULE_STEER_MOTOR,
            Constants.FRONT_LEFT_MODULE_STEER_ENCODER,
            Constants.FRONT_LEFT_MODULE_STEER_OFFSET);
    SwerveModule frontRightModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Front Right Module", BuiltInLayouts.kList)
                .withPosition(4, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            Constants.FRONT_RIGHT_MODULE_DRIVE_MOTOR,
            Constants.FRONT_RIGHT_MODULE_STEER_MOTOR,
            Constants.FRONT_RIGHT_MODULE_STEER_ENCODER,
            Constants.FRONT_RIGHT_MODULE_STEER_OFFSET);
    SwerveModule backLeftModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Back Left Module", BuiltInLayouts.kList)
                .withPosition(6, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            Constants.BACK_LEFT_MODULE_DRIVE_MOTOR,
            Constants.BACK_LEFT_MODULE_STEER_MOTOR,
            Constants.BACK_LEFT_MODULE_STEER_ENCODER,
            Constants.BACK_LEFT_MODULE_STEER_OFFSET);
    SwerveModule backRightModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Back Right Module", BuiltInLayouts.kList)
                .withPosition(8, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            Constants.BACK_RIGHT_MODULE_DRIVE_MOTOR,
            Constants.BACK_RIGHT_MODULE_STEER_MOTOR,
            Constants.BACK_RIGHT_MODULE_STEER_ENCODER,
            Constants.BACK_RIGHT_MODULE_STEER_OFFSET);

    modules =
        new SwerveModule[] {frontLeftModule, frontRightModule, backLeftModule, backRightModule};

    odometryXEntry = tab.add("X", 0.0).withPosition(0, 0).withSize(1, 1).getEntry();
    odometryYEntry = tab.add("Y", 0.0).withPosition(0, 1).withSize(1, 1).getEntry();
    odometryAngleEntry = tab.add("Angle", 0.0).withPosition(0, 2).withSize(1, 1).getEntry();
    tab.addNumber(
            "Trajectory X",
            () -> {
              if (follower.getLastState() == null) {
                return 0.0;
              }
              return follower.getLastState().getPathState().getPosition().x;
            })
        .withPosition(1, 0)
        .withSize(1, 1);
    tab.addNumber(
            "Trajectory Y",
            () -> {
              if (follower.getLastState() == null) {
                return 0.0;
              }
              return follower.getLastState().getPathState().getPosition().y;
            })
        .withPosition(1, 1)
        .withSize(1, 1);

    tab.addNumber(
        "Rotation Voltage",
        () -> {
          HolonomicDriveSignal signal;
          synchronized (stateLock) {
            signal = driveSignal;
          }

          if (signal == null) {
            return 0.0;
          }

          return signal.getRotation() * RobotController.getBatteryVoltage();
        });

    tab.addNumber("Average Velocity", this::getAverageAbsoluteValueVelocity);
  }

  public RigidTransform2 getPose() {
    synchronized (kinematicsLock) {
      return pose;
    }
  }

  public Vector2 getVelocity() {
    synchronized (kinematicsLock) {
      return velocity;
    }
  }

  public double getAngularVelocity() {
    synchronized (kinematicsLock) {
      return angularVelocity;
    }
  }

  public void drive(
      Vector2 translationalVelocity, double rotationalVelocity, boolean isFieldOriented) {
    synchronized (stateLock) {
      // Vector2 slowTranslationalVelocity = new Vector2(translationalVelocity.x / 2,
      // translationalVelocity.y / 2);
      driveSignal =
          new HolonomicDriveSignal(
              translationalVelocity.scale(0.5), rotationalVelocity, isFieldOriented);
    }
  }

  public void resetPose(RigidTransform2 pose) {
    synchronized (kinematicsLock) {
      this.pose = pose;
      swerveOdometry.resetPose(pose);
    }
  }

  public void resetGyroAngle(Rotation2 angle) {
    synchronized (sensorLock) {
      gyroscope.setAdjustmentAngle(gyroscope.getUnadjustedAngle().rotateBy(angle.inverse()));
    }
  }

  public double getAverageAbsoluteValueVelocity() {
    double averageVelocity = 0;
    for (var module : modules) {
      averageVelocity += Math.abs(module.getDriveVelocity());
    }
    return averageVelocity / 4;
  }

  private void updateOdometry(double time, double dt) {
    Vector2[] moduleVelocities = new Vector2[modules.length];
    for (int i = 0; i < modules.length; i++) {
      var module = modules[i];

      moduleVelocities[i] =
          Vector2.fromAngle(Rotation2.fromRadians(module.getSteerAngle()))
              .scale(module.getDriveVelocity() * 39.37008);
    }

    Rotation2 angle;
    double angularVelocity;
    synchronized (sensorLock) {
      angle = gyroscope.getAngle();
      angularVelocity = gyroscope.getRate();
    }

    ChassisVelocity velocity = swerveKinematics.toChassisVelocity(moduleVelocities);

    synchronized (kinematicsLock) {
      this.pose = swerveOdometry.update(angle, dt, moduleVelocities);
      if (latencyCompensationMap.size() > MAX_LATENCY_COMPENSATION_MAP_ENTRIES) {
        latencyCompensationMap.remove(latencyCompensationMap.firstKey());
      }
      latencyCompensationMap.put(new InterpolatingDouble(time), pose);
      this.velocity = velocity.getTranslationalVelocity();
      this.angularVelocity = angularVelocity;
    }
  }

  private void updateModules(HolonomicDriveSignal driveSignal, double dt) {
    ChassisVelocity chassisVelocity;
    if (driveSignal == null) {
      chassisVelocity = new ChassisVelocity(Vector2.ZERO, 0.0);
    } else if (driveSignal.isFieldOriented()) {
      chassisVelocity =
          new ChassisVelocity(
              driveSignal.getTranslation().rotateBy(getPose().rotation.inverse()),
              driveSignal.getRotation());
    } else {
      chassisVelocity =
          new ChassisVelocity(driveSignal.getTranslation(), driveSignal.getRotation());
    }

    Vector2[] moduleOutputs = swerveKinematics.toModuleVelocities(chassisVelocity);
    SwerveKinematics.normalizeModuleVelocities(moduleOutputs, 1);
    for (int i = 0; i < moduleOutputs.length; i++) {
      var module = modules[i];
      module.set(moduleOutputs[i].length * 12.0, moduleOutputs[i].getAngle().toRadians());
    }
  }

  public RigidTransform2 getPoseAtTime(double timestamp) {
    synchronized (kinematicsLock) {
      if (latencyCompensationMap.isEmpty()) {
        return RigidTransform2.ZERO;
      }
      return latencyCompensationMap.getInterpolated(new InterpolatingDouble(timestamp));
    }
  }

  @Override
  public void update(double time, double dt) {
    updateOdometry(time, dt);

    HolonomicDriveSignal driveSignal;
    Optional<HolonomicDriveSignal> trajectorySignal =
        follower.update(getPose(), getVelocity(), getAngularVelocity(), time, dt);
    if (trajectorySignal.isPresent()) {
      driveSignal = trajectorySignal.get();
      driveSignal =
          new HolonomicDriveSignal(
              driveSignal.getTranslation().scale(1.0 / RobotController.getBatteryVoltage()),
              driveSignal.getRotation() / RobotController.getBatteryVoltage(),
              driveSignal.isFieldOriented());
    } else {
      synchronized (stateLock) {
        driveSignal = this.driveSignal;
      }
    }

    updateModules(driveSignal, dt);
  }

  @Override
  public void periodic() {
    RigidTransform2 pose = getPose();
    odometryXEntry.setDouble(pose.translation.x);
    odometryYEntry.setDouble(pose.translation.y);
    odometryAngleEntry.setDouble(getPose().rotation.toDegrees());
  }

  public HolonomicMotionProfiledTrajectoryFollower getFollower() {
    return follower;
  }

  private final Pigeon2 m_pigeon2 = new Pigeon2(DRIVETRAIN_PIGEON_ID);

  public double yawTrim = 1.0;
  public double yawOffset = 0.0;

  public double zeroGyroscope() {
    yawOffset = yawOffset + m_pigeon2.getYaw();
    return yawOffset;
  }

  public double trimGyroscopeRight() {
    return yawTrim;
  }

  public double trimGyroscopeLeft() {
    return -yawTrim;
  }

  public Rotation2d getGyroscopeRotation() {
    return Rotation2d.fromDegrees(
        m_pigeon2.getYaw() + trimGyroscopeLeft() + trimGyroscopeRight() + zeroGyroscope());
  }
}
