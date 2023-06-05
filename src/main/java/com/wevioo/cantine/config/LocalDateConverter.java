package com.wevioo.cantine.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public LocalDateTime date(String source){
        LocalDateTime dateToGet = LocalDateTime.parse(source);
        LocalDateTime date = LocalDateTime.parse(dateToGet.toString().substring(0,9));
        System.out.println(date);
        return date;
    }
}
