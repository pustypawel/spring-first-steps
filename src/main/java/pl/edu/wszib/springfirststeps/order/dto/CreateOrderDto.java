package pl.edu.wszib.springfirststeps.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.wszib.springfirststeps.order.Position;

import java.util.Collections;
import java.util.Set;

public class CreateOrderDto {
    private Set<Position> positions;

    @JsonCreator
    public CreateOrderDto(@JsonProperty("positions") Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Position> getPositions() {
        return Collections.unmodifiableSet(positions);
    }

    public static class Position {
        private Long price;

        private Long quantity;

        private String name;

        @JsonCreator
        public Position(@JsonProperty("price") Long price, @JsonProperty("quantity") Long quantity, @JsonProperty("name") String name) {
            this.price = price;
            this.quantity = quantity;
            this.name = name;
        }

        public Long getPrice() {
            return price;
        }

        public Long getQuantity() {
            return quantity;
        }

        public String getName() {
            return name;
        }
    }
}
