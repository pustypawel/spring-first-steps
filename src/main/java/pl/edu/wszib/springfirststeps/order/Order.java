package pl.edu.wszib.springfirststeps.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.wszib.springfirststeps.order.dto.OrderDto;
import pl.edu.wszib.springfirststeps.order.dto.PositionDto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ORDER_TABLE")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
    private Set<Position> positions = new HashSet<>();

    protected Order() {
        // for hibernate
    }

    @JsonCreator
    public Order(@JsonProperty("amount") Long amount, @JsonProperty("positions") Position[] positions) {
        this.amount = amount;
        this.positions.addAll(Arrays.asList(positions));
        this.positions.forEach(position -> position.setOrder(this));
    }

    public Order(Position ... positions) {
        Objects.requireNonNull(positions);
        this.positions.addAll(Arrays.asList(positions));
        this.positions.forEach(position -> position.setOrder(this));
        this.amount = calculateAmount();
    }

    public Order(long id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    private Long calculateAmount() {
        return positions.stream()
                .mapToLong(position -> position.amount())
                .sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", positions=" + positions +
                '}';
    }

    public Long getAmount() {
        return amount;
    }

    public void applyPositions(List<Position> positions) {
        this.positions.addAll(positions);
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public OrderDto dto() {
        List<PositionDto> positionDtos = positions.stream()
                .map(position -> position.dto())
                .collect(Collectors.toList());
        return new OrderDto(id, amount, positionDtos);
    }
}
