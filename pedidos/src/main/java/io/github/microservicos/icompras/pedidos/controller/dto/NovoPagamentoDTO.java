package io.github.microservicos.icompras.pedidos.controller.dto;

import io.github.microservicos.icompras.pedidos.model.enums.TipoPagamento;

public record NovoPagamentoDTO(
        Long codigoPedido,
        String dadosCartao,
        TipoPagamento tipoPagamento
) {
}
