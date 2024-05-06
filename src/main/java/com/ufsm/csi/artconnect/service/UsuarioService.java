package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.UsuarioForm;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
    UsuarioDto salvar(UsuarioForm usuarioForm);
}
