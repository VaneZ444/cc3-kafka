package xyz.vanez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.vanez.model.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);
}