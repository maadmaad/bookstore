package pl.fula.bookstore.bookstore;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogueService {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    public CatalogueService() {
        books.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1901));
        books.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1902));
        books.put(3L, new Book(3L, "Chłopi", "Adam Mickiewicz", 1904));
    }

    List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
