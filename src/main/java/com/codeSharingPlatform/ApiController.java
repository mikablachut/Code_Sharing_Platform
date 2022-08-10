package com.codeSharingPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final CodeRepository repository;
    @Autowired
    public ApiController(CodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/code/{id}")
    public Code getCode(@PathVariable Long id, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        return repository.getCodeByID(id);
    }

    @GetMapping("/code/latest")
    public List<Code> getListOfLatestCode() {
        return repository.getLatestCode();
    }

    @PostMapping("/code/new")
    public ResponseEntity<Object> addCode(@RequestBody Code code) {
        return ResponseEntity.ok(repository.storeCode(code));
    }
}
