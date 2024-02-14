package com.github.pyrinoff.somebot.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface DateUtil {

    static LocalDate getLocalDate(final String birthDate, final String pattern) {
        try {
            return LocalDate.parse(birthDate, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    static boolean isAgeOver(LocalDate birthDate, int age, boolean strict) {
        return compareTwoDates(birthDate, LocalDate.now(), age, strict);
    }

    static boolean compareTwoDates(LocalDate firstDate, LocalDate secondDate, int age, boolean strict) {
        Period ageToCheck = Period.between(firstDate, secondDate);
        return strict ? ageToCheck.getYears() >= age : ageToCheck.getYears() > age;
    }

}
