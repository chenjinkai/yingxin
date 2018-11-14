package com.yingxin.tec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PageController {

    @GetMapping("/health")
    public String health() {
        return "on";
    }
}
