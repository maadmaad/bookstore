package pl.fula.bookstore.bookstore.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.catalog.application.port.AuthorUseCase;
import pl.fula.bookstore.bookstore.catalog.db.AuthorJpaRepository;
import pl.fula.bookstore.bookstore.catalog.domain.Author;

import java.util.List;

@Service
@AllArgsConstructor
class AuthorService implements AuthorUseCase {
    private AuthorJpaRepository authorJpaRepository;

    @Override
    public List<Author> findAll() {
        return authorJpaRepository.findAll();
    }
}
