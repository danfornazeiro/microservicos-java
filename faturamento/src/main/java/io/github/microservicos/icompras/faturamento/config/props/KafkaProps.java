package io.github.microservicos.icompras.faturamento.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring")
@Data
public class KafkaProps {
    String bootstrapServers;
    String groupId;
    String topic;
    String autoOffsetReset;
}
