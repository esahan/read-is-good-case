package com.ege.readingisgood.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
