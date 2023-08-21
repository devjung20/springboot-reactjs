package sapo.vn.productconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String ROUTING_CREATE_PRODUCT = "product.create";
    private static final String ROUTING_UPDATE_PRODUCT = "product.update";
    private static final String ROUTING_DELETE_PRODUCT = "product.delete";
    private static final String ROUTING_SEARCH_PRODUCT = "product.search";
    private static final String ROUTING_INVENTORY_STATISTIC = "product.inventory";

    @Bean
    Queue createProductQueue() {
        return new Queue("queue.CreateProduct", false);
    }

    @Bean
    Queue updateProductQueue() {
        return new Queue("queue.UpdateProduct", false);
    }

    @Bean
    Queue deleteProductQueue() {
        return new Queue("queue.DeleteProduct", false);
    }

    @Bean
    Queue searchProductQueue() {
        return new Queue("queue.SearchProduct", false);
    }

    @Bean
    Queue inventoryStatistic() {
        return new Queue("queue.InventoryStatistic", false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Binding bindingCreateProduct(Queue createProductQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createProductQueue)
                .to(directExchange)
                .with(ROUTING_CREATE_PRODUCT);
    }

    @Bean
    Binding bindingUpdateProduct(Queue createProductQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createProductQueue)
                .to(directExchange)
                .with(ROUTING_UPDATE_PRODUCT);
    }

    @Bean
    Binding bindingDeleteProduct(Queue createProductQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createProductQueue)
                .to(directExchange)
                .with(ROUTING_DELETE_PRODUCT);
    }

    @Bean
    Binding bindingSearchProduct(Queue createProductQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createProductQueue)
                .to(directExchange)
                .with(ROUTING_SEARCH_PRODUCT);
    }

    @Bean
    Binding bindingInventoryStatistic(Queue inventoryStatistic, DirectExchange directExchange) {
        return BindingBuilder.bind(inventoryStatistic)
                .to(directExchange)
                .with(ROUTING_INVENTORY_STATISTIC);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
