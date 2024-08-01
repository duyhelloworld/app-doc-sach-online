package huce.edu.vn.appdocsach.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtils {
    
    // Chuyển từ LocalDate (java.time) sang Date (java.util) 
    public static Date getDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Chuyển từ LocalDateTime (java.time) sang Date (java.util) 
    public static Date getDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
