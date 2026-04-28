package io.github.microservicos.icompras.pedidos.controller;

import io.github.microservicos.icompras.pedidos.controller.dto.NovoPagamentoDTO;
import io.github.microservicos.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.microservicos.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.microservicos.icompras.pedidos.exception.ItemNaoEncontradoException;
import io.github.microservicos.icompras.pedidos.exception.ValidationException;
import io.github.microservicos.icompras.pedidos.model.ErroResponse;
import io.github.microservicos.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoMapper mapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDTO) {
        try {
            var pedido = mapper.map(novoPedidoDTO);
            return ResponseEntity.ok(pedidoService.criarPedido(pedido).getCodigo());
        } catch (ValidationException e) {
             var response = new ErroResponse(
                     "Erro validação",
                     e.getField(),
                     e.getMessage()
             );
             return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody NovoPagamentoDTO novoPagamentoDTO) {
        try {
            pedidoService.adicionarNovoPagamento(
                    novoPagamentoDTO.codigoPedido(),
                    novoPagamentoDTO.dadosCartao(),
                    novoPagamentoDTO.tipoPagamento()
            );

            return  ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var response = new ErroResponse(
                    "Item não Encontrado",
                    "codigoPedido",
                    e.getMessage()
            );
            return  ResponseEntity.badRequest().body(response);
        }
    }

}
