package frc;

import org.junit.Test;
import static org.junit.Assert.*;

import frc.lib.util.LEDPerimeter;

public class LEDTest {
    @Test public void testTest() {
        LEDPerimeter ledPerimeter = new LEDPerimeter();
        ledPerimeter.setFrameLength(32);
        ledPerimeter.setFrameWidth(24);
        ledPerimeter.setLEDPerMeter(30);
        assertEquals(true, ledPerimeter.testy());
        System.out.println(ledPerimeter.getLEDAmount());
        assertEquals(84, ledPerimeter.getLEDAmount());
    }
    @Test public void evaluateExpression() {
        LEDPerimeter ledPerimeter = new LEDPerimeter();
        ledPerimeter.setFrameLength(32);
        ledPerimeter.setFrameWidth(24);
        ledPerimeter.setLEDPerMeter(30);
        int LEDNum1 = ledPerimeter.getLEDNum(30);
        System.out.println(LEDNum1);
        assertEquals(6,LEDNum1);
    }
}
