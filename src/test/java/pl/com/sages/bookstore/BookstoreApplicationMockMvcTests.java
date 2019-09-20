package pl.com.sages.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Slf4j
public class BookstoreApplicationMockMvcTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser
    public void shouldReturnAllBooks() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Dziady")))
                .andExpect(jsonPath("$[1].title", equalTo("Kordian")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateBook() throws Exception {
        var newBookDto = NewBookDto.builder()
                .author("Tolkien")
                .title("Wladca pierscieni")
                .price(5)
                .rating(50)
                .build();
        var json = mapper.writeValueAsString(newBookDto);
        var result = mvc.perform(post("/books").contentType("application/json").content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", equalTo("Tolkien")))
                .andReturn();
        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        var book = bookRepository.getOne(id);
        assertThat(book).isNotNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldDeleteBookFromDatabase() throws Exception {
        var books = bookRepository.findAll();
        var id = books.get(0).getId();
        mvc.perform(delete("/books/" + id))
                .andExpect(status().isOk());
        assertThat(bookRepository.existsById(id)).isFalse();
    }

}
