package com.ufsm.csi.artconnect.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {
    private Long idusuario;
    private String nomeusuario;
    private String emailusuario;
    private String profilepicture;
    private String description;
    private int tipousuario;
}
