package pl.fula.bookstore.bookstore.catalog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    // TODO 11 - conflicts.
    //      method 1 - @Qualifier() - good approach
    public CatalogService(@Qualifier("schoolCatalogRepositoryImpl") CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(b -> b.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
