package io.github.microservicos.icompras.clientes.service;

import io.github.microservicos.icompras.clientes.model.Cliente;
import io.github.microservicos.icompras.clientes.repository.ClienteRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRespository clienteRespository;

    public Cliente salvar(Cliente cliente){
        return clienteRespository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo){
        return clienteRespository.findById(codigo);
    }
}
