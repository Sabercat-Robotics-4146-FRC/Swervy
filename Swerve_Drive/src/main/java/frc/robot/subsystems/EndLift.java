package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class EndLift implements Subsystem {
  private final CANSparkMax liftMotorLeader =
      new CANSparkMax(LiftMotorLeader, MotorType.kBrushless);
  private final CANSparkMax liftMotorFollower =
      new CANSparkMax(LiftMotorFollower, MotorType.kBrushless);
  private final DigitalInput limitSwitch1 = new DigitalInput(LiftLimitSwitch1);
  private final DigitalInput limitSwitch2 = new DigitalInput(LiftLimitSwitch2);

  public void reverseSpool() {
    if (!limitSwitch1.get() && !limitSwitch2.get()) {
      liftMotorLeader.set(-.5);
      liftMotorFollower.set(-.5);
    } else {
      liftMotorLeader.set(0);
      liftMotorFollower.set(0);
    }
  }

  @Override
  public void periodic() {
    reverseSpool();
  }
}
