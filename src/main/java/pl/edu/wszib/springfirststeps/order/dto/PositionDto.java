package pl.edu.wszib.springfirststeps.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionDto {
    private Long id;

    private Long price;

    private Long quantity;

    private String name;

    @JsonCreator
    public PositionDto(@JsonProperty("id") Long id, @JsonProperty("price") Long price, @JsonProperty("quantity") Long quantity, @JsonProperty("name") String name) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public Long getId() {
        return id;
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
