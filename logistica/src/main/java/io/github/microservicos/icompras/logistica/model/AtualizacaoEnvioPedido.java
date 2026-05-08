package io.github.microservicos.icompras.logistica.model;

import io.github.microservicos.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoEnvioPedido(
        Long codigo,
        StatusPedido status,
        String codigoRastreio
) {
}
