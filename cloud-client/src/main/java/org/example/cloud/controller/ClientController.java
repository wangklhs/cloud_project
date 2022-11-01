package org.example.cloud.controller;

import org.example.cloud.entities.Payment;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ClientController {

    static String paymentUrl = "http://cloud-payment";
    @Resource
    DiscoveryClient discoveryClient;
    @Resource
    RestTemplate restTemplate;

    @GetMapping("/discover")
    public String discover() {
        return String.join(",", discoveryClient.getServices());
    }

    @GetMapping("/payment")
    public Payment getPayment() {
        return restTemplate.getForObject(paymentUrl, Payment.class);
    }

}
