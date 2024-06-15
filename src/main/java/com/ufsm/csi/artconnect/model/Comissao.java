package com.ufsm.csi.artconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcomissao;
    @CreationTimestamp
    private LocalDateTime datacomissao;
    private Date datafinalizacao;
    private Double valorcomissao;

    @ManyToOne
    @JoinColumn(name = "idartista")
    private Usuario artista;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Usuario cliente;
}
