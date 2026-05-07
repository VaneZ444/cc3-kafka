package xyz.vanez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostActiveRestaurantDto {
    private String restaurantName;
    private Long reviewCount;
}