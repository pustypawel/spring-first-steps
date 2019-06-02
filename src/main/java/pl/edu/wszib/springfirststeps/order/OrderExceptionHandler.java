package pl.edu.wszib.springfirststeps.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.wszib.springfirststeps.order.exception.OrderNotFoundException;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(OrderNotFoundException e) {
        Long orderId = e.getOrderId();
        System.out.println("order id not found. orderId = " + orderId);
        return ResponseEntity.notFound()
                .build();
    }
}
