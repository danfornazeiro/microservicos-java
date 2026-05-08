package io.github.microservicos.icompras.pedidos.subscriber.representation;

import io.github.microservicos.icompras.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNF,
        String codigoRastreio
) {

}
