package pl.com.sages.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
