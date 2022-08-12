package com.codeSharingPlatform.controllers;

import com.codeSharingPlatform.entities.Code;
import com.codeSharingPlatform.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class WebController {
    private final CodeService codeService;

    @Autowired
    public WebController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{id}")
    public String getCode(@PathVariable("id") String id, Model model) {
        Code code = codeService.getCodeByID(id);
        model.addAttribute("code", code);
        return "index";
    }

    @GetMapping("/code/latest")
    public String getLatestCode(Model model) {
        List<Code> listOfLatestCode = codeService.getLatestCode();
        model.addAttribute("codes", listOfLatestCode);
        return "latestCode";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "newCode";
    }
}
