package pl.edu.wszib.springfirststeps.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Position {

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

    public Position(Order order, long id, long price, long quantity, String name) {
        this.order = order;
        this.id = id;
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