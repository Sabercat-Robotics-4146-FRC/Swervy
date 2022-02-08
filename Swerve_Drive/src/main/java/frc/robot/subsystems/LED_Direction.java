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
    i -= 1;
    if (i <= 0) {
      i = 36 + i;
    }

    int[][] placeholderName = new int[36][];

    placeholderName[0] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[1] = new int[] {0,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[2] = new int[] {1,0,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[3] = new int[] {1,2,0,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[4] = new int[] {1,2,3,0,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[5] = new int[] {1,2,3,4,0,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[6] = new int[] {1,2,3,4,5,0,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[7] = new int[] {1,2,3,4,5,6,0,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[8] = new int[] {1,2,3,4,5,6,7,0,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[9] = new int[] {1,2,3,4,5,6,7,8,0,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[10] = new int[] {1,2,3,4,5,6,7,8,9,0,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[11] = new int[] {1,2,3,4,5,6,7,8,9,10,0,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[12] = new int[] {1,2,3,4,5,6,7,8,9,10,11,0,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[13] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,0,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[14] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,0,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[15] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[16] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[17] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,0,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[18] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,0,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[19] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,0,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[20] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,0,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[21] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,0,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[22] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,0,23,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[23] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,0,24,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[24] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,0,25,26,27,28,29,30,31,32,33,34,35};
    placeholderName[25] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,0,26,27,28,29,30,31,32,33,34,35};
    placeholderName[26] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,0,27,28,29,30,31,32,33,34,35};
    placeholderName[27] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,0,28,29,30,31,32,33,34,35};
    placeholderName[28] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,0,29,30,31,32,33,34,35};
    placeholderName[29] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,0,30,31,32,33,34,35};
    placeholderName[30] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,0,31,32,33,34,35};
    placeholderName[31] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,32,33,34,35};
    placeholderName[32] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,0,33,34,35};
    placeholderName[33] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,0,34,35};
    placeholderName[34] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,0,35};
    placeholderName[35] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,0};

    m_LedBuffer.setRGB(i, 100, 100, 100);
    // for (int x = 0; x <= 34; x++) {
    //   m_LedBuffer.setRGB(placeholderName[i][x], 0, 0, 0); 
    // }
    m_LedBuffer.setRGB(placeholderName[i][0], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][1], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][2], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][3], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][4], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][5], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][6], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][7], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][8], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][9], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][10], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][11], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][12], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][13], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][14], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][15], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][16], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][17], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][18], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][19], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][20], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][21], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][22], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][23], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][24], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][25], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][26], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][27], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][28], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][29], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][30], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][31], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][32], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][33], 0, 0, 0);
    m_LedBuffer.setRGB(placeholderName[i][34], 0, 0, 0);
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
