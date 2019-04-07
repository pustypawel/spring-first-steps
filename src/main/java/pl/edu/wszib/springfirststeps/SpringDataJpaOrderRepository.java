package pl.edu.wszib.springfirststeps;

import java.util.Optional;

public class SpringDataJpaOrderRepository implements OrderRepository {

    private final SpringDataJpaOrderDao orderDao;

    public SpringDataJpaOrderRepository(SpringDataJpaOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Long save(Order order) {
        Order savedOrder = orderDao.saveAndFlush(order);
        return savedOrder.getId();
    }

    @Override
    public Order findById(Long orderId) {
        Optional<Order> order = orderDao.findById(orderId);
        return order.orElse(null);
    }
}
