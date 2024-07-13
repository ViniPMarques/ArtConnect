package com.ufsm.csi.artconnect.form;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoForm {
    @NotBlank(message = "descrição inválida")
    @Size(max = 255, message = "descrição inválida")
    private String descricao;

    @NotNull(message = "comissão inválida")
    private Long idcomissao;
}
