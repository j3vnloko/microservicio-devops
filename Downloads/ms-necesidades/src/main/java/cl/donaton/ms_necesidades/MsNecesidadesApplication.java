package cl.donaton.ms_necesidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsNecesidadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNecesidadesApplication.class, args);
	}

}
