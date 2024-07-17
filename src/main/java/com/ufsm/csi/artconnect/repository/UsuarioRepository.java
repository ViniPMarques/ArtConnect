package com.ufsm.csi.artconnect.repository;

import com.ufsm.csi.artconnect.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailusuario(String email);
    Optional<Usuario> findById(Long id);
}



