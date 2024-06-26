package com.Game.Multi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Game.Multi.model.CreateAccount;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CreateAccouny {

    @RequestMapping(value = "/createaccount", method = RequestMethod.GET)
    public String createAccount() {
        return "create/createAccount";
    }

    @RequestMapping(value = "/successful", method = RequestMethod.POST)
    public String CreateAccount(Model model, CreateAccount create) {
        log.info("Account create is successfully! - create user is : " + create.getEmail());
        return "create/successful";
    }
}
