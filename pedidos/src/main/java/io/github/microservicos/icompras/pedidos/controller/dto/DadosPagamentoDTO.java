package io.github.microservicos.icompras.pedidos.controller.dto;

import io.github.microservicos.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(
        String dados,
        TipoPagamento tipoPagamento
) {
}
