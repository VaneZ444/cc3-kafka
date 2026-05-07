package xyz.vanez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private String restaurantName;
    private String restaurantAddress;
    private Integer rating;
    private String comment;
}