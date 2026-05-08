package io.github.microservicos.icompras.faturamento.representation;

public record AtualizacaoStatusPedido(
        Long codigo,
        StatusPedido status,
        String urlNF
) {
}
