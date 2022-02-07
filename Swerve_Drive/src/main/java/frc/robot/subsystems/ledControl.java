package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lib.util.LEDPerimeter;
import frc.robot.Constants;

public class ledControl implements Subsystem {
  private final AddressableLED ringLed;
  private final AddressableLEDBuffer ringLEDBuffer;
  private final DrivetrainSubsystem drive;

  private LEDPerimeter ledPerimeter =
      new LEDPerimeter(Constants.frameWidth, Constants.frameLength, Constants.LEDPerMeter, true);

  public ledControl(DrivetrainSubsystem drivetrainSubsystem) {
    this.drive = drivetrainSubsystem;
    this.ringLed = new AddressableLED(Constants.LED_ID);

    ringLEDBuffer = new AddressableLEDBuffer(ledPerimeter.getLEDAmount());
    ringLed.setLength(ringLEDBuffer.getLength());

    ringLed.start();
  }

  public void getLEDid() {
    int ledID = ledPerimeter.getLEDID(drive.getGyroscopeRotation().getDegrees());
    SmartDashboard.putNumber("drive gyro", drive.getGyroscopeRotation().getDegrees());
    SmartDashboard.putNumber("ledID", ledID);
    for (int i = 0; i < ringLEDBuffer.getLength(); i++) {
      ringLEDBuffer.setRGB(i, 0, 0, 0);
    }

    for (int i = 5; i > -5; i--) {
      ledID -= 1;
      if (ledID < 0) {
        ledID += ledPerimeter.getLEDAmount();
      }
      ledID %= ledPerimeter.getLEDAmount();
      ringLEDBuffer.setRGB(ledID, 255, 255, 255);
    }
  }

  @Override
  public void periodic() {
    getLEDid();
    ringLed.setData(ringLEDBuffer);
  }
}
