package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.ArtistRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
    List<UsuarioDto> findAllArtistas();
    UsuarioDto salvar(UsuarioForm usuarioForm);
    UsuarioDto findUsuarioById(Long id);
    UsuarioDto findUsuarioByEmail(String name);

//    @Override
//    public UsuarioDto findUsuarioById(Long id) {
//        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return mapToUsuarioDto(usuario);
//    }

}
