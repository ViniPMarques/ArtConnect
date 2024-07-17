package com.ufsm.csi.artconnect.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpedido;
    @CreationTimestamp
    private LocalDateTime datacomissao;
    private Date datafinalizacao;
    private Double valorcomissao;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idartista")
    private Usuario artista;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Usuario cliente;
    @ManyToOne
    @JoinColumn(name = "idcomissao")
    private Comissao comissao;
}
