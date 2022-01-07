package pl.fula.bookstore.bookstore.catalog.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
