package spring.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllers {

    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    @GetMapping("/o-eshopu")
    public String renderEshop() {
        return "pages/home/o-eshopu";
    }

}
