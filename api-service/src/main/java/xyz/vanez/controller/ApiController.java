package xyz.vanez.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.vanez.dto.ReviewRequest;
import xyz.vanez.service.DataServiceClient;
import xyz.vanez.service.KafkaProducerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final KafkaProducerService kafkaProducer;
    private final DataServiceClient dataClient;

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewRequest request) {
        String messageId = request.getMessageId();
        if (messageId == null || messageId.isBlank()) {
            messageId = UUID.randomUUID().toString();
            log.debug("Generated messageId: {}", messageId);
        } else {
            log.debug("Using provided messageId: {}", messageId);
        }
        log.info("POST /api/reviews - received: {}", request);
        kafkaProducer.sendReview(request, messageId);
        return ResponseEntity.accepted().body("Review sent to Kafka");
    }

    @GetMapping("/search")
    public ResponseEntity<List<?>> search(@RequestParam String text) {
        log.info("GET /api/search: {}", text);
        List<?> result = dataClient.search(text);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reports/top-rated")
    public ResponseEntity<List<?>> topRated() {
        log.info("GET /api/reports/top-rated");
        List<?> result = dataClient.getTopRated();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reports/reviews-by-day")
    public ResponseEntity<List<?>> reviewsByDay() {
        log.info("GET /api/reports/reviews-by-day");
        List<?> result = dataClient.getReviewsByDay();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reports/most-active")
    public ResponseEntity<List<?>> mostActive() {
        log.info("GET /api/reports/most-active");
        List<?> result = dataClient.getMostActive();
        return ResponseEntity.ok(result);
    }
}