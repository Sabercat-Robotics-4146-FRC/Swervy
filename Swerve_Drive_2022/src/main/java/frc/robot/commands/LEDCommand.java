package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED_Direction;

public class LEDCommand extends CommandBase {
  private final LED_Direction m_LED_Direction;

  public LEDCommand(LED_Direction LED_direction) {
    this.m_LED_Direction = LED_direction;
  }

  @Override
  public void execute() {
    m_LED_Direction.SetLEDColor();
    m_LED_Direction.TurnOff();
  }
}
