package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.PedidoForm;
import com.ufsm.csi.artconnect.form.PerfilForm;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Pedido;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;
import com.ufsm.csi.artconnect.service.PedidoService;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;
    private PedidoService pedidoService;
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, PedidoService pedidoService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.pedidoService = pedidoService;
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

    @PostMapping("/pedido")
    public String pedido(@ModelAttribute("pedidoForm") @Valid PedidoForm pedido, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "erro ao efetuar pedido");
            return "redirect:/home";
        }
        Usuario currentUser = (Usuario) request.getSession().getAttribute("usuario");
        this.pedidoService.save(pedido, currentUser);
        redirectAttributes.addFlashAttribute("success", "pedido efetuado com sucesso");
        return "redirect:/home";
    }
}
