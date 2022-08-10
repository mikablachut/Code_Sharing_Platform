package com.codeSharingPlatform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    private LocalDateTime date;

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
