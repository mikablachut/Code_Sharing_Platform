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
        String date = codeService.getCodeByID(id).getDate();
        String views = String.valueOf(codeService.getCodeByID(id).getViews());
        String time = String.valueOf(codeService.getCodeByID(id).getTime());
        String code = codeService.getCodeByID(id).getCode();
        model.addAttribute("date", date);
        model.addAttribute("views", views);
        model.addAttribute("time", time);
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
