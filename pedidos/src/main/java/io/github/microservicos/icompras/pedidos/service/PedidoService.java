package io.github.microservicos.icompras.pedidos.service;

import io.github.microservicos.icompras.pedidos.client.ServicoBancarioClient;
import io.github.microservicos.icompras.pedidos.exception.ItemNaoEncontradoException;
import io.github.microservicos.icompras.pedidos.model.DadosPagamento;
import io.github.microservicos.icompras.pedidos.model.Pedido;
import io.github.microservicos.icompras.pedidos.model.enums.StatusPedido;
import io.github.microservicos.icompras.pedidos.model.enums.TipoPagamento;
import io.github.microservicos.icompras.pedidos.repository.ItemPedidoRespository;
import io.github.microservicos.icompras.pedidos.repository.PedidoRepository;
import io.github.microservicos.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRespository itemPedidoRespository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criarPedido(Pedido pedido){
        pedidoValidator.validar(pedido);
        salvarPedido(pedido);
        solicitarPagamento(pedido);
        return pedido;
    }

    private void salvarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRespository.saveAll(pedido.getItens());
    }

    private void solicitarPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        //com o @Transactional ele cria uma transação e apos inclusão, atualiza os dados
        pedido.setChavePagamento(chavePagamento);
    }

    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {
        var pedidoEncontrado = pedidoRepository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if (pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido %d com chave pagamento %s não encontrado.", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if (sucesso) {
            pedido.setStatusPedido(StatusPedido.PAGO);
        } else {
            pedido.setStatusPedido(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()) {
            throw new ItemNaoEncontradoException("Pedido não encontrado para o código não inforfmado!");
        }

        var pedido = pedidoEncontrado.get();

        var dadosPagamento = new DadosPagamento();
        dadosPagamento.setDados(dadosCartao);
        dadosPagamento.setTipoPagamento(tipoPagamento);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguarde o processamento!");

        var novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        //não precisaria colocar o repository por conta da Trasactional
        pedidoRepository.save(pedido);
    }
}
