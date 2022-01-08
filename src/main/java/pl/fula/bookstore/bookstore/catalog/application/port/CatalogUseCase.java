package pl.fula.bookstore.bookstore.catalog.application.port;

import lombok.Builder;
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

    void removeBookById(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
    }

    @Value
    @Builder
    class UpdateBookCommand {
        Long id;
        String newTitle;
        String newAuthor;
        Integer newYear;

        public Book updateFields(Book updatedBook) {
            if (newTitle != null) {
                updatedBook.setTitle(newTitle);
            }
            if (newAuthor != null) {
                updatedBook.setAuthor(newAuthor);
            }
            if (newYear != null) {
                updatedBook.setYear(newYear);
            }
            return updatedBook;
        }
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }
}
