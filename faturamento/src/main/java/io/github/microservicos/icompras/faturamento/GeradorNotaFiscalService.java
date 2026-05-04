package io.github.microservicos.icompras.faturamento;

import io.github.microservicos.icompras.faturamento.bucket.BucketFile;
import io.github.microservicos.icompras.faturamento.bucket.BucketService;
import io.github.microservicos.icompras.faturamento.model.Pedido;
import io.github.microservicos.icompras.faturamento.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {
    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;

    public void gerarNotaFiscal(Pedido pedido) {
        log.info("Gerando nota fiscal do pedido {}", pedido.codigo());

        try {
            byte[] byteArray = notaFiscalService.getNotaFiscal(pedido);
            String arquivo = String.format("nf_pedido_%d.pdf", pedido.codigo());
            var file = new BucketFile(
                    arquivo,
                    new ByteArrayInputStream(byteArray),
                    MediaType.APPLICATION_PDF,
                    byteArray.length
            );

            bucketService.upload(file);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
