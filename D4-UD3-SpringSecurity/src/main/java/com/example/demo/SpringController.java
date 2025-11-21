package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringController {
	@RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }
    
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    
    @RequestMapping("/login2")
    public String login() {
        return "login";
    }
}
