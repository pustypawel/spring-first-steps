package pl.edu.wszib.springfirststeps.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaOrderDao extends JpaRepository<Order, Long> {
}
