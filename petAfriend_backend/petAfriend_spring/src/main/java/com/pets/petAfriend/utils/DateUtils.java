package com.pets.petAfriend.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private DateUtils() {
    }


    public static Date toDate(final String date) throws ParseException {
        try {
            final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.parse(date);
        } catch (ParseException e) {
            log.info("Error parsing date {}", date);
            throw new ParseException("Invalid date entry " + date + ". expecting format " + DATE_FORMAT, e.getErrorOffset());
        }
    }

    public static String toString(final Date date) {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }
}
