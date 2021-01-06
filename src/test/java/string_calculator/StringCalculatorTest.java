package string_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    StringCalculator calculator = new StringCalculator();

    @Test
    void emptyStringShouldReturn0() {
        assertEquals(0, calculator.add(""));
    }

    @Test
    void numberStringShouldReturnSameNumber() {
        assertAll(
                () -> assertEquals(1, calculator.add("1")),
                () -> assertEquals(5, calculator.add("5")),
                () -> assertEquals(999, calculator.add("999"))
        );
    }

    @Test
    void numbersCommaDelimitedShouldBeSummed() {
        assertAll(
                () -> assertEquals(3, calculator.add("1,2")),
                () -> assertEquals(25, calculator.add("10,15")),
                () -> assertEquals(45, calculator.add("1,2,3,4,5,10,20"))
        );
    }

    @Test
    void numbersNewlineDelimitedShouldBeSummed() {
        assertAll(
                () -> assertEquals(10, calculator.add("1\n2,7")),
                () -> assertEquals(24, calculator.add("11\n13")),
                () -> assertEquals(100, calculator.add("94\n3\n2\n1"))
        );

    }

    @Test
    void numbersCustomDelimitedShouldBeSummed() {
        assertAll(
                () -> assertEquals(3, calculator.add("//|\n1|2")),
                () -> assertEquals(3, calculator.add("//;\n1;2")),
                () -> assertEquals(8, calculator.add("//[@@]\n1@@2@@5")),
                () -> assertEquals(15, calculator.add("//[*][%]\n1*2%3*9")),
                () -> assertEquals(18, calculator.add("//[***][%%][Aa@a#X]\n1***2***3%%2Aa@a#X10")),
                () -> assertEquals(13, calculator.add("//[.{}()<>*+-=!?^$]\n1.{}()<>*+-=!?^$2.{}()<>*+-=!?^$10"))
        );
    }

    @Test
    void negativeNumberShouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> calculator.add("-1"));
        assertEquals("Negatives not allowed [-1]", exception.getMessage());
    }

    @Test
    void negativeNumbersShouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> calculator.add("-5,10\n-15"));

        String message = exception.getMessage();
        assertAll(
                () -> assertTrue(message.contains("-1")),
                () -> assertTrue(message.contains("-15")),
                () -> assertTrue(message.contains("Negatives not allowed")),
                () -> assertFalse(message.contains("10"))
        );
    }

    @Test
    void numbersGreaterThan1000ShouldBeIgnored() {
        assertAll(
                () -> assertEquals(17, calculator.add("5,12,1001")),
                () -> assertEquals(26, calculator.add("14124,22\n4,1214"))
        );
    }
}
