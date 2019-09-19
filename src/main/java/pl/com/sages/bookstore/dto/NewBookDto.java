package pl.com.sages.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewBookDto {
    @NotBlank
    private String author;
    private String title;
    private Integer rating;
    private Integer price;
}
