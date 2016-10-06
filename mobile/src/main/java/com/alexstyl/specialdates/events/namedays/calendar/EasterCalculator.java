package com.alexstyl.specialdates.events.namedays.calendar;

import com.alexstyl.specialdates.date.Date;

public class EasterCalculator {

    /**
     * Calculates the date of the easter Sunday for the given year
     */
    public Date calculateEasterForYear(int year) {
        int a = year % 4;
        int b = year % 7;
        int c = year % 19;
        int d = (19 * c + 15) % 30;
        int e = (2 * a + 4 * b - d + 34) % 7;
        int month = (int) Math.floor((d + e + 114) / 31);
        int day = ((d + e + 144) % 31) + 1;
        day++;
        return Date.on(day, month, year)
                .addDay(13);
    }
}
