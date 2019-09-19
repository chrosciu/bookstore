package pl.com.sages.bookstore.service.impl;

import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.service.BookStatsService;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class BookStatsServiceImpl implements BookStatsService {
    @Override
    public Optional<Integer> getMaxRating(Book book) {
        if (null == book) {
            throw new IllegalArgumentException();
        }
        var reviews = book.getReviews();
        if (null == reviews) {
            return Optional.empty();
        }
        return reviews.stream()
                .filter(Objects::nonNull)
                .map(Review::getRating)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(r -> r));
    }
}
