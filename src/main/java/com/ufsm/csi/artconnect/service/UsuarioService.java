package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.PerfilForm;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
    List<UsuarioDto> findAllArtistas(String filtro);
    UsuarioDto salvar(UsuarioForm usuarioForm);
    UsuarioDto update(PerfilForm usuario, HttpServletRequest request);
    UsuarioDto findUsuarioById(Long id);
    UsuarioDto findUsuarioByEmail(String name);
    UsuarioDto deactivateAccount(Long id);
}
