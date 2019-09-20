package pl.com.sages.bookstore.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.service.impl.BookStatsServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@RunWith(JUnitParamsRunner.class)
public class BookStatsServiceImplTest {

    BookStatsService bookStatsService = null;

    @Before
    public void init() {
        bookStatsService = new BookStatsServiceImpl();
    }

    @Test
    public void shouldThrowExceptionForNullBook() {
        log.info("Inside test");
        check();
        assertThatThrownBy(() -> bookStatsService.getMaxRating(null))
                .isInstanceOf(IllegalArgumentException.class);
        /*
        try {
            var rating = bookStatsService.getMaxRating(null);
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
        var rating = bookStatsService.getMaxRating(book);
        assertThat(rating).isEmpty();
        //assertEquals(rating, Optional.empty());
    }

    @Test
    public void shouldReturnEmptyResultForEmptyReviews() {
        var book = Book.builder().reviews(Collections.emptyList()).build();
        var rating = bookStatsService.getMaxRating(book);
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
        var rating = bookStatsService.getMaxRating(book);

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
        var rating = bookStatsService.getMaxRating(book);

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


    @Test
    public void shouldReturnThatThereAreNoDescriptionsForNullBook() {
        //when
        var isAnyDescription = bookStatsService.hasAnyDescription(null);

        //then
        assertThat(isAnyDescription).isFalse();
    }

    @Test
    public void shouldReturnThatThereAreNoDescriptionsForBookWithNoReviews() {
        //given
        var book = Book.builder().reviews(null).build();

        //when
        var isAnyDescription = bookStatsService.hasAnyDescription(book);

        //then
        assertThat(isAnyDescription).isFalse();
    }

    @Test
    @Parameters(method = "paramsForNotEmptyDescriptionsTest")
    public void shouldReturnTrueIfThereIsAtLeastOneNotEmptyDescription(List<String> descriptions) {
        //given
        var reviews = descriptions.stream().map(d -> Review.builder().description(d).build()).collect(toList());
        var book = Book.builder().reviews(reviews).build();

        //when
        var isAnyDescription = bookStatsService.hasAnyDescription(book);

        //then
        assertThat(isAnyDescription).isTrue();
    }

    private Object paramsForNotEmptyDescriptionsTest() {
        return new Object[] { //each array element represents data for one test run
                new Object[] {buildList("AAA", null, "", "BBB")},
                new Object[] {buildList("XXX")}
        };
    }

    @Test
    @Parameters(method = "paramsForEmptyDescriptionsTest")
    public void shouldReturnFalseIfThereAreEmptyDescriptionsOnly(List<String> descriptions) {
        //given
        var reviews = descriptions.stream().map(d -> Review.builder().description(d).build()).collect(toList());
        var book = Book.builder().reviews(reviews).build();

        //when
        var isAnyDescription = bookStatsService.hasAnyDescription(book);

        //then
        assertThat(isAnyDescription).isFalse();
    }

    private Object paramsForEmptyDescriptionsTest() {
        return new Object[] { //each array element represents data for one test run
                new Object[] {buildList(null, "")},
                new Object[] {buildList()}
        };
    }

    /* allows to create list with null values */
    private static List<String> buildList(String... args) {
        return Arrays.stream(args).collect(toCollection(ArrayList::new));
    }

}
