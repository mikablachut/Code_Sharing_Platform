package com.codeSharingPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final CodeRepository repository;
    @Autowired
    public ApiController(CodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/code")
    public Code getJsonObject(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return repository.getCode();
    }

    @PostMapping("/code/new")
    public ResponseEntity<Object> addCode(@RequestBody Code code) {
        repository.setCode(code);
        return ResponseEntity.ok("{}");
    }
}
