package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

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

    @Entity
    public static class Position {

        @OneToOne(optional = false)
        private Order order;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Long price;

        @Column(nullable = false)
        private Long quantity;

        @Column(nullable = false)
        private String name;

        protected Position() {
            // for hibernate
        }

        @JsonCreator
        public Position(@JsonProperty("price") Long price, @JsonProperty("quantity") Long quantity, @JsonProperty("name") String name) {
            this.price = price;
            this.quantity = quantity;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "price=" + price +
                    ", quantity=" + quantity +
                    ", name='" + name + '\'' +
                    '}';
        }

        public Long amount() {
            return price * quantity / 1000;
        }

        public void setOrder(Order order) {
            this.order = order;
        }
    }
}
