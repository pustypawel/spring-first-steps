package pl.edu.wszib.springfirststeps.order;

import java.util.List;
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

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
