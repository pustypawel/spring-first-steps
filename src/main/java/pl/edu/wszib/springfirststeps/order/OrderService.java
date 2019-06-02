package pl.edu.wszib.springfirststeps.order;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.edu.wszib.springfirststeps.order.dto.CreateOrderDto;
import pl.edu.wszib.springfirststeps.order.dto.OrderDto;
import pl.edu.wszib.springfirststeps.order.exception.OrderNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long create(CreateOrderDto createOrderDto) {
        Order order = Order.fromCreate(createOrderDto);
        return orderRepository.save(order);
    }

    public OrderDto findById(Long orderId) {
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        System.out.println("actualTransactionActive = " + actualTransactionActive);
        System.out.println("currentTransactionName = " + currentTransactionName);
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        return order.dto();
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> order.dto())
                .collect(Collectors.toList());
    }
}
