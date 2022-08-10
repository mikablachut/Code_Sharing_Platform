package com.codeSharingPlatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebController {
    private final CodeRepository repository;

    @Autowired
    public WebController(CodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/code/{id}")
    public String getCode(@PathVariable("id") Long id, Model model) {
        String code = repository.getCodeByID(id).getCode();
        String time = repository.getCodeByID(id).getDate();
        model.addAttribute("date", time);
        model.addAttribute("code", code);
        return "index";
    }

    @GetMapping("/code/latest")
    public String getLatestCode(Model model) {
        List<Code> listOfLatestCode = repository.getLatestCode();
        model.addAttribute("codes", listOfLatestCode);
        return "latestCode";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "newCode";
    }
}
