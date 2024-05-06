package com.ufsm.csi.artconnect.service.impl;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;
import com.ufsm.csi.artconnect.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private PasswordEncoder passwordEncoder;
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder p) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = p;
    }

    @Override
    public List<UsuarioDto> findAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
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

    @Override
    public UsuarioDto salvar(UsuarioForm usuarioForm) {
        Usuario u = new Usuario();
        u.setEmailusuario(usuarioForm.getEmail());
        u.setNomeusuario(usuarioForm.getNome());
        u.setTipousuario(usuarioForm.getArtista() ? 1 : 0);
        u.setSenhausuario(passwordEncoder.encode(usuarioForm.getSenha()));
        this.usuarioRepository.save(u);
        return mapToUsuarioDto(u);
    }
}
