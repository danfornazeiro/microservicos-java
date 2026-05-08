package io.github.microservicos.icompras.faturamento.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "icompras.config.kafka")
@Data
public class KafkaProps {
    private String bootstrapServers;
    private Consumer consumer = new Consumer();

    @Data
    public static class Consumer {
        private String groupId;
        private String autoOffsetReset;
    }
}
