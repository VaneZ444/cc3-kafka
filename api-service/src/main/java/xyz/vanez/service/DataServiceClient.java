package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceClient {

    private final RestTemplate restTemplate;

    @Value("${data-service.url}")
    private String dataServiceUrl;

    public List<?> search(String text) {
        try {
            String url = dataServiceUrl + "/internal/search?text=" + text;
            log.debug("Search request: {}", url);
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Search request error: {}", e.getMessage());
            throw e;
        }
    }

    public List<?> getTopRated() {
        try {
            String url = dataServiceUrl + "/internal/reports/top-rated";
            log.debug("getTopRated Request: {}", url);
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("getTopRated Request Error: {}", e.getMessage());
            throw e;
        }
    }

    public List<?> getReviewsByDay() {
        try {
            String url = dataServiceUrl + "/internal/reports/reviews-by-day";
            log.debug("getReviewsByDay Request: {}", url);
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("getReviewsByDay Request Error: {}", e.getMessage());
            throw e;
        }
    }

    public List<?> getMostActive() {
        try {
            String url = dataServiceUrl + "/internal/reports/most-active";
            log.debug("getMostActive Request: {}", url);
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("getMostActive Request Error: {}", e.getMessage());
            throw e;
        }
    }
}