package com.galileo.cu.unidadesusuarios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@EntityScan({ "com.galileo.cu.commons.models" })
public class ServicioUnidadesUsuariosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServicioUnidadesUsuariosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("**************************************");
		System.out.println("Unidades Usuarios V-24-10-19 17:04");
	}

}
