package pl.fula.bookstore.bookstore.catalog.application.port;

import pl.fula.bookstore.bookstore.catalog.domain.Author;

import java.util.List;

public interface AuthorUseCase {
    public List<Author> findAll();
}
