package pl.fula.bookstore.bookstore.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fula.bookstore.bookstore.catalog.application.port.AuthorUseCase;
import pl.fula.bookstore.bookstore.catalog.domain.Author;

import java.util.List;

@RequestMapping("/authors")
@RestController
@AllArgsConstructor
public class AuthorsController {
    private final AuthorUseCase authorUseCase;

    @GetMapping()
    public List<Author> getAll() {
        return authorUseCase.findAll();
    }
}
