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

    @GetMapping("/code/{id}")
    public Code getJsonObject(@PathVariable Long id, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return repository.getCodeByID(id);
    }

    @PostMapping("/code/new")
    public ResponseEntity<Object> addCode(@RequestBody Code code) {
        return ResponseEntity.ok(repository.storeCode(code));
    }
}
