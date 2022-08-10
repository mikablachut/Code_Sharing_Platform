package com.codeSharingPlatform.controllers;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final CodeService codeService;

    @Autowired
    public ApiController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{id}")
    public Code getCode(@PathVariable Long id, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        try {
            return codeService.findCodeByID(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/code/latest")
    public List<Code> getListOfLatestCode() {
        try {
            return codeService.getLatestCode();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/code/new")
    public ResponseEntity<String> addCode(@RequestBody Code code) {
        try {
            Code createdCode = codeService.save(new Code(code.getId(), code.getCode(),
                    code.formatDate(LocalDateTime.now())));
            return new ResponseEntity<>("{\"id\": " + createdCode.getId() + "}", HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
