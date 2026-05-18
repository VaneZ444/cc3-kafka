package xyz.vanez.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {
    private String restaurantName;
    private String restaurantAddress;
    private Integer rating;
    private String comment;
    private String messageId;
}