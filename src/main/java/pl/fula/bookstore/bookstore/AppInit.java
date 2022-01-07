package pl.fula.bookstore.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.CatalogController;
import pl.fula.bookstore.bookstore.catalog.domain.Book;

import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private final CatalogController catalogController;

    public AppInit(CatalogController catalogueService) {
        this.catalogController = catalogueService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = catalogController.findByTitle("Pan");
        books.forEach(System.out::println);
    }
}
