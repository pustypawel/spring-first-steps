package pl.edu.wszib.springfirststeps;

public interface OrderRepository {
    Long save(Order order);

    Order findById(Long orderId);
}
