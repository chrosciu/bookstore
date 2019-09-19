package pl.com.sages.bookstore;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class User {
    private String name;
}

@Slf4j
public class JsonTest {

    @Test
    public void testMapping() throws Exception {
        var user = new User("Kowalski");
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(user);
        assertThat(json).isEqualTo("{\"name\":\"Kowalski\"}");
    }

}
