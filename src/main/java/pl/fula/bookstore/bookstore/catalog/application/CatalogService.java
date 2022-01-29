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
        Set<Author> authors =  command.getAuthorIds().stream()
                .map(id -> authorRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find author with id: " + id)))
                .collect(Collectors.toSet());
        book.setAuthors(authors);
        return book;
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
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                // todo efg
//                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                // todo efg
//                .filter(b -> b.getAuthor().contains(author))
                .findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findAll().stream()
                // todo efg
//                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return bookRepository.findById(command.getId())
                .map(book -> {
                    Book updatedBook = command.updateFields(book);
                    bookRepository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> {
                    String errorMsg = "Book not found with id: " + command.getId();
                    return new UpdateBookResponse(false, List.of(errorMsg));
                });
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
