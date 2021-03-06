package pl.edu.wszib.springfirststeps;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wszib.springfirststeps.order.OrderService;

@Configuration
public class SpringFirstStepsConfiguration {

    @Bean
    public SpringFirstStepsRunner springFirstStepsRunner(ObjectMapper objectMapper, OrderService orderService) {
        FileJsonReader fileJsonReader = new NIOFileJsonReader();
        return new SpringFirstStepsRunner(fileJsonReader, objectMapper, orderService);
    }

}
