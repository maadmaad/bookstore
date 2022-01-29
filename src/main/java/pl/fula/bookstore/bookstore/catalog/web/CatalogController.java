package pl.fula.bookstore.bookstore.catalog.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookCoverCommand;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.common.validation.NullOrNotBlank;
import pl.fula.bookstore.bookstore.common.validation.ValidationGroups.CreateBookValidationGroup;
import pl.fula.bookstore.bookstore.common.validation.ValidationGroups.UpdateBookValidationGroup;
import pl.fula.bookstore.bookstore.uploads.application.port.UploadUseCase;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private final CatalogUseCase catalog;
    private final UploadUseCase uploadUseCase;

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
        // TODO 4.9 - Exception Handling
        if (id.equals(100L)) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "I am a teapot. Sorry");
        }

        return catalog.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook_Validated(@Validated({CreateBookValidationGroup.class}) @RequestBody RestBookCommand command) {
        Book book = catalog.addBook(command.toCreateBookCommand());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + book.getId().toString()).build().toUri();
        return ResponseEntity.created(uri).build();                                                                     // TODO 4.6 - return location of created Book in header
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@PathVariable Long id, @Validated({UpdateBookValidationGroup.class}) @RequestBody RestBookCommand command) {
        UpdateBookResponse response = catalog.updateBook(command.toUpdateBookCommand(id));

        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PutMapping(value = "/{id}/cover", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addBookCover(@PathVariable Long id, @RequestParam("coverFile") MultipartFile file) throws IOException { // TODO nazwa 'coverFile' musi być używa przy tworzeniu zapytania PUT - w body wybieramy form-data i kluczem ma być 'coverFile' a wartością plik
        System.out.println("Got file: " + file.getName());

        catalog.updateBookCover(new UpdateBookCoverCommand(
                id,
                file.getBytes(),
                file.getContentType(),
                file.getOriginalFilename()
        ));
    }

    @DeleteMapping("/{id}/cover")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookCover(@PathVariable Long id) {
        catalog.removeBookCover(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        catalog.removeBookById(id);
    }

    @Data
    private static class RestBookCommand {
        @NotBlank(groups = CreateBookValidationGroup.class, message = "Please provide a title")
        @NullOrNotBlank(groups = UpdateBookValidationGroup.class)
        private String title;

        // todo efg
//        @NotBlank(groups = CreateBookValidationGroup.class)
//        @NullOrNotBlank(groups = UpdateBookValidationGroup.class)
//        private String author;

        @NotNull(groups = CreateBookValidationGroup.class)
        private Integer year;

        @NotNull(groups = CreateBookValidationGroup.class)
        @DecimalMin(groups = {CreateBookValidationGroup.class, UpdateBookValidationGroup.class}, value = "0.01")
        private BigDecimal price;

        CreateBookCommand toCreateBookCommand() {
            // todo efg
            return new CreateBookCommand(title, Set.of(), year, price);
        }

        UpdateBookCommand toUpdateBookCommand(Long id) {
            // todo efg
            return new UpdateBookCommand(id, title, Set.of(), year, price);
        }
    }

    // TODO 4.10 - @Valid, @Validated
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Void> addBook_Valid(@Valid @RequestBody RestBookCommand_Valid command) {
//        Book book = catalog.addBook(command.toCreateBookCommand());
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + book.getId().toString()).build().toUri();
//        return ResponseEntity.created(uri).build();                                                                     // TODO 4.6 - return location of created Book in header
//    }
//
//    @Data
//    private static class RestBookCommand_Valid {
//        @NotBlank(message = "Please provide a title")
//        private String title;
//
//        @NotBlank
//        private String author;
//
//        @NotNull
//        private Integer year;
//
//        @NotNull
//        @DecimalMin("0.01")
//        private BigDecimal price;
//
//        CreateBookCommand toCreateBookCommand() {
//            return new CreateBookCommand(title, author, year, price);
//        }
//
//        UpdateBookCommand toUpdateBookCommand(Long id) {
//            return new UpdateBookCommand(id, title, author, year, price);
//        }
//    }
}
