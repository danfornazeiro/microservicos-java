package io.github.microservicos.icompras.pedidos.controller.mappers;

import io.github.microservicos.icompras.pedidos.controller.dto.DadosPagamentoDTO;
import io.github.microservicos.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.microservicos.icompras.pedidos.model.DadosPagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DadosPagamentoMapper {
    DadosPagamento map(DadosPagamentoDTO dto);
}
