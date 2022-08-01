package com.codeSharingPlatform;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class WebController {
    String code = "public static void main(String[] args) {\n" +
            "\tSpringApplication.run(CodeSharingPlatform.class, args);\n}";

    @GetMapping(path = "/code", produces = MediaType.TEXT_HTML_VALUE)
    public String getHtml(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/html");
        Code myCode = new Code(code);
        return myCode.toString();
    }
}
