package com.ufsm.csi.artconnect.repository;

import com.ufsm.csi.artconnect.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ObraRepository extends JpaRepository<Obra, Long> {
    @Query("SELECT o FROM Obra o WHERE o.idartista = :idartista")
    List<Obra> findByIdartista(@Param("idartista") Long idartista);

    @Query("SELECT o FROM Obra o WHERE o.idartista = :idartista AND o.id = :idobra")
    Optional<Obra> findByIdartistaAndIdObra(@Param("idartista") Long idartista, @Param("idobra") Long idobra);
}
