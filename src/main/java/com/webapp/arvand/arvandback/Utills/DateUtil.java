package com.webapp.arvand.arvandback.Utills;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String format(Date date, String calendar, String pattern) {

        if (date == null)
            return null;

        LocalDate localDate =
                date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

        if ("fa".equalsIgnoreCase(calendar)) {

            int[] j = gregorianToJalali(
                    localDate.getYear(),
                    localDate.getMonthValue(),
                    localDate.getDayOfMonth()
            );

            return formatPattern(j[0], j[1], j[2], pattern);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return localDate.format(formatter);
    }

    private static String formatPattern(int y, int m, int d, String pattern) {

        String yyyy = String.format("%04d", y);
        String mm = String.format("%02d", m);
        String dd = String.format("%02d", d);

        return pattern
                .replace("yyyy", yyyy)
                .replace("mm", mm)
                .replace("dd", dd);
    }

    // -------- Gregorian -> Jalali --------

    public static int[] gregorianToJalali(int gy, int gm, int gd) {

        int[] g_d_m = {0,31,59,90,120,151,181,212,243,273,304,334};

        int jy;

        if (gy > 1600) {
            jy = 979;
            gy -= 1600;
        } else {
            jy = 0;
            gy -= 621;
        }

        int gy2 = (gm > 2) ? gy + 1 : gy;

        int days =
                (365 * gy)
                        + ((gy2 + 3) / 4)
                        - ((gy2 + 99) / 100)
                        + ((gy2 + 399) / 400)
                        - 80
                        + gd
                        + g_d_m[gm - 1];

        jy += 33 * (days / 12053);
        days %= 12053;

        jy += 4 * (days / 1461);
        days %= 1461;

        if (days > 365) {
            jy += (days - 1) / 365;
            days = (days - 1) % 365;
        }

        int jm, jd;

        if (days < 186) {
            jm = 1 + days / 31;
            jd = 1 + days % 31;
        } else {
            jm = 7 + (days - 186) / 30;
            jd = 1 + (days - 186) % 30;
        }

        return new int[]{jy, jm, jd};
    }
}
