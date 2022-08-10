package com.codeSharingPlatform.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Snippets")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private Long id;
    @Column
    private String code;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @Column
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
