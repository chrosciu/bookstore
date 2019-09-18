package pl.com.sages.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewBookDto {
    @NotBlank
    private String author;
    @NotBlank
    private String title;
    @NotNull
    @Min(1)
    @Max(100)
    private Integer rating;
    @NotNull
    @Min(0)
    private Integer price;
}
