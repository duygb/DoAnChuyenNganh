package com.nlu.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @GetMapping("")
    public String viewHomePage(HttpServletRequest request) {
        if (request.getSession().getAttribute("userLogged") != null){
            return "index";
        }
        return "redirect:/login";
    }
}
