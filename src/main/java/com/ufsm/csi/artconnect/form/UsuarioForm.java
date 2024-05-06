package com.ufsm.csi.artconnect.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioForm {
    @NotBlank(message = "nome inválido")
    private String nome;

    @NotBlank(message = "email inválido")
    @Email(message = "email inválido")
    private String email;

    @NotBlank(message = "senha inválida")
    @Size(min = 4, message = "senha deve ter no mínimo 4 caracteres")
    private String senha;

    private Boolean artista;

    public UsuarioForm(){
        this.artista = false;
    }
}
