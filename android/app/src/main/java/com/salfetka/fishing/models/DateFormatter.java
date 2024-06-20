package com.salfetka.fishing.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/** Сокращает форматирование даты в строку */
public class DateFormatter {
    public static String format(Calendar date, String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        return formatter.format(date.getTime());
    }
}
