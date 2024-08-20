package com.galileo.cu.unidadesusuarios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EntityScan({ "com.galileo.cu.commons.models" })
public class ServicioUnidadesUsuariosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServicioUnidadesUsuariosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**************************************");
		System.out.println("Unidades Usuarios V-2408200236");
	}

}
