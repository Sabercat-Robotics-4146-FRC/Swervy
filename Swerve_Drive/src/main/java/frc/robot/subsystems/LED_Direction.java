package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
// import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED_Direction extends SubsystemBase {

  private final AddressableLED m_LedStrip = new AddressableLED(LED_Input);
  private final AddressableLEDBuffer m_LedBuffer = new AddressableLEDBuffer(LED_Length);
  private final PigeonIMU m_pigeon = new PigeonIMU(DRIVETRAIN_PIGEON_ID);

  public void zeroGyroscope() {
    m_pigeon.setFusedHeading(0.0);
  }
    // m_pigeon.getFusedHeading();

  public synchronized void TrueNorth() {
    
    m_LedStrip.setLength(m_LedBuffer.getLength());
    m_LedStrip.setData(m_LedBuffer);
    m_LedStrip.start();
    
    int i = (int) m_pigeon.getFusedHeading() / 10;

    m_LedBuffer.setRGB(i, 100, 100, 100);
    m_LedBuffer.setRGB(i-1, 0, 0, 0);
    m_LedStrip.stop();

  }

  public synchronized void FixNorth() {

  }

          // CODE TO MOVE LED SIDE TO SIDE

          // private int i = 1;
  
          // public synchronized void TurnOn() {
          //   m_LedStrip.setLength(m_LedBuffer.getLength());
          //   m_LedStrip.setData(m_LedBuffer);
          //   m_LedStrip.start();
  
          //   m_LedBuffer.setRGB(i, 0, 255, 0);
          //   m_LedStrip.stop();
          // }

          // public synchronized void MoveDotRight() {
          //   m_LedStrip.setLength(m_LedBuffer.getLength());
          //   m_LedStrip.setData(m_LedBuffer);
          //   m_LedStrip.start();

          //   i += 1;

          //   m_LedBuffer.setRGB(i, 0, 255, 0);
          //   m_LedBuffer.setRGB(i - 1, 0, 0, 0);
          //   m_LedStrip.stop();
          // }

          // public synchronized void MoveDotLeft() {
          //   m_LedStrip.setLength(m_LedBuffer.getLength());
          //   m_LedStrip.setData(m_LedBuffer);
          //   m_LedStrip.start();

          //   i -= 1;

          //   m_LedBuffer.setRGB(i, 0, 255, 0);
          //   m_LedBuffer.setRGB(i + 0, 0, 0, 0);
          //   m_LedStrip.stop();
          // }

          // public synchronized void TurnOff() {
          //   m_LedStrip.setLength(m_LedBuffer.getLength());
          //   m_LedStrip.setData(m_LedBuffer);
          //   m_LedStrip.start();

          //   for (int i = 0; i < m_LedBuffer.getLength(); i++) {
          //       m_LedBuffer.setRGB(i, 0, 0, 0);
          //       m_LedStrip.stop();
          //     }
          // }

  @Override
  public void periodic() {
    TrueNorth();
    // TurnOn();
    // MoveDotRight();
    // MoveDotLeft();
    // TurnOff();
  }
}
