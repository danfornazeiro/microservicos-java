package io.github.microservicos.icompras.pedidos.subscriber;

import io.github.microservicos.icompras.pedidos.service.AtualizacaoStatusPedidoService;
import io.github.microservicos.icompras.pedidos.subscriber.representation.AtualizacaoStatusPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoSubscriber {
    private final AtualizacaoStatusPedidoService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
            "${icompras.config.kafka.topics.pedidos-faturados}",
            "${icompras.config.kafka.topics.pedidos-enviados}"
    })
    public void receberAtualizacao(String json) {
        log.info("Recebendo atualizacaoStatusPedido: {}", json);

        try {
            var representation = objectMapper.readValue(json, AtualizacaoStatusPedidoRepresentation.class);
            service.AtualizarStatus(
                    representation.codigo(),
                    representation.status(),
                    representation.urlNF(),
                    representation.codigoRastreio()
            );

            log.info("Pedido atualizado com sucesso!");
        } catch (Exception e){
            log.error("Erro ao processar a mensagem de atualização de status do pedido", e);
        }
    }

}

