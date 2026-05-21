package xyz.vanez.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "Название ресторана обязательно")
    private String restaurantName;

    @NotBlank(message = "Адрес ресторана обязателен")
    private String restaurantAddress;

    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 1, message = "Рейтинг должен быть от 1 до 5")
    @Max(value = 5, message = "Рейтинг должен быть от 1 до 5")
    private Integer rating;

    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(max = 1000, message = "Комментарий не длиннее 1000 символов")
    private String comment;

    private String messageId;
}