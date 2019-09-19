package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.service.BookService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class JdbcTemplateBookServiceImpl implements BookService {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<BookDto> getAllBooks() {
        //List<BookDto> books = jdbcTemplate.query("select * from books", manualRowMapper);
        List<BookDto> books = jdbcTemplate.query("select * from books", automaticRowMapper);
        return books;

    }

    @Override
    public BookDto createBook(NewBookDto newBookDto) {
        return null;
    }

    @Override
    public BookDto updateBook(int id, NewBookDto newBookDto) {
        //jdbcTemplate.update("update books set price = (?) where id = (?)", newBookDto.getPrice(), id);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", id);
        paramsMap.put("price", newBookDto.getPrice());
        namedParameterJdbcTemplate.update("update books set price = (:price) where id = (:id)", paramsMap);
        BookDto bookDto = jdbcTemplate.queryForObject("select * from books where id = (?)", automaticRowMapper, id);
        return bookDto;
    }

    @Override
    public void deleteBook(int id) {

    }

    private static RowMapper<BookDto> manualRowMapper = new RowMapper<BookDto>() {
        @Override
        public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookDto bookDto = new BookDto();
            bookDto.setId(rs.getInt("id"));
            bookDto.setTitle(rs.getString("title"));
            bookDto.setAuthor(rs.getString("author"));
            bookDto.setRating(rs.getInt("rating"));
            bookDto.setPrice(rs.getInt("price"));
            return bookDto;
        }
    };

    private static RowMapper<BookDto> automaticRowMapper = BeanPropertyRowMapper.newInstance(BookDto.class);
}
