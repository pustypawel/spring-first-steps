package pl.edu.wszib.springfirststeps.order;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.springfirststeps.order.dto.CreateOrderDto;
import pl.edu.wszib.springfirststeps.order.dto.OrderDto;
import pl.edu.wszib.springfirststeps.order.exception.OrderNotFoundException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.status(213)
                .body(orderService.findAll());
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId,
                             @RequestHeader("User-Agent") String userAgent,
                             @RequestHeader HttpHeaders httpHeaders,
                             @RequestParam(value = "param", required = false) List<String> param) {
        System.out.println("User-Agent = " + userAgent);
        System.out.println("HttpHeaders = " + httpHeaders);
        System.out.println("param = " + param);
        return orderService.findById(orderId);
    }

    @PostMapping
    public Long create(@Valid @RequestBody CreateOrderDto createOrderDto) {
        Long id = orderService.create(createOrderDto);
        return id;
    }
}
