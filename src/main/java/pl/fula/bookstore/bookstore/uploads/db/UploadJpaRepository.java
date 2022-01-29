package pl.fula.bookstore.bookstore.uploads.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fula.bookstore.bookstore.uploads.domain.Upload;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
