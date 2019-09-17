package pl.com.sages.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String title;

    private String author;

    private Integer rating;

    private Integer price;

    private LocalDate creationDate;

    private LocalDate updateDate;

    @PrePersist
    private void onCreate() {
        creationDate = LocalDate.now();
        updateDate = LocalDate.now();
    }

    @PreUpdate
    private void onUpdate() {
        updateDate = LocalDate.now();
    }
}
