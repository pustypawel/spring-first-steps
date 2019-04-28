package pl.edu.wszib.springfirststeps.order;

public interface OrderRepository {
    Long save(Order order);

    Order findById(Long orderId);
}
