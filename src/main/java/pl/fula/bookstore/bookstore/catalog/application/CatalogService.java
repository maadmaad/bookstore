package pl.fula.bookstore.bookstore.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        catalogRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return Optional.empty();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getAuthor().contains(author))
                .collect(Collectors.toList());
    }

    @Override
    public void updateBook() {

    }

    @Override
    public void removeBook(Long id) {

    }
}
