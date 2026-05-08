package io.github.microservicos.icompras.logistica.publisher;

import io.github.microservicos.icompras.logistica.model.AtualizacaoEnvioPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnvioPedidoPublisher {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.icompras.config.kafka.topics.pedidos-enviados}")
    private String topico;

    public void enviar(AtualizacaoEnvioPedido atualizacaoEnvioPedido) {
        log.info("Enviando atualização de envio de pedido: {}", atualizacaoEnvioPedido.codigo());

        try {
            var json = objectMapper.writeValueAsString(atualizacaoEnvioPedido);

            kafkaTemplate.send(topico, "dados", json);

            log.info("Atualização de envio de pedido enviada com sucesso: {}, código rastreio {}: ",
                    atualizacaoEnvioPedido.codigo(),
                    atualizacaoEnvioPedido.codigoRastreio());
        } catch (Exception e) {
            log.error("Erro ao enviar atualização de envio de pedido {}: ",
                    atualizacaoEnvioPedido.codigo(),
                    e
            );
        }
    }
}
