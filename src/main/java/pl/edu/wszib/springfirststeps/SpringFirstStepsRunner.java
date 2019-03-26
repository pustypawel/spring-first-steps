package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.io.UncheckedIOException;

public class SpringFirstStepsRunner implements CommandLineRunner {

    private FileJsonReader fileJsonReader;
    private ObjectMapper objectMapper;

    public SpringFirstStepsRunner(FileJsonReader fileJsonReader, ObjectMapper objectMapper) {
        this.fileJsonReader = fileJsonReader;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        String json = fileJsonReader.readJson("order.txt");
        Order order = tryReadValue(json);
        System.out.println(order);
    }

    private Order tryReadValue(String json) {
        try {
            return objectMapper.readValue(json, Order.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
