package pl.edu.wszib.springfirststeps.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class OrderDto {

    private Long id;

    private Long amount;

    private List<PositionDto> positions = new ArrayList<>();

    @JsonCreator
    public OrderDto(@JsonProperty("id") Long id, @JsonProperty("amount") Long amount, @JsonProperty("positions") List<PositionDto> positions) {
        this.id = id;
        this.amount = amount;
        this.positions.addAll(positions);
    }

    public Long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }
}
