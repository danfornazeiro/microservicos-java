package io.github.microservicos.icompras.pedidos.service;

import io.github.microservicos.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import io.github.microservicos.icompras.pedidos.repository.ItemPedidoRespository;
import io.github.microservicos.icompras.pedidos.repository.PedidoRepository;
import io.github.microservicos.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRespository itemPedidoRespository;
    private final PedidoValidator pedidoValidator;

    public Pedido criarPedido(Pedido pedido){
        pedidoRepository.save(pedido);
        itemPedidoRespository.saveAll(pedido.getItens());
        return pedido;
    }

}
