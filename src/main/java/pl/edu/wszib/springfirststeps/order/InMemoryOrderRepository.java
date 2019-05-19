package pl.edu.wszib.springfirststeps.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryOrderRepository implements OrderRepository {

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, Order> memory = new ConcurrentHashMap<>();

    @Override
    public Long save(Order order) {
        long id = counter.getAndIncrement();
        memory.put(id, order);
        return id;
    }

    @Override
    public Order findById(Long orderId) {
        return memory.get(orderId);
    }

    @Override
    public List<Order> findAll() {
        // TODO InMemory IMPL
        return null;
    }
}
