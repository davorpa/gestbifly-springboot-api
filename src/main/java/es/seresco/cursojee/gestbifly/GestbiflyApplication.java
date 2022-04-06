package es.seresco.cursojee.gestbifly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "es.seresco")
public class GestbiflyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestbiflyApplication.class, args);
	}

}
