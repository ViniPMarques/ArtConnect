package com.ufsm.csi.artconnect.repository;

import org.springframework.stereotype.Repository;

import com.ufsm.csi.artconnect.model.Comissao;
import com.ufsm.csi.artconnect.model.Obra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ComissaoRepository extends JpaRepository<Comissao, Long>{
    @Query("SELECT c FROM Comissao c WHERE c.artista.id = :idartista")
    List<Comissao> findByIdartista(@Param("idartista") Long idartista);

    @Query("SELECT c FROM Comissao c WHERE c.artista.id = :idartista AND c.id = :idcomissao")
    Optional<Comissao> findByIdartistaAndIdComissao(@Param("idartista") Long idartista, @Param("idcomissao") Long idcomissao);
}
