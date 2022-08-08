package com.codeSharingPlatform;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class CodeRepository {
    String simpleCode = "public class CodeSharingPlatform {\n" +
            "    public static void main(String[] args) {\n" +
            "        SpringApplication.run(CodeSharingPlatform.class, args);\n" +
            "    }\n" +
            "\n" +
            "}";
    private Code code = new Code(simpleCode, LocalDateTime.now());

    public void setCode(Code code) {
        this.code = new Code(code.getCode(), LocalDateTime.now());
    }
    public Code getCode() {
        return code;
    }
}
