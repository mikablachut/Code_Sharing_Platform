package com.codeSharingPlatform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    private String code;

    @Override
    public String toString() {
        return "<html><head>" + "<title>Code</title>" + "</head>" + "<body>" + "<pre>" +
                code + "</pre>" + "</body>" + "</html>";
    }
}
