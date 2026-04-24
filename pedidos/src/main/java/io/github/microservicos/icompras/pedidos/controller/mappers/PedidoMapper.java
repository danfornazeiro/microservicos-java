package io.github.microservicos.icompras.pedidos.controller.mappers;

import io.github.microservicos.icompras.pedidos.controller.dto.DadosPagamentoDTO;
import io.github.microservicos.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.microservicos.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.microservicos.icompras.pedidos.model.DadosPagamento;
import io.github.microservicos.icompras.pedidos.model.ItemPedido;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import io.github.microservicos.icompras.pedidos.model.enums.StatusPedido;
import org.aspectj.lang.annotation.After;
import org.jspecify.annotations.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);
    //DadosPagamentoMapper DADOS_PAGAMENTO_MAPPER = Mappers.getMapper(DadosPagamentoMapper.class);

    @Mappings({
        @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens"),
        @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    })

    /*@Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "dadosPagamento", target = "dadosPagamento")*/
    Pedido map(NovoPedidoDTO dto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream().map(ITEM_PEDIDO_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido){
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = caluclarTotalDoPedido(pedido);

        pedido.setTotal(total);

        pedido.getItens().forEach(item -> item.setPedido(pedido));
    }

    private static @NonNull BigDecimal caluclarTotalDoPedido(Pedido pedido) {
        return pedido.getItens().stream().map(item ->
                item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }

    //se tivesse que fazer o mapeamento
    /*@Named("map")
    default DadosPagamento map(DadosPagamentoDTO dto) {
        return DADOS_PAGAMENTO_MAPPER.map(dto);
    }*/
}
