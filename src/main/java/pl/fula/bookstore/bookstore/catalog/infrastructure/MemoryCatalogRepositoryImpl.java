package pl.fula.bookstore.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryCatalogRepositoryImpl implements CatalogRepository {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0L);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void save(Book book) {
        books.put(nextId(), book);
    }

    private long nextId() {
        return ID_NEXT_VALUE.incrementAndGet();
    }
}
