package io.github.microservicos.icompras.logistica.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.microservicos.icompras.logistica.service.EnvioPedidoService;
import io.github.microservicos.icompras.logistica.subscriber.representation.AtualizacaoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoSubscriber {
    private final ObjectMapper objectMapper;
    private final EnvioPedidoService envioPedidoService;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.icompras.config.kafka.topics.pedidos-faturados}"
    )
    public void listen(String json){
        log.info("Mensagem recebida: {}", json);

        try {
            var representation = objectMapper.readValue(json, AtualizacaoFaturamentoRepresentation.class);
            envioPedidoService.enviar(representation.codigo(), representation.urlNF());

        } catch (Exception e) {
            log.error("Erro ao receber faturamento de pedidos", e);
        }
    }
}
