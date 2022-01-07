package pl.fula.bookstore.bookstore.catalog.application.port;

import lombok.Value;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public interface CatalogUseCase {

    void addBook(CreateBookCommand command);

    List<Book> findAll();

//    Book findById(Long id);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    public List<Book> findByTitle(String title);

    public List<Book> findByAuthor(String author);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void removeBook(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookCommand {
        Long id;
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }
}
