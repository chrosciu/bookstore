package pl.com.sages.bookstore.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class NewBookDto {
    @NotBlank
    private String author;
    @NotBlank
    private String title;
    @Min(1)
    @Max(100)
    @NotNull
    private Integer rating;
    @Min(0)
    @NotNull
    private Integer price;
}
