package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.dto.ReviewMessage;
import xyz.vanez.dto.ReviewRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, ReviewRequest> kafkaTemplate;
    private static final String TOPIC = "reviews";

    public void sendReview(ReviewRequest request, String messageId) {
        ReviewMessage message = new ReviewMessage(
                request.getRestaurantName(),
                request.getRestaurantAddress(),
                request.getRating(),
                request.getComment(),
                messageId
        );
        log.info("sending: key='{}', value={}", request.getRestaurantName(), message);
        kafkaTemplate.send(TOPIC, request.getRestaurantName(), message);
    }
}