package xyz.vanez.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.vanez.dto.MostActiveRestaurantDto;
import xyz.vanez.dto.ReviewsByDayDto;
import xyz.vanez.dto.TopRatedRestaurantDto;
import xyz.vanez.model.Review;
import xyz.vanez.service.ReviewService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class DataController {

    private final ReviewService reviewService;

    @GetMapping("/search")
    public List<Review> search(@RequestParam String text) {
        log.info("GET /internal/search: {}", text);
        return reviewService.searchReviews(text);
    }

    @GetMapping("/reports/top-rated")
    public List<TopRatedRestaurantDto> topRated() {
        log.info("GET /internal/reports/top-rated");
        return reviewService.getTopRated();
    }

    @GetMapping("/reports/reviews-by-day")
    public List<ReviewsByDayDto> reviewsByDay() {
        log.info("GET /internal/reports/reviews-by-day");
        return reviewService.getReviewsByDay();
    }

    @GetMapping("/reports/most-active")
    public List<MostActiveRestaurantDto> mostActive() {
        log.info("GET /internal/reports/most-active");
        return reviewService.getMostActive();
    }
}