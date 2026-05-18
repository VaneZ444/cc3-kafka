package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import xyz.vanez.dto.ReviewMessage;
import xyz.vanez.dto.ReviewRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewConsumerService {

    private final ReviewService reviewService;
    private final KafkaTemplate<String, ReviewMessage> kafkaTemplate;
    private static final String DLQ_TOPIC = "dead-letter-reviews";

    @KafkaListener(topics = "reviews", groupId = "data-service-group")
    public void consume(ReviewMessage message, Acknowledgment ack) {
        log.info("Got Kafka message: {}", message);
        try {
            reviewService.saveReview(message);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing message, sending to DLQ", e);
            kafkaTemplate.send(DLQ_TOPIC, message.getRestaurantName(), message);
            ack.acknowledge();
        }
    }
}