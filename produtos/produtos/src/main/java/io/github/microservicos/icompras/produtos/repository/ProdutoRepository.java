package io.github.microservicos.icompras.produtos.repository;

import io.github.microservicos.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
