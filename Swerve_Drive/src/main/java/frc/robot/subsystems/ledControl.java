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
  private int ledNUM;
  private int ParkersSpecialRedLed;

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
    ledNUM = ledPerimeter.getLEDID(drive.getGyroscopeRotation().getDegrees());
    ParkersSpecialRedLed = ledPerimeter.getLEDID(drive.getGyroscopeRotation().getDegrees() + 180);
    SmartDashboard.putNumber("drive gyro", drive.getGyroscopeRotation().getDegrees());
    SmartDashboard.putNumber("ledID", ledNUM);
    SmartDashboard.putNumber("Parker's Red LED", ParkersSpecialRedLed);
    for (int i = 0; i < ringLEDBuffer.getLength(); i++) {
      ringLEDBuffer.setRGB(i, 0, 0, 0);
    }

    for (int i = 5; i > -5; i--) {
      int ledID = ledNUM - i;
      int ledID2 = ParkersSpecialRedLed - i;
      if (ledID < 0) {
        ledID += ledPerimeter.getLEDAmount();
      }
      if (ledID2 < 0) {
        ledID2 += ledPerimeter.getLEDAmount();
      }
      ledID %= ledPerimeter.getLEDAmount();
      ledID2 %= ledPerimeter.getLEDAmount();
      ringLEDBuffer.setRGB(ledID, 255, 255, 255);
      ringLEDBuffer.setRGB(ledID2, 255, 0, 0);
    }
  }

  @Override
  public void periodic() {
    getLEDid();
    ringLed.setData(ringLEDBuffer);
  }
}
