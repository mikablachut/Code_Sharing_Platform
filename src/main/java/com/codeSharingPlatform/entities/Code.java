package com.codeSharingPlatform.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Snippets")
public class Code {

    @Id
    @Column
    @JsonIgnore
    private String id = generateId();
    @Column
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @Column
    private String date;

    @Column
    private Long time;

    @Column
    Long views;

    public String formatDate(LocalDateTime localDateTime) {
        final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss:SSS";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return localDateTime.format(formatter);
    }

    public static String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public String toString() {
        return date + "\n" + code;
    }
}
