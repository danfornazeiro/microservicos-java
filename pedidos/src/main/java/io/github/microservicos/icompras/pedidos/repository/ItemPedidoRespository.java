package io.github.microservicos.icompras.pedidos.repository;

import io.github.microservicos.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRespository extends JpaRepository<ItemPedido,Long> {
}
