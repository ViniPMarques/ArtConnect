package com.ufsm.csi.artconnect.controller;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.service.ObraService;
import com.ufsm.csi.artconnect.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ObraService obraService;

    @GetMapping("/artistPage/{id}")
    public String showArtistPage(@PathVariable Long id, Model model) {
        UsuarioDto artist = usuarioService.findUsuarioById(id);
        List<Obra> obras = obraService.findObrasByArtistId(id);
        model.addAttribute("artist", artist);
        model.addAttribute("obras", obras);
        return "artistPage";
    }

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable Long id, Model model) {
        UsuarioDto artist = usuarioService.findUsuarioById(id);
        if (artist != null) {
            System.out.println("Artist found: " + artist.getDescription()); //Debug
            model.addAttribute("artist", artist);
        } else {
            System.out.println("Artist not found for id: " + id); //Debug
            return "errorPage";
        }
        return "artistPage";
    }

    @GetMapping("/artist/{id}/upload")
    public String showUploadForm(@PathVariable Long id, Model model, Principal principal) {
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        if (currentUser != null && currentUser.getIdusuario().equals(id)) {
            model.addAttribute("artistId", id);
            return "uploadForm";
        } else {
            return "errorPage";
        }
    }

    @PostMapping("/artist/{id}/upload")
    public String handleFileUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file, Model model, Principal principal) {
        UsuarioDto currentUser = usuarioService.findUsuarioByEmail(principal.getName());
        if (currentUser != null && currentUser.getIdusuario().equals(id)) {
            try {
                obraService.saveObra(file, id);
                model.addAttribute("message", "File uploaded successfully!");
            } catch (IOException e) {
                model.addAttribute("message", "Failed to upload file!");
            }
            return "uploadForm";
        } else {
            return "errorPage";
        }
    }

    @GetMapping("/artist/{id}/gallery")
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

}
