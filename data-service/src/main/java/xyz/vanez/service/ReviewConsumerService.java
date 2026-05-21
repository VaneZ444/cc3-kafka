package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import xyz.vanez.dto.ReviewMessage;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewConsumerService {

    private final ReviewService reviewService;

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 10000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            autoCreateTopics = "true",
            include = {DataAccessException.class, TimeoutException.class,
                    SocketTimeoutException.class, IOException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "reviews", groupId = "data-service-group")
    public void consume(ReviewMessage message, Acknowledgment ack) {
        log.info("Got Kafka message: {}", message);
        try {
            reviewService.saveReview(message);
            //ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing message", e);
            throw new RuntimeException(e);
        }
    }
}