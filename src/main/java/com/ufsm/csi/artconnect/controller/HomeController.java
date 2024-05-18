package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"/", "/home"})
    public String showHomePage(Model model) {
        List<UsuarioDto> artistas = usuarioService.findAllArtistas();
        model.addAttribute("artists", artistas);
        return "home";
    }
}
