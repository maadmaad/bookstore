package pl.fula.bookstore.bookstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fula.bookstore.bookstore.catalog.application.port.CatalogUseCase;

@Component
public class AppInit implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final String title;
    private final String author;
    private final Long limit;

    // TODO 12 - properties. (values in application.properties)
    //          ':' after properties is a default value (if proprty does not exist)
    public AppInit(CatalogUseCase catalogController, @Value("${bookstore.catalog.booktitle}") String title,
                   @Value("${bookstore.catalog.limit:2}") Long limit,
                   @Value("${bookstore.catalog.author}") String author) {
        this.catalog = catalogController;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Book> books = catalogController.findByTitle(title);
//        books.stream().limit(limit).forEach(System.out::println);

        catalog.findByAuthor(author).forEach(System.out::println);
    }
}
