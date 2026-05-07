package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xyz.vanez.dto.ReviewRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewConsumerService {

    private final ReviewService reviewService;

    @KafkaListener(topics = "reviews", groupId = "data-service-group")
    public void consume(ReviewRequest request) {
        log.info("Got kafka message: {}", request);
        try {
            reviewService.saveReview(request);
            log.debug("Review saved");
        } catch (Exception e) {
            log.error("Review save error: {}", e.getMessage(), e);
        }
    }
}