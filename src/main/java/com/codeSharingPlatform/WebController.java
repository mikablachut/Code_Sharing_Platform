package com.codeSharingPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {
    private final CodeRepository repository;

    @Autowired
    public WebController(CodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/code")
    public String getCode(Model model) {
        model.addAttribute("date", repository.getCode().formatDate(repository.getCode().getDate()));
        model.addAttribute("code", repository.getCode().getCode());
        return "index.html";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "newCode.html";
    }
}
