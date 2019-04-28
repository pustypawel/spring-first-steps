package pl.edu.wszib.springfirststeps.order;

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
}
