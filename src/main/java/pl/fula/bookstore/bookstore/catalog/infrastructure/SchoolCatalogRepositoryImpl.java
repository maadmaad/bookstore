package pl.fula.bookstore.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SchoolCatalogRepositoryImpl implements CatalogRepository {
        private final Map<Long, Book> books = new ConcurrentHashMap<>();

    public SchoolCatalogRepositoryImpl() {
        books.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1901));
        books.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1902));
        books.put(3L, new Book(3L, "Chłopi", "Adam Mickiewicz", 1904));
        books.put(3L, new Book(3L, "Pan Wołodyjowski", "Henryk Sienkiewicz", 1905));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
