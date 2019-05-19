package pl.edu.wszib.springfirststeps.order;

import java.util.List;

public interface OrderRepository {
    Long save(Order order);

    Order findById(Long orderId);

    List<Order> findAll();
}
