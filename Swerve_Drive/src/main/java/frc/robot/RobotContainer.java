package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeAndIndexer;
import frc.robot.subsystems.ledControl;
import frc.robot.util.AutonomousChooser;
import frc.robot.util.AutonomousTrajectories;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private AutonomousTrajectories autonomousTrajectories;
  private final AutonomousChooser autonomousChooser;

  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  private final ledControl m_LedControl;
  private final IntakeAndIndexer m_IntakeAndIndexer = new IntakeAndIndexer();
  // add vision

  private final XboxController m_controller = new XboxController(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
      try {
        autonomousTrajectories = new AutonomousTrajectories(DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS);
    } catch (IOException e) {
        e.printStackTrace();
    }
    autonomousChooser = new AutonomousChooser(autonomousTrajectories);
    
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    CommandScheduler.getInstance().registerSubsystem(m_drivetrainSubsystem);
    m_LedControl = new ledControl(m_drivetrainSubsystem);
    CommandScheduler.getInstance().registerSubsystem(m_LedControl);
    CommandScheduler.getInstance().registerSubsystem(m_IntakeAndIndexer);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            m_drivetrainSubsystem,
            new DefaultDriveCommand(
                m_drivetrainSubsystem,
                () ->
                    -modifyAxis(m_controller.getLeftY())
                        * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                () ->
                    -modifyAxis(m_controller.getLeftX())
                        * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                () ->
                    -modifyAxis(
                            -(m_controller.getLeftTriggerAxis()
                                - m_controller.getRightTriggerAxis()))
                        * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND));
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Back button zeros the gyroscope
    new Button(m_controller::getBackButton)
        // No requirements because we don't need to interrupt anything
        .whenPressed(m_drivetrainSubsystem::zeroGyroscope);
    new Button(m_controller::getRightBumper).whenPressed(m_drivetrainSubsystem::trimGyroscopeRight);
    new Button(m_controller::getLeftBumper).whenPressed(m_drivetrainSubsystem::trimGyroscopeLeft);
    new Button(m_controller::getBButton).whenPressed(m_IntakeAndIndexer::loadTopBall);
    new Button(m_controller::getBButton).whenReleased(m_IntakeAndIndexer::indexerAlwaysOn);
    new Button(m_controller::getAButton).whenPressed(m_IntakeAndIndexer::toggleIntake);
    new Button(m_controller::getRightStickButton)
        .whenPressed(m_IntakeAndIndexer::extendIntakeSubsystem);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonomousChooser.getCommand(this);
  }

  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private static double modifyAxis(double value) {
    // Deadband
    value = deadband(value, 0.05);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return m_drivetrainSubsystem;
  }
}
