package io.github.microservicos.icompras.logistica.service;

import io.github.microservicos.icompras.logistica.model.AtualizacaoEnvioPedido;
import io.github.microservicos.icompras.logistica.model.enums.StatusPedido;
import io.github.microservicos.icompras.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher envioPedidoPublisher;

    public void enviar(Long codigo, String urlNF) {
        var codigogoRastreio = gerarCodigoRastreio();
        var atualizacaoEnvioPedido = new AtualizacaoEnvioPedido(codigo, StatusPedido.ENVIADO, codigogoRastreio);

        envioPedidoPublisher.enviar(atualizacaoEnvioPedido);
    }

    private String gerarCodigoRastreio() {
        var random = new Random();
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));

        int numeros = 100000000 + random.nextInt(900000000);

        return String.valueOf(letra1) + String.valueOf(letra2) + numeros + "BR";
     }
}
