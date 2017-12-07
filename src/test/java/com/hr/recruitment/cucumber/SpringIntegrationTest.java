package com.hr.recruitment.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = FeatureTestConfiguration.class)
public class SpringIntegrationTest {
    protected static Object latestResponse = null;
    protected static HttpStatus latestStatusCode = null;
    @Autowired
    protected RestTemplate restTemplate;

    protected void execute(String url, Object entity, Class entityClass, HttpMethod httpMethod) throws IOException {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        System.out.println("URL:" + url + ":Method=:" + httpMethod);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(entity, requestHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, entityClass);
        latestStatusCode = responseEntity.getStatusCode();
        latestResponse = responseEntity.getBody();
    }

}