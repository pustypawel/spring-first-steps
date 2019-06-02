package pl.edu.wszib.springfirststeps.order;

import pl.edu.wszib.springfirststeps.order.dto.CreateOrderDto;
import pl.edu.wszib.springfirststeps.order.dto.PositionDto;

import javax.persistence.*;

@Entity
class Position {

    @ManyToOne(optional = false)
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

    public Position(Order order, long id, long price, long quantity, String name) {
        this.order = order;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public Position(Long price, Long quantity, String name) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public static Position fromCreate(CreateOrderDto.Position position) {
        Long price = position.getPrice();
        Long quantity = position.getQuantity();
        String name = position.getName();
        return new Position(price, quantity, name);
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

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public PositionDto dto() {
        return new PositionDto(id, price, quantity, name);
    }
}