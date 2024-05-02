package com.ufsm.csi.artconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long idartista;
    private Long idcliente;
    @CreationTimestamp
    private LocalDateTime datacomissao;
    private Date datafinalizacao;
    private Double valorcomissao;
}
