package pl.fula.bookstore.bookstore.catalog.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
// TODO 11 - conflicts. Third approach @Primary() - only here (no need to add @Qualifier)
//@Primary
public class BestsellerCatalogRepositoryImpl implements CatalogRepository {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    public BestsellerCatalogRepositoryImpl() {
        books.put(1L, new Book(1L, "Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
        books.put(2L, new Book(2L, "Władca Pierścieni: Dwie Wieże", "JPR Tolkien", 1954));
        books.put(3L, new Book(3L, "Mężczyźni, którzy nienawidzą kobiet", "Stieg Larsson", 2005));
        books.put(4L, new Book(4L, "Sezon Burz", "Andrzej Sapkowski", 2013));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
