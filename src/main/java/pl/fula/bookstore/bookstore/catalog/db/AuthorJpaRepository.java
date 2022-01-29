package pl.fula.bookstore.bookstore.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Author;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
