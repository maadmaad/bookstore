package pl.fula.bookstore.bookstore.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    public List<Book> findByTitle(String title) {
        return catalogService.findByTitle(title);
    }
}
