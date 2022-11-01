package org.example.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
})
public class PaymentController {

    @Resource
    DiscoveryClient discoveryClient;
    @Value("${server.port}")
    String port;

    @GetMapping("/sleep1")
    public String sleep1() {
        System.out.println(Thread.currentThread().getName() + " come in");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

    @GetMapping("/sleep2")
    public String sleep2() {
        System.out.println(Thread.currentThread().getName() + " come in");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

    @GetMapping("/discover")
    public List<String> discover() {
        List<String> services = discoveryClient.getServices();
        for (String s : services) {
            System.out.println(s);
        }
        return services;
    }

    @GetMapping("/port")
    public String port() {
        return port;
    }

    @GetMapping("/timeout")
    @HystrixCommand(fallbackMethod = "timeoutHandler")
    public String timeout() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return port;
    }

    @GetMapping("/id/{id}")
    @HystrixCommand
    public String id(@PathVariable("id") Long id) {
        if (id < 0)
            throw new RuntimeException();
        return "success";
    }

    public String timeoutHandler() {
        return "sorry";
    }

    public String fallback() {
        return "universal fallback";
    }

}
