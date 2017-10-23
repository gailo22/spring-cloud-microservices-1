package com.gailo22.microservices.helloclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloResource {

    @Value("${serverApiUrl}")
    private String serverApiUrl;

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback", groupKey = "Hello",
            commandKey = "hello",
            threadPoolKey = "helloThread"
    )
    @GetMapping("/hello")
    public String hello() {
        String url = String.format("http://%s/hello", serverApiUrl);
        return restTemplate.getForObject(url, String.class) + " via client";
    }

    public String fallback(Throwable hystrixCommand) {
        return "Fall Back Hello world";
    }

}
