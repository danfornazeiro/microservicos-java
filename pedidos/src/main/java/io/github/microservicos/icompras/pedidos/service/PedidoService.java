package io.github.microservicos.icompras.pedidos.service;

import io.github.microservicos.icompras.pedidos.client.ServicoBancarioClient;
import io.github.microservicos.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import io.github.microservicos.icompras.pedidos.repository.ItemPedidoRespository;
import io.github.microservicos.icompras.pedidos.repository.PedidoRepository;
import io.github.microservicos.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRespository itemPedidoRespository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criarPedido(Pedido pedido){
        pedidoValidator.validar(pedido);
        salvarPedido(pedido);
        solicitarPagamento(pedido);
        return pedido;
    }

    private void salvarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRespository.saveAll(pedido.getItens());
    }

    private void solicitarPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        //com o @Transactional ele cria uma transação e apos inclusão, atualiza os dados
        pedido.setChavePagamento(chavePagamento);
    }
}
