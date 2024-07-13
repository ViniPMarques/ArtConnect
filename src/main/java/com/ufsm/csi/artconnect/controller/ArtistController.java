package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.ComissaoForm;
import com.ufsm.csi.artconnect.form.PedidoForm;
import com.ufsm.csi.artconnect.model.Comissao;
import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.model.Pedido;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.service.ComissaoService;
import com.ufsm.csi.artconnect.service.ObraService;
import com.ufsm.csi.artconnect.service.PedidoService;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ObraService obraService;
    @Autowired
    private ComissaoService comissaoService;
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/artistPage")
    public String showArtistPage(Model model, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario.getTipousuario() == 0){
            throw new Exception("acesso somente para artistas");
        }
        UsuarioDto artist = usuarioService.findUsuarioById(usuario.getIdusuario());
        List<Obra> obras = obraService.findObrasByArtistId(usuario.getIdusuario());
        List<Comissao> comissoes = comissaoService.getAllByArtista(usuario.getIdusuario());
        model.addAttribute("artist", artist);
        model.addAttribute("obras", obras);
        model.addAttribute("comissoes", comissoes);
        model.addAttribute("podeEditar", true);
        model.addAttribute("pedidoForm", new PedidoForm());
        return "artistPage";
    }

    @GetMapping("/artistPage/{id}")
    public String showArtistPage(@PathVariable Long id ,Model model, HttpServletRequest request) {
        UsuarioDto artist = usuarioService.findUsuarioById(id);
        List<Obra> obras = obraService.findObrasByArtistId(artist.getIdusuario());
        List<Comissao> comissoes = comissaoService.getAllByArtista(artist.getIdusuario());
        model.addAttribute("artist", artist);
        model.addAttribute("obras", obras);
        model.addAttribute("comissoes", comissoes);
        model.addAttribute("podeEditar", false);
        model.addAttribute("pedidoForm", new PedidoForm());
        return "artistPage";
    }

    @GetMapping("/artist/comissao")
    public String showComisssoes(Model model, HttpServletRequest request) {
        model.addAttribute("comissaoForm", new ComissaoForm());
        return "comissao/cadastro";
    }

    @GetMapping("/artist/comissao/update/{id}")
    public String updateComissoesView(@PathVariable Long id, Model model, Principal principal) throws Exception{
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        Comissao comissao = comissaoService.findByArtista(currentUser.getIdusuario(), id);
        ComissaoForm comissaoForm = new ComissaoForm(comissao);
        model.addAttribute("comissaoForm", comissaoForm);
        return "comissao/cadastro";
    }

    @PostMapping("/artist/comissao")
    public String saveComissao(@Valid ComissaoForm comissaoForm, RedirectAttributes redirectAttributes, Principal principal) throws Exception{
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        comissaoService.save(comissaoForm, currentUser);
        redirectAttributes.addFlashAttribute("success", "salvo com sucesso");
        return "redirect:/artistPage";
    }

    @PostMapping("/artist/comissao/delete")
    public String deleteComissao(@RequestParam Long id, RedirectAttributes redirectAttributes, Principal principal) throws Exception{
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        comissaoService.delete(id, currentUser);
        redirectAttributes.addFlashAttribute("success", "deletado com sucesso");
        return "redirect:/artistPage";
    }



    @GetMapping("/artist/upload")
    public String showUploadForm(Model model, Authentication authentication) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            return "uploadForm";
        }
        else{
            return "errorPage";
        }
    }

    @GetMapping("/artist/historico")
    public String historico(Model model, Principal principal) {
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        List<Pedido> pedidos = pedidoService.findByArtista(currentUser.getIdusuario());
        model.addAttribute("pedidos", pedidos);
        return "artistHistory";
    }

    @PostMapping("/artist/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, Principal principal) {
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        try {
            obraService.saveObra(file, currentUser);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file!");
        }
        return "uploadForm";
    }

    @PostMapping("/artist/obra/{id}")
    public String deleteFile(@PathVariable Long id, Model model, Principal principal) {
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        try {
            obraService.deleteObra(id, currentUser);
            model.addAttribute("message", "File deleted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to delete file!");
        }
        return "redirect:/artistPage";
    }

    @GetMapping("/artist/gallery")
    public String showGallery(@PathVariable Long id, Model model) {
        List<Obra> obras = obraService.findObrasByArtistId(id);
        model.addAttribute("obras", obras);
        return "gallery";
    }

    @GetMapping("/obras/{filename:.+}")
    @ResponseBody
    public Resource serveFile(@PathVariable String filename) {
        Path file = obraService.getObraPath(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
//
//    @PostMapping("/deactivateAccount")
//    @ResponseBody
//    public String deactivateAccount(Authentication authentication) {
//        String username = authentication.getName();
//        Usuario usuario = usuarioService.findByNomeusuario(username);
//        if (usuario != null) {
//            usuario.setAtivo(false);
//            usuarioService.updateUsuario(usuario);
//            return "success";
//        }
//        return "error";
//    }

}
