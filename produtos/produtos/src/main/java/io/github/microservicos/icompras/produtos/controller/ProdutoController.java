package io.github.microservicos.icompras.produtos.controller;

import io.github.microservicos.icompras.produtos.model.Produto;
import io.github.microservicos.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        produtoService.salvar(produto);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterPorCodigo(@PathVariable Long codigo) {
        return produtoService.obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
