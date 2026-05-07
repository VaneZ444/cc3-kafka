package xyz.vanez.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.vanez.dto.MostActiveRestaurantDto;
import xyz.vanez.dto.TopRatedRestaurantDto;
import xyz.vanez.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCommentContainingIgnoreCase(String text);

    @Query("SELECT new xyz.vanez.dto.TopRatedRestaurantDto(r.restaurant.name, AVG(r.rating), COUNT(r)) " +
            "FROM Review r GROUP BY r.restaurant ORDER BY AVG(r.rating) DESC")
    List<TopRatedRestaurantDto> findTopRatedRestaurants(Pageable pageable);

    @Query(value = "SELECT DATE(created_at) as review_date, COUNT(*) FROM reviews " +
            "WHERE created_at >= CURRENT_DATE - INTERVAL '30 days' " +
            "GROUP BY review_date ORDER BY review_date", nativeQuery = true)
    List<Object[]> getReviewsCountByDay();

    @Query("SELECT new xyz.vanez.dto.MostActiveRestaurantDto(r.restaurant.name, COUNT(r)) " +
            "FROM Review r GROUP BY r.restaurant ORDER BY COUNT(r) DESC")
    List<MostActiveRestaurantDto> findMostActiveRestaurants(Pageable pageable);
}