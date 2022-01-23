package pl.fula.bookstore.bookstore.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
