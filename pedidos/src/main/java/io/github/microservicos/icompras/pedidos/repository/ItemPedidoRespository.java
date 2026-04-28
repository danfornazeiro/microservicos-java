package io.github.microservicos.icompras.pedidos.repository;

import io.github.microservicos.icompras.pedidos.model.ItemPedido;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRespository extends JpaRepository<ItemPedido,Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
