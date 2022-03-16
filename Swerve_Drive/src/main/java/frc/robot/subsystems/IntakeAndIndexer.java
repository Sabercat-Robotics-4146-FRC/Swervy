package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class IntakeAndIndexer implements Subsystem {

  private final CANSparkMax indexerBottom = new CANSparkMax(IndexerBottom, MotorType.kBrushless);
  private final CANSparkMax indexerTop = new CANSparkMax(IndexerTop, MotorType.kBrushless);
  private final DigitalInput indexerBottomSensor = new DigitalInput(IndexerBottomSensor);
  private final DigitalInput indexerTopSensor = new DigitalInput(IndexerTopSensor);
  private final CANSparkMax intakeMotor = new CANSparkMax(IntakeMotor, MotorType.kBrushless);
  private boolean intakeActive = false;
  private final DoubleSolenoid intakePiston =
      new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
  private boolean intakePistonExtended = false;

  public void indexerAlwaysOn() {
    if (indexerTopSensor.get() == true) {
      indexerBottom.set(.1);
      indexerTop.set(.1);
    } else if (indexerTopSensor.get() == false && indexerBottomSensor.get() == true) {
      indexerBottom.set(.1);
      indexerTop.set(0.0);
    } else {
      indexerBottom.set(0.0);
      indexerTop.set(0.0);
    }
  }

  public void loadTopBall() {
    indexerBottom.stopMotor();
    indexerTop.set(.1);
  }

  public void toggleIntake() {
    if (intakeActive == false) {
      intakeMotor.set(.2);
      intakeActive = true;
    } else if (intakeActive == true) {
      intakeMotor.stopMotor();
      intakeActive = false;
    }
  }

  public void extendIntakeSubsystem() {
    if (intakePistonExtended == false) {
      intakePiston.set(Value.kForward);
      intakePistonExtended = true;
    } else if (intakePistonExtended == true) {
      intakePiston.set(Value.kReverse);
      intakePistonExtended = false;
    }
  }

  @Override
  public void periodic() {
     indexerAlwaysOn();

     SmartDashboard.putBoolean("Indexer Top Sensor", indexerTopSensor.get());
     SmartDashboard.putBoolean("Indexer Bottom Sensor", indexerBottomSensor.get());
  }
}
