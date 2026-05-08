package io.github.microservicos.icompras.pedidos.service;

import io.github.microservicos.icompras.pedidos.model.enums.StatusPedido;
import io.github.microservicos.icompras.pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {
    private final PedidoRepository repository;

    @Transactional
    public void AtualizarStatus(Long codigo, StatusPedido status, String urlNF, String codigoRastreio) {
        repository.findById(codigo).ifPresent(
                pedido -> {
                    pedido.setStatusPedido(status);
                    pedido.setUrlNF(urlNF);
                    pedido.setCodigoRastreio(codigoRastreio);
                }
        );
    }
}
