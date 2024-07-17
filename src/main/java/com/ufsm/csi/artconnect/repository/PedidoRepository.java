package com.ufsm.csi.artconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ufsm.csi.artconnect.model.Comissao;
import com.ufsm.csi.artconnect.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    @Query("SELECT p FROM Pedido p WHERE p.artista.id = :idartista")
    List<Pedido> findByIdartista(@Param("idartista") Long idartista);
}
