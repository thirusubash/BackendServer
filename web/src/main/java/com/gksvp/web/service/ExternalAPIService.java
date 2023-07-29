package com.gksvp.web.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIService {

    private final RestTemplate restTemplate;

    public ExternalAPIService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> callExternalAPI(String apiUrl, String payload) {
        // Set the request headers, if needed
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        // Make the HTTP request
        HttpEntity<String> requestEntity = new HttpEntity<>(payload);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
                String.class);
        return responseEntity;
    }
}
