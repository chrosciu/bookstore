package pl.com.sages.bookstore.service;

import org.junit.Assert;
import org.junit.Test;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.service.impl.BookRatingServiceImpl;

import java.util.Optional;

public class BookRatingServiceImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullBook() {
        BookRatingService bookRatingService = new BookRatingServiceImpl();
        var rating = bookRatingService.getMaxRating(null);
    }

    @Test
    public void shouldReturnEmptyResultForNoReviews() {
        BookRatingService bookRatingService = new BookRatingServiceImpl();
        var book = Book.builder().reviews(null).build();
        var rating = bookRatingService.getMaxRating(book);
        Assert.assertEquals(rating, Optional.empty());
    }

}
