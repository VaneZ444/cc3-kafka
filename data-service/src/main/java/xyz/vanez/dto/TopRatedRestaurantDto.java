package xyz.vanez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopRatedRestaurantDto {
    private String restaurantName;
    private Double averageRating;
    private Long reviewCount;
}