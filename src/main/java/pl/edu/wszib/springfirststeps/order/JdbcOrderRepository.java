package pl.edu.wszib.springfirststeps.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Order order) {
        Long id = order.getId();
        if (id == null) {
            return insert(order);
        } else {
            return update(order);
        }
    }

    private Long insert(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ORDER_TABLE (amount) VALUES (?)");
                    preparedStatement.setLong(1, order.getAmount());
                    return preparedStatement;
        },
                keyHolder);
        if (rowsAffected != 1) {
            throw new IllegalStateException("rowsAffected should be 1 when insert. actual rowsAffected = " + rowsAffected);
        }
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("key is null when insert");
        }
        Long orderId = key.longValue();
        insertPositions(orderId, order.getPositions());
        return orderId;
    }

    private void insertPositions(Long orderId, Set<Position> positions) {
        positions.forEach(position -> {
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO POSITION (price, quantity, name, order_id) VALUES (?, ?, ?, ?)");
                preparedStatement.setLong(1, position.getPrice());
                preparedStatement.setLong(2, position.getQuantity());
                preparedStatement.setString(3, position.getName());
                preparedStatement.setLong(4, orderId);
                return preparedStatement;
            });
            if (rowsAffected != 1) {
                throw new IllegalStateException("rowsAffected should be 1 when insert. actual rowsAffected = " + rowsAffected);
            }
        });
    }

    private Long update(Order order) {
        // TODO IMPL
        return null;
    }

    @Override
    public Order findById(Long orderId) {
        Order order = jdbcTemplate.queryForObject("SELECT id, amount FROM ORDER_TABLE WHERE id = ?",
                (resultSet, i) -> {
                    long id = resultSet.getLong("id");
                    long amount = resultSet.getLong("amount");
                    return new Order(id, amount);
                }, orderId);
        List<Position> positions = jdbcTemplate.query("SELECT id, price, quantity, name FROM POSITION WHERE order_id = ?",
                (resultSet, i) -> {
                    long id = resultSet.getLong("id");
                    long price = resultSet.getLong("price");
                    long quantity = resultSet.getLong("quantity");
                    String name = resultSet.getString("name");
                    return new Position(order, id, price, quantity, name);
                },
                orderId);
        order.applyPositions(positions);
        return order;
    }

    @Override
    public List<Order> findAll() {
        // TODO JDBC IMPL
        return null;
    }
}
