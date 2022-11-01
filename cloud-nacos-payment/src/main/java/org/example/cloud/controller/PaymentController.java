package org.example.cloud.controller;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RefreshScope
public class PaymentController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${config.info}")
    String info;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        List<String> services = discoveryClient.getServices();
        return new Date() + info + " " + services.get(0);
    }

    @GetMapping("/id/{id}")
    @SentinelResource(value = "id", blockHandler = "blockHandler", fallback = "fallbackHandler")
    public String getTime(@PathVariable("id") Long id) {
        if (id < 0)
            throw new RuntimeException();

        return new Date().toString();
    }


    public String blockHandler(Long id, BlockException blockException) {
        return "block";
    }

    public String fallbackHandler(Long id, Throwable e) {
        return "fallback";
    }

}
