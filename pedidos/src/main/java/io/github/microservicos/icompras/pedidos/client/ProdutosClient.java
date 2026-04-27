package io.github.microservicos.icompras.pedidos.client;

import io.github.microservicos.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.microservicos.icompras.pedidos.client.representation.ProdutoRepresentation;
import org.apache.coyote.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "produtos", url="${icompras.pedidos.clients.produtos.url}")
public interface ProdutosClient {
    @GetMapping("/{codigo}")
    ResponseEntity<ProdutoRepresentation> obterPorCodigo(@PathVariable("codigo") Long codigo);
}

