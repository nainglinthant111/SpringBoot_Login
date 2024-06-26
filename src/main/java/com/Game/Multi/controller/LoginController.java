package com.Game.Multi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Game.Multi.model.User;
import com.Game.Multi.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private UserService uService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginForm(HttpSession session, Model model) {
        Object loginInfo = session.getAttribute("loginInfo");
        if (loginInfo != null) {
            return "redirect:/home";
        }
        User user = new User();
        model.addAttribute("user", user);
        return "login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginurl(HttpSession session, @RequestParam String email, @RequestParam String password,
            Model model) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Object loginInfo = session.getAttribute("loginInfo");
        if (loginInfo != null) {
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        return "login/login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String Login(Model model, User user, HttpSession session) throws Exception {
        System.out.println("this is user" + user.getPassword() + user.getEmail());
        user.setPassword(uService.uncodePass(user.getPassword()));
        String loginInfo = uService.loginUser(user.getEmail(), user.getPassword());
        String accessKey = UserService.generatePassword(10);
        log.info(loginInfo);
        session.setAttribute("loginInfo", accessKey);
        return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHome(HttpSession session) {
        Object loginInfo = session.getAttribute("loginInfo");
        if (loginInfo != null) {
            return "login/home";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
