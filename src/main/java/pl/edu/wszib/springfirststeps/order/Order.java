package pl.edu.wszib.springfirststeps.order;

import pl.edu.wszib.springfirststeps.order.dto.CreateOrderDto;
import pl.edu.wszib.springfirststeps.order.dto.OrderDto;
import pl.edu.wszib.springfirststeps.order.dto.PositionDto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ORDER_TABLE")
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "order",
            orphanRemoval = true)
    private Set<Position> positions = new HashSet<>();

    protected Order() {
        // for hibernate
    }

    public Order(Set<Position> positions) {
        Objects.requireNonNull(positions);
        this.positions.addAll(positions);
        this.positions.forEach(position -> position.setOrder(this));
        this.amount = calculateAmount();
    }

    public Order(long id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public static Order fromCreate(CreateOrderDto createOrderDto) {
        Set<CreateOrderDto.Position> positionsDto = createOrderDto.getPositions();
        Set<Position> positions = positionsDto.stream()
                .map(position -> Position.fromCreate(position))
                .collect(Collectors.toSet());
        return new Order(positions);
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
