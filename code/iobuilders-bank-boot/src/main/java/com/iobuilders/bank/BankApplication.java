package com.iobuilders.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BankApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BankApplication.class, args);
  }
}