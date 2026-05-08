package io.github.microservicos.icompras.logistica.subscriber.representation;

import io.github.microservicos.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNF
) {
}
