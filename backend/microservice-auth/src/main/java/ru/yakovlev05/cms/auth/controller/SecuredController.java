package ru.yakovlev05.cms.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecuredController {

    @GetMapping("/s")
    public String secured() {
        return "secured";
    }
}
