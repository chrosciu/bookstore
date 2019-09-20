package pl.com.sages.bookstore.service;

import pl.com.sages.bookstore.model.Book;

import java.util.Optional;

public interface BookStatsService {
    Optional<Integer> getMaxRating(Book book);
}
