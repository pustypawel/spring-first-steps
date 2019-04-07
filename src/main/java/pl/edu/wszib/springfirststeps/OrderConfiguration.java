package pl.edu.wszib.springfirststeps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    OrderService orderService(OrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }

    @Bean
    OrderRepository orderRepository(SpringDataJpaOrderDao orderDao) {
        return new SpringDataJpaOrderRepository(orderDao);
    }
}
