/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.loops.Looper;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  Looper mEnabledLooper = new Looper();
  Looper mDisabledLooper = new Looper();

  private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();

  private Joystick mController;

  public Swerve mDrive;
  public DriveTest2 mDriveTest2;

  // private boolean AButtonFlag = false;
  public boolean BButtonFlag = false;
  public boolean RBButtonFlag = false;
  public boolean XButtonFlag = false;
  public boolean YButtonFlag = false;

  public boolean intakeToggle = false;
  public boolean pneumaticsToggle = false;
  public boolean flywheelToggle = false;
  public boolean limelightToggle = false;
  public boolean shootToggle = false;

  @Override
  public void robotInit() {
    mDrive = Swerve.getInstance();
    mDriveTest2 = DriveTest2.getInstance();

    mSubsystemManager.setSubsystems(new Subsystem[] {mDrive});

    mController = new Joystick(Constants.kDriver1USBPort);

    mSubsystemManager.registerEnabledLoops(mEnabledLooper);
    mSubsystemManager.registerDisabledLoops(mDisabledLooper);
  }

  @Override
  public void robotPeriodic() {
    mSubsystemManager.outputToSmartDashboard();
  }

  @Override
  public void autonomousInit() {
    mDisabledLooper.stop();
    mEnabledLooper.start();
  }

  @Override
  public void disabledInit() {
    mEnabledLooper.stop();
    mDisabledLooper.start();
  }

  @Override
  public void teleopInit() {
    mDisabledLooper.stop();
    mEnabledLooper.start();
  }

  @Override
  public void teleopPeriodic() {

    // mDrive.setSwerveDrive(mController.getRawAxis(0), -mController.getRawAxis(1));
    mDrive.setSwerveDrive(
        mController.getRawAxis(0), -mController.getRawAxis(1), mController.getRawAxis(4));
  }
}
