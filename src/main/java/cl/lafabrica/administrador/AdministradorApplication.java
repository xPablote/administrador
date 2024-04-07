package cl.lafabrica.administrador;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AdministradorApplication {
    public static void main(String[] args) {
		SpringApplication.run(AdministradorApplication.class, args);
	}

}
