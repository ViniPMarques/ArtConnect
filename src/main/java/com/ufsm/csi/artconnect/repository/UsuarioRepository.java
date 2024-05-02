package com.ufsm.csi.artconnect.repository;

import com.ufsm.csi.artconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioRepository, Long> {

}
