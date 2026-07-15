package com.webapp.arvand.arvandback.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/layout")
public class LayoutController {
    @GetMapping()
    public String layout() {
        return "layout/category/list";
    }
    @GetMapping("/form")
    public String form() {
        return "layout/sidebar";
    }
}
