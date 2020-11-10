package ru.armishev.intellekta;

import ru.armishev.intellekta.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class})
public class IntellektaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntellektaApplication.class, args);
	}

}
