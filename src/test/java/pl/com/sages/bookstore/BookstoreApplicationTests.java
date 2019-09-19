package pl.com.sages.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.BookType;
import pl.com.sages.bookstore.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookstoreApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser
    public void shouldReturnAllBooksForAuthenticatedUser() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Dziady")))
                .andExpect(jsonPath("$[1].title", equalTo("Kordian")));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturn401ForAnonymousUserInsteadOfBooks() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldBeAbleToCreateBookForAdmin() throws Exception {
        var newBookDto = NewBookDto.builder()
                .title("Nie-Boska Komedia")
                .author("Krasinski")
                .price(40)
                .rating(60)
                .type(BookType.NEW)
                .build();
        var newBookDtoJson = objectMapper.writeValueAsString(newBookDto);
        MvcResult result = mvc.perform(post("/books").contentType("application/json").content(newBookDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue(Integer.class)))
                .andExpect(jsonPath("$.title", equalTo("Nie-Boska Komedia"))).andReturn();
        int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        assertThat(bookRepository.getOne(id)).isNotNull().hasFieldOrPropertyWithValue("title", "Nie-Boska Komedia");
    }

    @Test
    @WithMockUser
    public void shouldNotBeAbleToCreateBookForRegularUser() throws Exception {
        var newBookDto = NewBookDto.builder()
                .title("Nie-Boska Komedia")
                .author("Krasinski")
                .price(40)
                .rating(60)
                .type(BookType.NEW)
                .build();
        var newBookDtoJson = objectMapper.writeValueAsString(newBookDto);
        mvc.perform(post("/books").contentType("application/json").content(newBookDtoJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldBeAbleToDeleteBookForAdmin() throws Exception {
        var id = bookRepository.findAll().stream().map(Book::getId).findFirst().get();
        mvc.perform(delete("/books/" + id)).andExpect(status().isOk());
        assertThat(bookRepository.existsById(id)).isFalse();
    }

}
