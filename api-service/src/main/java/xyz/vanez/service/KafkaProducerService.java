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
        String key = review.getRestaurantName();
        log.info("sending: key='{}', value={}", key, review);
        kafkaTemplate.send(TOPIC, key, review)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("sent: key='{}', partition={}, offset={}",
                                key,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("failed: key='{}', error={}", key, ex.getMessage(), ex);
                    }
                });
    }
}