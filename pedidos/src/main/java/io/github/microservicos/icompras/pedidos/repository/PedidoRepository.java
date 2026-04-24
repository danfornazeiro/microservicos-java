package io.github.microservicos.icompras.pedidos.repository;

import io.github.microservicos.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
