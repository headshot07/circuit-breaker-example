package com.example.circuitbreaker.controllers;

import com.example.circuitbreaker.model.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping(value = "/api")
public class CircuitBreakerController {
    @GetMapping( path = "/{time}")
    @CircuitBreaker(name = "example", fallbackMethod = "fallback")
    public Response circuitBreaker(@PathVariable int time) throws InterruptedException {
        Thread.sleep(time * 1000);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    public Response fallback(int time, Exception ex){
        log.info("Api call failed for time: " + time);
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }
}
