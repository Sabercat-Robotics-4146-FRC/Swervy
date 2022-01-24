package frc;

import org.junit.Test;
import static org.junit.Assert.*;

import frc.lib.util.LEDPerimeter;

public class LEDTest {
    @Test public void testTest() {
        LEDPerimeter ledPerimeter = new LEDPerimeter(24,32,30);
        assertEquals(true, ledPerimeter.testy());
        System.out.println(ledPerimeter.getLEDAmount());
        assertEquals(84, ledPerimeter.getLEDAmount());
    }
    @Test public void testy() {
        LEDPerimeter ledPerimeter1 = new LEDPerimeter(24,32,30);
        int cunt = ledPerimeter1.getLEDNum(170);
        assertEquals(true, ledPerimeter1.testy());
        System.out.println(ledPerimeter1.getEdgePointX());
        System.out.println(ledPerimeter1.getEdgePointY());
        System.out.println(cunt);
        assertEquals(84, ledPerimeter1.getLEDAmount());
    }
    // @Test public void evaluateExpression() {
    //     LEDPerimeter ledPerimeter = new LEDPerimeter(24,32,30);
    //     int LEDNum1 = ledPerimeter.getLEDNum(30);
    //     System.out.println(LEDNum1);
    //     assertEquals(6,LEDNum1);
    // }
}
