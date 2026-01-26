package edu.du.ict4305.myCalendar;

import static org.junit.Assert.*;
import org.junit.Test;

public class MyDateTest {

    @Test
    public void testDefaultConstructor() {
        MyDate date = new MyDate();
        assertEquals(1, date.getDay());
        assertEquals(1, date.getMonth());
        assertEquals(1970, date.getYear());
    }

    @Test
    public void testParameterizedConstructor() {
        MyDate date = new MyDate(25, 12, 2023);
        assertEquals(25, date.getDay());
        assertEquals(12, date.getMonth());
        assertEquals(2023, date.getYear());
    }

    @Test
    public void testCopyConstructor() {
        MyDate original = new MyDate(10, 10, 2020);
        MyDate copy = new MyDate(original);
        assertEquals(original.getDay(), copy.getDay());
        assertEquals(original.getMonth(), copy.getMonth());
        assertEquals(original.getYear(), copy.getYear());
    }

    @Test
    public void testLeapYear() {
        assertTrue(MyDate.isLeapYear(2024)); // Leap year
        assertFalse(MyDate.isLeapYear(1900)); // Not a leap year
        assertTrue(MyDate.isLeapYear(2000)); // Leap year
        assertFalse(MyDate.isLeapYear(2023)); // Not a leap year
    }

    @Test
    public void testGetLastDayOfMonth() {
        assertEquals(31, MyDate.getLastDayOfMonth(1, 2023)); // January
        assertEquals(28, MyDate.getLastDayOfMonth(2, 2023)); // February (non-leap year)
        assertEquals(29, MyDate.getLastDayOfMonth(2, 2024)); // February (leap year)
        assertEquals(30, MyDate.getLastDayOfMonth(4, 2023)); // April
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMonth() {
        new MyDate(1, 15, 2023); // Invalid month
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDay() {
        new MyDate(31, 2, 2023); // Invalid day for February
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidYear() {
        new MyDate(1, 1, 1200); // Invalid year (before 1583)
    }

    @Test
    public void testJulianConversion() {
        MyDate date = new MyDate(1, 1, 2000);
        assertEquals(1, date.getDay());
        assertEquals(1, date.getMonth());
        assertEquals(2000, date.getYear());

        MyDate date2 = new MyDate(29, 2, 2024); // Leap year
        assertEquals(29, date2.getDay());
        assertEquals(2, date2.getMonth());
        assertEquals(2024, date2.getYear());
    }
}