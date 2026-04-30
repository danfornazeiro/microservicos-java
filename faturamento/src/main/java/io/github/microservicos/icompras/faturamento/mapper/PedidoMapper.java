package io.github.microservicos.icompras.faturamento.mapper;

import io.github.microservicos.icompras.faturamento.model.Cliente;
import io.github.microservicos.icompras.faturamento.model.ItemPedido;
import io.github.microservicos.icompras.faturamento.model.Pedido;
import io.github.microservicos.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import io.github.microservicos.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {
    public Pedido map(DetalhePedidoRepresentation representation) {
        var cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.logradouro(),
                representation.numero(),
                representation.bairro(),
                representation.email(),
                representation.telefone()
        );

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();

        return new Pedido(
                representation.codigo(),
                cliente,
                representation.dataPedido(),
                representation.total(),
                itens
        );
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation) {
        return new ItemPedido(
            representation.codigoProduto(),
            representation.nome(),
            representation.valorUnitario(),
            representation.quantidade()
        );
    }
}
