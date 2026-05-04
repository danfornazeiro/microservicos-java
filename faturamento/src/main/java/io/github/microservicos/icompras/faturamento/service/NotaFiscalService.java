package io.github.microservicos.icompras.faturamento.service;

import io.github.microservicos.icompras.faturamento.model.Pedido;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotaFiscalService {
    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo.png")
    private Resource logo;

    public byte[] getNotaFiscal(Pedido pedido) {
        try (InputStream is = notaFiscal.getInputStream()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NOME", pedido.cliente().nome());
            parameters.put("CPF", pedido.cliente().cpf());
            parameters.put("LOGRADOURO", pedido.cliente().logradouro());
            parameters.put("NUMERO", pedido.cliente().numero());
            parameters.put("BAIRRO", pedido.cliente().bairro());
            parameters.put("EMAIL", pedido.cliente().email());
            parameters.put("TELEFONE", pedido.cliente().telefone());
            parameters.put("DATA_PEDIDO", pedido.data());
            parameters.put("TOTAL_PEDIDO", pedido.total());
            parameters.put("LOGO", logo.getFile().getAbsolutePath());

            // Jasper reads map keys directly, avoiding Java record accessor incompatibilities.
            List<Map<String, ?>> itens = new ArrayList<>();
            for (var item : pedido.itens()) {
                Map<String, Object> map = new HashMap<>();
                map.put("codigo", item.getCodigo());
                map.put("nome", item.getDescricao());
                map.put("quantidade", item.getQuantidade());
                map.put("valorUnitario", item.getValorUnitario());
                map.put("total", item.getTotal());
                itens.add(map);
            }

            var dataSource = new JRMapCollectionDataSource(itens);

            JasperReport jasperReport = JasperCompileManager.compileReport(is);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e){
            throw new RuntimeException("Erro ao carregar nota fiscal", e);
        }
    }
}
