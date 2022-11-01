package org.example.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRule {

    @Bean
    public IRule paymentRule() {
        return new RandomRule();
    }

}
