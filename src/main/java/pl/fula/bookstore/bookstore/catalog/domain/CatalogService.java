package pl.fula.bookstore.bookstore.catalog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
