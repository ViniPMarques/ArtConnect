package com.ufsm.csi.artconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    private String nomeusuario;
    private String emailusuario;
    private String profilepicture;
    private String description;
    private String senhausuario;
    private int tipousuario;
    private boolean ativo;
}
