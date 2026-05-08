package io.github.microservicos.icompras.faturamento.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.microservicos.icompras.faturamento.model.Pedido;
import io.github.microservicos.icompras.faturamento.representation.AtualizacaoStatusPedido;
import io.github.microservicos.icompras.faturamento.representation.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar(Pedido pedido, String urlNF){
        log.info("PUBLICAR PEDIDO NO FATURAMENTO {}", pedido);
        try {
            var representation = new AtualizacaoStatusPedido(
                    pedido.codigo(),
                    StatusPedido.FATURADO,
                    urlNF
            );

            String json = objectMapper.writeValueAsString(representation);
            log.info("payload: {}", json);
            kafkaTemplate.send(topico, "dados", json);
            log.info("payload enviado");
        } catch (Exception e){
            log.error("Erro ao publicar no tópico de pedidos faturados", e.getMessage());
        }
    }
}
