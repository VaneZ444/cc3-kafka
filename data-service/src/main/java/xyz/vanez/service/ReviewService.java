package xyz.vanez.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.vanez.dto.MostActiveRestaurantDto;
import xyz.vanez.dto.ReviewRequest;
import xyz.vanez.dto.ReviewsByDayDto;
import xyz.vanez.dto.TopRatedRestaurantDto;
import xyz.vanez.model.Restaurant;
import xyz.vanez.model.Review;
import xyz.vanez.repository.RestaurantRepository;
import xyz.vanez.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public void saveReview(ReviewRequest req) {
        log.debug("Saving Review: {}", req);
        Restaurant restaurant = restaurantRepository.findByName(req.getRestaurantName())
                .orElseGet(() -> {
                    log.info("Created new restaurant: {}", req.getRestaurantName());
                    Restaurant r = new Restaurant();
                    r.setName(req.getRestaurantName());
                    r.setAddress(req.getRestaurantAddress());
                    return restaurantRepository.save(r);
                });
        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        log.info("Review saved: restaurant '{}', rating {}", restaurant.getName(), req.getRating());
    }

    public List<Review> searchReviews(String text) {
        log.debug("searchReviews: {}", text);
        return reviewRepository.findByCommentContainingIgnoreCase(text);
    }

    public List<TopRatedRestaurantDto> getTopRated() {
        log.debug("getTopRated");
        return reviewRepository.findTopRatedRestaurants(PageRequest.of(0, 10));
    }

    public List<ReviewsByDayDto> getReviewsByDay() {
        log.debug("getReviewsByDay");
        List<Object[]> rows = reviewRepository.getReviewsCountByDay();
        return rows.stream()
                .map(row -> new ReviewsByDayDto(
                        ((Date) row[0]).toLocalDate(),
                        ((Number) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<MostActiveRestaurantDto> getMostActive() {
        log.debug("getMostActive");
        return reviewRepository.findMostActiveRestaurants(PageRequest.of(0, 10));
    }
}