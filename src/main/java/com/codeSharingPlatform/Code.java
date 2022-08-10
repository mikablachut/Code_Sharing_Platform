package com.codeSharingPlatform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    private String date;

    public String formatDate(LocalDateTime localDateTime) {
        final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss:SSS";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return localDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return date + "\n" + code;
    }
}
