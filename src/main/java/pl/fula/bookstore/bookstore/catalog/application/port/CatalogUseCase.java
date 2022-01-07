package pl.fula.bookstore.bookstore.catalog.application.port;

import lombok.Value;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CatalogUseCase {

    void addBook(CreateBookCommand command);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    public List<Book> findByTitle(String title);

    public List<Book> findByAuthor(String author);

    void updateBook();

    void removeBook(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
    }
}
