package io.github.microservicos.icompras.pedidos.controller.mappers;

import io.github.microservicos.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.microservicos.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido map(ItemPedidoDTO itemPedidoDTO);
}
