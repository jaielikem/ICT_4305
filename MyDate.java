package edu.du.ict4305.myCalendar;

public class MyDate {
    private int julianNumber;

    // Default constructor: January 1, 1970
    public MyDate() {
        this.julianNumber = toJulianNumber(1, 1, 1970);
    }

    // Copy constructor
    public MyDate(MyDate date) {
        this.julianNumber = date.julianNumber;
    }

    // Parameterized constructor
    public MyDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
        if (day < 1 || day > getLastDayOfMonth(month, year)) {
            throw new IllegalArgumentException("Invalid day: " + day);
        }
        if (year < 1583) { // Gregorian calendar started in 1583
            throw new IllegalArgumentException("Year must be 1583 or later.");
        }
        this.julianNumber = toJulianNumber(day, month, year);
    }

    // Getters
    public int getDay() {
        return fromJulianNumber()[0];
    }

    public int getMonth() {
        return fromJulianNumber()[1];
    }

    public int getYear() {
        return fromJulianNumber()[2];
    }

    // Check if a year is a leap year
    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

    // Get the last day of a given month in a specific year
    public static int getLastDayOfMonth(int month, int year) {
        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    // Convert a date to a Julian number
    private static int toJulianNumber(int day, int month, int year) {
        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        return day + ((153 * m + 2) / 5) + (365 * y) + (y / 4) - (y / 100) + (y / 400) - 32045;
    }

    // Convert a Julian number to a date (day, month, year)
    private int[] fromJulianNumber() {
        int j = julianNumber;
        int f = j + 1401 + (((4 * j + 274277) / 146097) * 3) / 4 - 38;
        int e = 4 * f + 3;
        int g = (e % 1461) / 4;
        int h = 5 * g + 2;
        int day = (h % 153) / 5 + 1;
        int month = ((h / 153 + 2) % 12) + 1;
        int year = (e / 1461) - 4716 + (14 - month) / 12;
        return new int[]{day, month, year};
    }
}
