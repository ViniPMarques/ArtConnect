package com.ufsm.csi.artconnect.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufsm.csi.artconnect.form.LoginForm;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
	public String login(Model model) {
		model.addAttribute("login", new LoginForm());
        return "login";
	}

    @GetMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
