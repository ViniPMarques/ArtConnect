package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.PerfilForm;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.ArtistRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
    List<UsuarioDto> findAllArtistas(String filtro);
    UsuarioDto salvar(UsuarioForm usuarioForm);
    UsuarioDto update(PerfilForm usuario, HttpServletRequest request);
    UsuarioDto findUsuarioById(Long id);
    UsuarioDto findUsuarioByEmail(String name);
    UsuarioDto deactivateAccount(Long id);

//    @Override
//    public UsuarioDto findUsuarioById(Long id) {
//        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return mapToUsuarioDto(usuario);
//    }

}
