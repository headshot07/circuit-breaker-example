package com.example.circuitbreaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "15000")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CircuitBreakerApplication.class)
public class CircuitBreakerControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @RepeatedTest(10)
    public void testCircuitBreaker(RepetitionInfo repetitionInfo) {
        webTestClient.get()
                .uri("/api/{time}", repetitionInfo.getCurrentRepetition())
                .exchange()
                .expectStatus()
                .isOk();
    }

}
