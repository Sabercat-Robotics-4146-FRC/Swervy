package frc;

import org.junit.Test;
import static org.junit.Assert.*;

import frc.lib.util.LEDPerimeter;

public class LEDTest {
    @Test public void testy() {
        LEDPerimeter ledPerimeter1 = new LEDPerimeter(28,28,30, false);
        System.out.println(ledPerimeter1.getLEDAmount());
        int test = ledPerimeter1.getLEDID(90);
        System.out.println(test);
        test = ledPerimeter1.getLEDID(270);
        System.out.println(test);
        test = ledPerimeter1.getLEDID(90);
        System.out.println(test);
        test = ledPerimeter1.getLEDID(120);
        System.out.println(test);
        test = ledPerimeter1.getLEDID(150);
        System.out.println(test);
        test = ledPerimeter1.getLEDID(180);
        System.out.println(test);
        assertEquals(true, ledPerimeter1.testy());
    }
//     // @Test public void evaluateExpression() {
//     //     LEDPerimeter ledPerimeter = new LEDPerimeter(24,32,30);
//     //     int LEDNum1 = ledPerimeter.getLEDNum(30);
//     //     System.out.println(LEDNum1);
//     //     assertEquals(6,LEDNum1);
//     // }
}
