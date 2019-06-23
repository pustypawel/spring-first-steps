package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wszib.springfirststeps.order.OrderService;
import pl.edu.wszib.springfirststeps.order.dto.CreateOrderDto;
import pl.edu.wszib.springfirststeps.order.dto.OrderDto;

import java.io.IOException;
import java.io.UncheckedIOException;

@Transactional
public class SpringFirstStepsRunner implements CommandLineRunner {

    private final FileJsonReader fileJsonReader;
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public SpringFirstStepsRunner(FileJsonReader fileJsonReader,
                                  ObjectMapper objectMapper,
                                  OrderService orderService) {
        this.fileJsonReader = fileJsonReader;
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        String json = fileJsonReader.readJson("order.txt");
        CreateOrderDto order = tryReadValue(json);
        Long orderId = orderService.create(order);
        OrderDto loadedOrder = this.orderService.findById(orderId);
        System.out.println(loadedOrder);
    }

    private CreateOrderDto tryReadValue(String json) {
        try {
            return objectMapper.readValue(json, CreateOrderDto.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
