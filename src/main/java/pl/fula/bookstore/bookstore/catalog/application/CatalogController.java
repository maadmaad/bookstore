package pl.fula.bookstore.bookstore.catalog.application;

import org.springframework.stereotype.Controller;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogService;

import java.util.List;

@Controller
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public List<Book> findByTitle(String title) {
        return catalogService.findByTitle(title);
    }
}
