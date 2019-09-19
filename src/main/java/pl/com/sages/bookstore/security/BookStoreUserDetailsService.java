package pl.com.sages.bookstore.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookStoreUserDetailsService implements UserDetailsService {

    private static final BookUserDetails JASIO = new BookUserDetails("jasio", "jasio");
    private static final BookUserDetails MALGOSIA = new BookUserDetails("malgosia", "malgosia");
    private static final List<BookUserDetails> userDetails = List.of(JASIO, MALGOSIA);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetails.stream()
                .filter(bookUserDetails -> bookUserDetails.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
