package pl.com.sages.bookstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookWithReviewsDto {
    private Integer id;
    private String author;
    private String title;
    private Integer rating;
    private Integer price;
    private List<ReviewDto> reviews;
}
