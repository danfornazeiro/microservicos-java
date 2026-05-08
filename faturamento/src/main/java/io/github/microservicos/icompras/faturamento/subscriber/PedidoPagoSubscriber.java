package io.github.microservicos.icompras.faturamento.subscriber;

import io.github.microservicos.icompras.faturamento.service.GeradorNotaFiscalService;
import io.github.microservicos.icompras.faturamento.mapper.PedidoMapper;
import io.github.microservicos.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {
    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json) {
        try {
            log.info("recebendo pedido para faturamento: {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            var pedido = pedidoMapper.map(representation);
            geradorNotaFiscalService.gerarNotaFiscal(pedido);
            log.info("Faturamento gerado com sucesso");
        } catch (Exception e){
            log.error("Erro na comunicação do tópico: " + e.getMessage());
        }
    }
}
