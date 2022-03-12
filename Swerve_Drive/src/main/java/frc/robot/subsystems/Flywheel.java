package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.LauncherConstants;

public class Flywheel {
  private CANSparkMax fLeader;
  private CANSparkMax fFollower;
  private RelativeEncoder lEncoder;
  private RelativeEncoder fEncoder;
  private SparkMaxPIDController lPIDController;
  private SparkMaxPIDController fPIDController;
  private double targetVelocity = 0;
  private int rollingAvg = 0;

  public Flywheel() {
    fLeader = new CANSparkMax(LauncherConstants.leaderLaunchMotor, MotorType.kBrushless);
    fFollower = new CANSparkMax(LauncherConstants.followerLaunchMotor, MotorType.kBrushless);
    fFollower.setInverted(true);

    lEncoder = fLeader.getEncoder();
    fEncoder = fFollower.getEncoder();

    lPIDController = fLeader.getPIDController();
    fPIDController = fFollower.getPIDController();

    lPIDController.setP(LauncherConstants.proportialPIDConstant);
    lPIDController.setI(LauncherConstants.integralPIDConstant);
    lPIDController.setD(LauncherConstants.derivativePIDConstant);
    lPIDController.setIZone(LauncherConstants.integralPIDConstant);
    lPIDController.setFF(LauncherConstants.leftFeedForwardPIDConstant);
    lPIDController.setOutputRange(LauncherConstants.minPIDOutput, LauncherConstants.maxPIDOutput);

    fPIDController.setP(LauncherConstants.proportialPIDConstant);
    fPIDController.setI(LauncherConstants.integralPIDConstant);
    fPIDController.setD(LauncherConstants.derivativePIDConstant);
    fPIDController.setIZone(LauncherConstants.integralPIDConstant);
    fPIDController.setFF(LauncherConstants.rightFeedForwardPIDConstant);
    fPIDController.setOutputRange(LauncherConstants.minPIDOutput, LauncherConstants.maxPIDOutput);
    stop();

    fLeader.burnFlash();
    fFollower.burnFlash();
  }

  public void setVelocity(double velocity) {
    targetVelocity = velocity;
    lPIDController.setReference(targetVelocity, ControlType.kVelocity);
    fPIDController.setReference(targetVelocity, ControlType.kVelocity);
  }

  public void setSpeed(double speed) {
    fLeader.set(speed);
    fFollower.set(speed);
  }

  public void stop() {
    setSpeed(0.0);
  }

  // Finds the average velocity of the two motors
  public double getVelocity() {
    double sum = lEncoder.getVelocity() + fEncoder.getVelocity();
    double average = sum / 2;
    return average;
  }

  // For the target velocity
  public boolean isOnTarget() {
    boolean leftOnTarget =
        Math.abs(targetVelocity - lEncoder.getVelocity()) <= LauncherConstants.velocityPIDTolerance;
    boolean rightOnTarget =
        Math.abs(targetVelocity - fEncoder.getVelocity()) <= LauncherConstants.velocityPIDTolerance;
    return (rightOnTarget && leftOnTarget);
  }

  public boolean isOnTargetAverage(int percent) {
    if (percent > 10) {
      percent = 10;
    } else if (percent < 0) {
      percent = 0;
    }

    if (rollingAvg >= percent) {
      return true;
    }
    return false;
  }

  public static double distanceToVelocity(double distance) {
    // TODO tune distance convertion
    return 0.0;
  }

  public void periodic() {
    if (isOnTarget()) {
      if (rollingAvg < 10) {
        rollingAvg++;
      }
    } else if (rollingAvg > 0) {
      if (rollingAvg > 0) {
        rollingAvg--;
      }
    }
    SmartDashboard.putNumber("Leader Velocity", lEncoder.getVelocity());
    SmartDashboard.putNumber("Follower Velocity", fEncoder.getVelocity());
    SmartDashboard.putNumber("Average Velocity", getVelocity());
    SmartDashboard.putBoolean("Launcher On Target", isOnTarget());
    SmartDashboard.putBoolean("Avg Launcher On Target", isOnTargetAverage(7));
    SmartDashboard.putNumber("Target Velocity", targetVelocity);
  }
}
