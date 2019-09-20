package pl.com.sages.bookstore.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.service.impl.BookRatingServiceImpl;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@RunWith(JUnitParamsRunner.class)
public class BookRatingServiceImplTest {

    BookRatingService bookRatingService = null;

    @Before
    public void init() {
        bookRatingService = new BookRatingServiceImpl();
    }

    @Test
    public void shouldThrowExceptionForNullBook() {
        log.info("Inside test");
        check();
        assertThatThrownBy(() -> bookRatingService.getMaxRating(null))
                .isInstanceOf(IllegalArgumentException.class);
        /*
        try {
            var rating = bookRatingService.getMaxRating(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
         */
    }

    private void check() {
        //throw new IllegalArgumentException();
    }

    @Test
    public void shouldReturnEmptyResultForNoReviews() {
        var book = Book.builder().reviews(null).build();
        var rating = bookRatingService.getMaxRating(book);
        assertThat(rating).isEmpty();
        //assertEquals(rating, Optional.empty());
    }

    @Test
    public void shouldReturnEmptyResultForEmptyReviews() {
        var book = Book.builder().reviews(Collections.emptyList()).build();
        var rating = bookRatingService.getMaxRating(book);
        assertThat(rating).isEmpty();
        //assertEquals(rating, Optional.empty());
    }

    //duplicated with paramterized one
    @Test
    public void shouldGetMaxRatingIfThereAreAny() {
        //given
        var ratings = List.of(1, 4, 7, 6);
        var reviews = ratings.stream().map(r -> Review.builder().rating(r).build()).collect(toList());
        var book = Book.builder().reviews(reviews).build();

        //when
        var rating = bookRatingService.getMaxRating(book);

        //then
        assertThat(rating).contains(7);
        //assertEquals(rating, Optional.of(7));
    }


    @Test
    @Parameters(method = "paramsForRatingCalculation")
    public void shouldGetMaxRatingIfThereAreAnyParameterized(List<Integer> ratings, int expected) {
        //given
        var reviews = ratings.stream().map(r -> Review.builder().rating(r).build()).collect(toList());
        var book = Book.builder().reviews(reviews).build();

        //when
        var rating = bookRatingService.getMaxRating(book);

        //then
        assertThat(rating).contains(expected);
        //assertEquals(rating, expected);
    }

    private Object paramsForRatingCalculation() {
        return new Object[] { //each array element represents data for one test run
                new Object[] {List.of(1, 4, 7, 6), 7},
                new Object[] {List.of(1, 4, 6), 6}
        };
    }

}
