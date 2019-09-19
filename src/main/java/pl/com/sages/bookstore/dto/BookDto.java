package pl.com.sages.bookstore.dto;

import lombok.Data;

@Data
public class BookDto {
    private Integer id;
    private String author;
    private String title;
    private Integer rating;
    private Integer price;
}
