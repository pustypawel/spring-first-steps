package pl.edu.wszib.springfirststeps.order;

import pl.edu.wszib.springfirststeps.order.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

// TODO Transakcja
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long create(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> order.dto())
                .collect(Collectors.toList());
    }
}
