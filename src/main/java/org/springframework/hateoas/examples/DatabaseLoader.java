package org.springframework.hateoas.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Greg Turnquist
 */
@Component
public class DatabaseLoader {

	@Bean
	CommandLineRunner init(OrderRepository repository) {

		return args -> {
			repository.save(new Order("grande mocha"));
			repository.save(new Order("venti hazelnut machiatto"));
		};
	}
}
