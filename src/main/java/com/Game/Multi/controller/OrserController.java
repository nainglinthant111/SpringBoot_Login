package com.Game.Multi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrserController {
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String requestMethodName(HttpSession session) {
        final Object loginInfo = session.getAttribute("loginInfo");
        if (loginInfo != null) {
            return "order/order";
        }
        return "redirect:/";
    }

}
