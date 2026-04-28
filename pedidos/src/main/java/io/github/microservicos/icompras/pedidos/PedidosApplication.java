package io.github.microservicos.icompras.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class PedidosApplication {

	/*@Bean
	public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> template){
		return args -> template.send("icompras.pedido-pago", "dados", "{jason}");
	}*/

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
