package io.github.microservicos.icompras.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemPedido {
    private Long codigo;
    private String descricao;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;
}
