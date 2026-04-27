package io.github.microservicos.icompras.pedidos.validator;

import feign.FeignException;
import io.github.microservicos.icompras.pedidos.client.ClientesClient;
import io.github.microservicos.icompras.pedidos.client.ProdutosClient;
import io.github.microservicos.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.microservicos.icompras.pedidos.client.representation.ProdutoRepresentation;
import io.github.microservicos.icompras.pedidos.exception.ValidationException;
import io.github.microservicos.icompras.pedidos.model.ItemPedido;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {
    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido){
        //poderia ser desta forma
       /*var codigos = pedido
                        .getItens()
                        .stream()
                        .map(p -> p.getCodigoProduto()).toList();

       codigos.forEach(codigo -> {
           ResponseEntity<ProdutoRepresentation> response = produtosClient.obterPorCodigo(codigo);
           ProdutoRepresentation produto = response.getBody();
       });*/

        //mas faremos assim
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente){
        try {
            var response = clientesClient.obterPorCodigo(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("cliente encontrado: {} - {}", cliente.codigo(), cliente.nome());
        } catch (FeignException.NotFound e) {
            String message = String.format("cliente %d não encontrado", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido itemPedido){
        try {
            var response = produtosClient.obterPorCodigo(itemPedido.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("produto encontrado: {} - {}", produto.codigo(), produto.nome());
        } catch (FeignException.NotFound e) {
            String message = String.format("produto %d não encontrado", itemPedido.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }
}
