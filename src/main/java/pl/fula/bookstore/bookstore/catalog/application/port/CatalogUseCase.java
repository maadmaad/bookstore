package pl.fula.bookstore.bookstore.catalog.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public interface CatalogUseCase {

    Book addBook(CreateBookCommand command);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    List<Book> findByTitleAndAuthor(String title, String author);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    public List<Book> findByTitle(String title);

    public Optional<Book> findOneByTitle(String title);

    public List<Book> findByAuthor(String author);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void updateBookCover(UpdateBookCoverCommand command);

    void removeBookById(Long id);

    void removeBookCover(Long id);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
        BigDecimal price;

        public Book toBook() {
            // todo efg - what about authors?
            return new Book(title, year, price);
        }
    }

    @Value
    @Builder
    @AllArgsConstructor
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
            // todo efg
//            if (newAuthor != null) {
//                updatedBook.setAuthor(newAuthor);
//            }
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

    @Value
    class UpdateBookCoverCommand {
        Long id;
        byte[] file;
        String contentType;
        String fileName;
    }
}
