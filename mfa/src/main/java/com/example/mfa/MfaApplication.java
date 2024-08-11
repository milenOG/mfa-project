package com.example.mfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * MFA application that facilitates generation and validation of MFA codes.
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
public class MfaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfaApplication.class, args);
	}

}
