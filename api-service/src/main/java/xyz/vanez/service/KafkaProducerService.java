package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.dto.ReviewRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, ReviewRequest> kafkaTemplate;
    private static final String TOPIC = "reviews";

    public void sendReview(ReviewRequest review) {
        log.info("Sending review to Kafka: {}", review);
        kafkaTemplate.send(TOPIC, review);
        log.debug("Sent review with topic: {}", TOPIC);
    }
}