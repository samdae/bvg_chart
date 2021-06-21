package com.braveg.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static int getCurrent(String format) {
        return Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)));
    }


}
