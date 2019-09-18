package pl.com.sages.bookstore.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Integer id;
    private String description;
    private Integer rating;
}
