package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.io.UncheckedIOException;

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
        Order order = tryReadValue(json);
        Long orderId = orderService.create(order);
        Order loadedOrder = orderService.findById(orderId);
        System.out.println(loadedOrder);
    }

    private Order tryReadValue(String json) {
        try {
            return objectMapper.readValue(json, Order.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
