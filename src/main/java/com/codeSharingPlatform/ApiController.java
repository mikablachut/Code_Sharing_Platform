package com.codeSharingPlatform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ApiController {
    String code = "public static void main(String[] args) {\n" +
            "\tSpringApplication.run(CodeSharingPlatform.class, args);\n}";

    @GetMapping("/api/code")
    public Code getJsonObject(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return new Code(code);
    }
}
