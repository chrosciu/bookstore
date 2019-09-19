package pl.com.sages.bookstore;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.service.BookStatsService;
import pl.com.sages.bookstore.service.impl.BookStatsServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@RunWith(JUnitParamsRunner.class)
public class BookStatsServiceTest {

    BookStatsService bookStatsService = new BookStatsServiceImpl();

    @Test
    public void shouldThrowExceptionWhenNullBookIsGiven() {
        assertThatThrownBy(() -> bookStatsService.getMaxRating(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Parameters(method = "paramsForEmptyRatingBook")
    public void shouldReturnEmptyMaxRatingIfNoRatingIsGiven(List<Integer> ratings) {
        Book book = Book.builder()
                .reviews(ratings.stream().map(r -> Review.builder().rating(r).build()).collect(Collectors.toList()))
                .build();
        var avg = bookStatsService.getMaxRating(book);
        assertThat(avg).isEmpty();
    }

    private Object paramsForEmptyRatingBook() {
        return new Object[]{
                new Object[]{List.of()}
        };
    }

    @Test
    @Parameters(method = "paramsForMaxRatingBook")
    public void shouldCalculateMaxRatingForBook(List<Integer> ratings, int expected) {
        Book book = Book.builder()
                .reviews(ratings.stream().map(r -> Review.builder().rating(r).build()).collect(Collectors.toList()))
                .build();
        var avg = bookStatsService.getMaxRating(book);
        assertThat(avg).isPresent().contains(expected);
    }

    private Object paramsForMaxRatingBook() {
        return new Object[]{
                new Object[]{List.of(1, 2, 3), 3},
                new Object[]{List.of(1), 1}
        };
    }

}
