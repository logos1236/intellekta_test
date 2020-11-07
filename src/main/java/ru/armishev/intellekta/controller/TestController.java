package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.model.Formatter;

@RestController
@RequestMapping(value="api/v1")
public class TestController {
    @Autowired
    private Formatter formatter;

    public TestController() {
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    @GetMapping("/format")
    public String getFormatter() {
        return formatter.format();
    }
}
