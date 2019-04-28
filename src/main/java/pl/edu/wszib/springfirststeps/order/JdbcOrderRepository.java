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
        // TODO IMPL
        return key.longValue();
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
}
