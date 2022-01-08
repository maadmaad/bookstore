package pl.fula.bookstore.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.fula.bookstore.bookstore.catalog.domain.Book;
import pl.fula.bookstore.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong NEXT_ID = new AtomicLong(0L);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (books.containsKey(id)) {
            return Optional.of(books.get(id));
        }
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId());
        }

        books.put(book.getId(), book);
    }

    @Override
    public void removeById(Long id) {
        books.remove(id);
    }

    private long nextId() {
        return NEXT_ID.incrementAndGet();
    }
}
