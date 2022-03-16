/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import frc.robot.loops.Looper;
import frc.common.robot.UpdateManager;

public class Robot extends TimedRobot {
  // private AddressableLED ringLed;
  // private AddressableLEDBuffer ringLEDBuffer;
  // private int i;

  // private LEDPerimeter ledPerimeter =
  //     new LEDPerimeter(Constants.frameWidth, Constants.frameLength, Constants.LEDPerMeter);

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private UpdateManager m_updateManager =
      new UpdateManager(m_robotContainer.getDrivetrainSubsystem()); // update manager for drive only

  @Override
  public void robotInit() {

    m_updateManager.startLoop(5.0e-3);

    // ringLed = new AddressableLED(Constants.LED_ID);

    // ringLEDBuffer = new AddressableLEDBuffer(60);
    // ringLed.setLength(ringLEDBuffer.getLength());

    // ringLed.start();
    m_robotContainer = new RobotContainer();
    // i = 0;

    // SmartDashboard.putNumber("ledNUM", i);

    // ringLEDBuffer.setRGB(5, 255, 255, 255);
    // ringLEDBuffer.setRGB(i, 255, 255, 255);
  }

  @Override
  public void robotPeriodic() {
    // int ki = (int) SmartDashboard.getNumber("ledNUM", 0);
    // if ((ki != i)) {
    //   ringLEDBuffer.setRGB(ki, 255, 255, 255);
    //   i = ki;
    // }
    // ringLed.setData(ringLEDBuffer);
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {

    m_robotContainer.getIntakeAndIndexer().setupCompressor();

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
