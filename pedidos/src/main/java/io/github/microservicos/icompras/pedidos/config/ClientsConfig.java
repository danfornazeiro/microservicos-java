package io.github.microservicos.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.github.microservicos.icompras.pedidos.client")
public class ClientsConfig {
}
