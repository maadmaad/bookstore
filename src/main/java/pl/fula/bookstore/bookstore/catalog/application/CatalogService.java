package pl.fula.bookstore.bookstore.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.db.AuthorJpaRepository;
import pl.fula.bookstore.bookstore.catalog.db.BookJpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Author;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.uploads.application.port.UploadUseCase;
import pl.fula.bookstore.bookstore.uploads.application.port.UploadUseCase.SaveUploadCommand;
import pl.fula.bookstore.bookstore.uploads.domain.Upload;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CatalogService implements CatalogUseCase {
    private final BookJpaRepository bookRepository;
    private final AuthorJpaRepository authorRepository;
    private final UploadUseCase upload;

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = toBook(command);
        return bookRepository.save(book);
    }

    private Book toBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getYear(), command.getPrice());
        Set<Author> authors = fetchAuthorsByIds(command
                .getAuthorIds());
        updateBook(book, authors);
        return book;
    }

    private void updateBook(Book book, Set<Author> authors) {
        book.removeAuthors();
        authors.forEach(book::addAuthor);
    }

    private Set<Author> fetchAuthorsByIds(Set<Long> authorIds) {
        return authorIds
                .stream()
                .map(id -> authorRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find author with id: " + id)))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleStartsWithIgnoreCase(title);
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return bookRepository.findDistinctFirstByTitleContainsIgnoreCase(title);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return bookRepository.findById(command.getId())
                .map(book -> {
                    Book updatedBook = updateFields(book, command);
                    bookRepository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> {
                    String errorMsg = "Book not found with id: " + command.getId();
                    return new UpdateBookResponse(false, List.of(errorMsg));
                });
    }

    private Book updateFields(Book book, UpdateBookCommand command) {
        if (command.getNewTitle() != null) {
            book.setTitle(command.getNewTitle());
        }
        if (command.getNewAuthorIds() != null && !command.getNewAuthorIds().isEmpty()) {
            updateBook(book, fetchAuthorsByIds(command.getNewAuthorIds()));
        }
        if (command.getNewYear() != null) {
            book.setYear(command.getNewYear());
        }
        if (command.getNewPrice() != null) {
            book.setPrice(command.getNewPrice());
        }
        return book;
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        bookRepository.findById(command.getId())
                .ifPresent(book -> {
                    Upload savedUpload = upload.save(
                            new SaveUploadCommand(command.getFileName(), command.getFile(), command.getContentType()));
                    book.setCoverId(savedUpload.getId());
                    bookRepository.save(book);
                });
    }

    @Override
    public void removeBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void removeBookCover(Long id) {
        bookRepository.findById(id)
                .ifPresent(book -> {
                    if (book.getCoverId() != null) {
                        upload.removeById(book.getCoverId());
                        book.setCoverId(null);
                        bookRepository.save(book);
                    }
                });
    }
}
