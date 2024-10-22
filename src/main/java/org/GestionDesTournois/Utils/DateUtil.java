package org.GestionDesTournois.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate dateValidation(String dateStr) {
        return LocalDate.parse(dateStr, format);
    }

    public static boolean isValidDate(String dateStr) {
        try {
            dateValidation(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
