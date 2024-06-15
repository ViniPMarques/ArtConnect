package com.ufsm.csi.artconnect.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PerfilForm {
    @NotBlank(message = "nome inválido")
    private String nome;
    private String description;
}
