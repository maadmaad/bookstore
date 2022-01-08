package pl.fula.bookstore.bookstore.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CatalogService implements CatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Override
    public void addBook(CreateBookCommand command) {
        Book book = command.toBook();
        catalogRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return catalogRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                .filter(b -> b.getAuthor().contains(author))
                .findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return catalogRepository.findById(command.getId())
                .map(book -> {
                    Book updatedBook = command.updateFields(book);
                    catalogRepository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> {
                    String errorMsg = "Book not found with id: " + command.getId();
                    return new UpdateBookResponse(false, List.of(errorMsg));
                });
    }

    @Override
    public void removeBookById(Long id) {
        catalogRepository.removeById(id);
    }
}
