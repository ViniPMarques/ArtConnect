package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.PerfilForm;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;
import java.util.Map;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String home(Model model){
        return "redirect:/home";
    }

    @ModelAttribute("usuarioForm")
    public PerfilForm modelAttribute(HttpServletRequest request){
        PerfilForm usuarioForm = new PerfilForm();

        Usuario currentUser = (Usuario) request.getSession().getAttribute("usuario");
        usuarioForm.setDescription(currentUser.getDescription());
        usuarioForm.setNome(currentUser.getNomeusuario());
        return usuarioForm;
    }

    @GetMapping("/perfil")
    public String perfil(Model model){
        return "perfil";
    }

    @PostMapping("/perfil")
    public String atualizarPerfil(@ModelAttribute("perfilForm") @Valid PerfilForm usuario, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request){
        if (result.hasErrors()) {
            return "perfil";
        }
        this.usuarioService.update(usuario, request);
        redirectAttributes.addFlashAttribute("success", "Conta atualizada com sucesso");
        return "redirect:/home";
    }

    @GetMapping("/usuarios")
    public String listUsuarios(Model model){
        List<UsuarioDto> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuarios",usuarios);
        return "usuarios-list";
    }

    @PostMapping("/desativar")
    public String desativarConta(@RequestBody Map<String, Long> payload) {
        Long id = payload.get("id");
        usuarioService.deactivateAccount(id);
        return "Conta desativada com sucesso";
    }
}
