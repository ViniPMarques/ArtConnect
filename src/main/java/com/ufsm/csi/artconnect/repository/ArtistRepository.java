package com.ufsm.csi.artconnect.repository;

import com.ufsm.csi.artconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByTipousuario(int tipousuario);
}
