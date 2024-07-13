package com.ufsm.csi.artconnect.repository;

import com.ufsm.csi.artconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface ArtistRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByTipousuario(int tipousuario);
}
