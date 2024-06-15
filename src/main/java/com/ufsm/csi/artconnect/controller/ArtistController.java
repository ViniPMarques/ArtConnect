package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.service.ObraService;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/artistPage")
    public String showArtistPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        UsuarioDto artist = usuarioService.findUsuarioById(usuario.getIdusuario());
        List<Obra> obras = obraService.findObrasByArtistId(usuario.getIdusuario());
        model.addAttribute("artist", artist);
        model.addAttribute("obras", obras);
        return "artistPage";
    }

    @GetMapping("/artistPage/{id}")
    public String showArtistPage(@PathVariable Long id ,Model model, HttpServletRequest request) {
        UsuarioDto artist = usuarioService.findUsuarioById(id);
        List<Obra> obras = obraService.findObrasByArtistId(artist.getIdusuario());
        model.addAttribute("artist", artist);
        model.addAttribute("obras", obras);
        return "artistPage";
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
