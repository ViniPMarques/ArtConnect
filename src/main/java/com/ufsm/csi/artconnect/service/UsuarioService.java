package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
}
