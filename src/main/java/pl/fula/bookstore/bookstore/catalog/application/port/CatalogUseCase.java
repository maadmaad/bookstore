package pl.fula.bookstore.bookstore.catalog.application.port;

import lombok.Builder;
import lombok.Value;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public interface CatalogUseCase {

    void addBook(CreateBookCommand command);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findByTitleAndAuthor(String title, String author);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    public List<Book> findByTitle(String title);

    public Optional<Book> findOneByTitle(String title);

    public List<Book> findByAuthor(String author);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void removeBookById(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
        BigDecimal price;

        public Book toBook() {
            return new Book(title, author, year, price);
        }
    }

    @Value
    @Builder
    class UpdateBookCommand {
        Long id;
        String newTitle;
        String newAuthor;
        Integer newYear;
        BigDecimal newPrice;

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
            if (newPrice != null) {
                updatedBook.setPrice(newPrice);
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
