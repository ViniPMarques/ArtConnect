package com.ufsm.csi.artconnect.service.impl;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;
import com.ufsm.csi.artconnect.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioDto> findAllUsuarios() {
        List<UsuarioRepository> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map((usuario) -> mapToUsuarioDto((Usuario) usuario)).collect((Collectors.toList()));
    }

    private UsuarioDto mapToUsuarioDto(Usuario usuario){
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .idusuario(usuario.getIdusuario())
                .nomeusuario(usuario.getNomeusuario())
                .emailusuario(usuario.getEmailusuario())
                .tipousuario(usuario.getTipousuario())
                .build();
        return usuarioDto;
    }
}
