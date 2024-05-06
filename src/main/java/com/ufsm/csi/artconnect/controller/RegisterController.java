package com.ufsm.csi.artconnect.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegisterController {
    private UsuarioService usuarioService;

    public RegisterController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute
    public UsuarioForm command(){
        return new UsuarioForm();
    }

    @GetMapping
    public String view(Model model){
        return "register";
    }

    @PostMapping
    public String cadastrar(@ModelAttribute("usuarioForm") @Valid UsuarioForm usuario, BindingResult result){
        if (result.hasErrors()) {
            return "register";
        }
        this.usuarioService.salvar(usuario);
        return "redirect:/login";
    }
}
