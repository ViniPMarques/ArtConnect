package com.ufsm.csi.artconnect.form;

import java.math.BigDecimal;

import com.ufsm.csi.artconnect.model.Comissao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComissaoForm {
    private Long id;

    @NotBlank(message = "descrição inválida")
    @Size(max = 255, message = "descrição inválida")
    private String descricao;

    @NotNull(message = "valor inválido")
    private BigDecimal valor;

    public ComissaoForm(Comissao c){
        this.descricao = c.getDescricao();
        this.id = c.getIdcomissao();
        this.valor = c.getValor();
    }
}
