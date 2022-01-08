package pl.fula.bookstore.bookstore.catalog.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private final CatalogUseCase catalog;

//    @GetMapping(params = {"title"})                                                                                   // TODO 5.5 - Request Params. Specified "title" param is now mandatory for this endpoint
//    public List<Book> getAllFiltered1(@RequestParam String title) {
//        return catalog.findByTitle(title);
//    }

    @GetMapping()
    public List<Book> getAllFiltered1(@RequestParam Optional<String> title, @RequestParam Optional<String> author) {
        if (title.isPresent() && author.isPresent()) {
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return catalog.findByTitle(title.get());
        } else if (author.isPresent()) {
            return catalog.findByAuthor(author.get());
        }

        return catalog.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return catalog.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook(@Valid @RequestBody RestCreateBookCommand command) {
        Book book = catalog.addBook(command.toCreateBookCommand());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + book.getId().toString()).build().toUri();
        return ResponseEntity.created(uri).build();                                                                     // TODO 4.6 - return location of created Book in header
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        catalog.removeBookById(id);
    }

    @Data
    private static class RestCreateBookCommand {
        @NotBlank
        private String title;

        @NotBlank
        private String author;

        @NotNull
        private Integer year;

        @NotNull
        @DecimalMin("0.01")
        private BigDecimal price;

        CreateBookCommand toCreateBookCommand() {
            return new CreateBookCommand(title, author, year, price);
        }
    }
}
