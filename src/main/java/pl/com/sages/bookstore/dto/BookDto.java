package pl.com.sages.bookstore.dto;

import lombok.Data;
import pl.com.sages.bookstore.model.BookType;

@Data
public class BookDto {
    private Integer id;
    private String author;
    private String title;
    private Integer rating;
    private Integer price;
    private BookType type;
}
