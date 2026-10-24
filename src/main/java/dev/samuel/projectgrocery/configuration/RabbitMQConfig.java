package dev.samuel.projectgrocery.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "estoque.exchange";
    public static final String QUEUE_ESTOQUE_BAIXO = "estoque.baixo.queue";
    public static final String DLQ_ESTOQUE_BAIXO = "estoque.baixo.dlq";
    public static final String ROUTING_KEY_ESTOQUE_BAIXO = "estoque.baixo";

    @Bean
    public TopicExchange estoqueExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue estoqueBaixoQueue() {
        return QueueBuilder.durable(QUEUE_ESTOQUE_BAIXO)
                // mensagens rejeitadas (apos esgotar o retry) vao para a DLQ
                .deadLetterExchange("")
                .deadLetterRoutingKey(DLQ_ESTOQUE_BAIXO)
                .build();
    }

    @Bean
    public Queue estoqueBaixoDlq() {
        return QueueBuilder.durable(DLQ_ESTOQUE_BAIXO).build();
    }

    @Bean
    public Binding estoqueBaixoBinding(Queue estoqueBaixoQueue, TopicExchange estoqueExchange) {
        return BindingBuilder.bind(estoqueBaixoQueue)
                .to(estoqueExchange)
                .with(ROUTING_KEY_ESTOQUE_BAIXO);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}