package com.kulushev.Payment.config;

import com.kulushev.Payment.dto.OrderReqDto;
import com.kulushev.Payment.dto.OrderRespDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newOrders() {
        return new NewTopic("payed_orders", 3, (short) 1);
    }


    @Bean
    public ConsumerFactory<String, OrderReqDto> orderReqDtoConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);
        JsonDeserializer<OrderReqDto> deserializer = new JsonDeserializer<>(OrderReqDto.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<?> orderReqDtoListenerFactory(ConsumerFactory<String, OrderReqDto> orderReqDtoConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderReqDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderReqDtoConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }

    @Bean
    public DefaultKafkaProducerFactory<String, OrderRespDto> orderRespDtoProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties(null);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    KafkaTemplate<String, OrderRespDto> objectKafkaTemplate(DefaultKafkaProducerFactory<String, OrderRespDto> objectProducerFactory) {
        return new KafkaTemplate<>(objectProducerFactory);
    }
}
