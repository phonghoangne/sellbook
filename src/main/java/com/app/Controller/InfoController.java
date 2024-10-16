package com.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class InfoController {
    @GetMapping("/optionDev")
    public String getOptionDevView(Model model){

        return "optionDevelopers";
    }

    @GetMapping("/info1")
    public String getInfo1(Model model){
        return "developers_Phong";
    }

    @GetMapping("/info2")
    public String getInfo2(Model model){
        return "developers_Hung";
    }
}
