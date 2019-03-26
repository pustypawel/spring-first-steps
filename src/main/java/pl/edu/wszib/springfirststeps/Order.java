package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Order {

    private final Long amount;

    private final List<Position> positions;

    @JsonCreator
    public Order(@JsonProperty("amount") Long amount, @JsonProperty("positions") Position[] positions) {
        this.amount = amount;
        this.positions = Arrays.asList(positions);
    }

    public Order(Position ... positions) {
        Objects.requireNonNull(positions);
        this.positions = new ArrayList<>();
        this.positions.addAll(Arrays.asList(positions));
        this.amount = calculateAmount();
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

    public static class Position {
        private final Long price;
        private final Long quantity;
        private final String name;

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
    }
}
