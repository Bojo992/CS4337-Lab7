package com.example.demo.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ExternalService implements IExternalService {
    private final RestTemplate restTemplate;

    public ExternalService(RestTemplateBuilder test) {
        this.restTemplate = test.build();
    }

    @Override
    public ResponseEntity<String> requestLoremIpsum() {
        String url = "https://loripsum.net/api/1/short";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        var test = "<div style=\"background-color: #5a8949; padding: 20px; border-radius: 10px; box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.7); max-width: 400px; text-align: center; align-content: center; margin: auto;\">\n" +
                "    <h2>Card Title</h2>\n" +
                "    " + response.getBody() +
                "</div>";
        return new ResponseEntity<>(test, response.getStatusCode());
    }
}
