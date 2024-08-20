package com.jtc.Logistic.Payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LogisticPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticPaymentApplication.class, args);
	}

}
