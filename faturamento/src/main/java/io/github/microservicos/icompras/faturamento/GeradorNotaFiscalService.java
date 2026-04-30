package io.github.microservicos.icompras.faturamento;

import io.github.microservicos.icompras.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeradorNotaFiscalService {
    public void gerarNotaFiscal(Pedido pedido) {
        log.info("Gerando nota fiscal do pedido {}", pedido);
    }
}
