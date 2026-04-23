package io.github.microservicos.icompras.clientes.repository;

import io.github.microservicos.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Long> {}
