package com.iobuilders.bank.config;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(PER_METHOD)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITTest {

}