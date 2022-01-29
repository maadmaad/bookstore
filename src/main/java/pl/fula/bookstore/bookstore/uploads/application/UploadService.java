package pl.fula.bookstore.bookstore.uploads.application;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.fula.bookstore.bookstore.uploads.application.port.UploadUseCase;
import pl.fula.bookstore.bookstore.uploads.db.UploadJpaRepository;
import pl.fula.bookstore.bookstore.uploads.domain.Upload;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class UploadService implements UploadUseCase {
    private final UploadJpaRepository uploadRepository;

    @Override
    public Upload save(SaveUploadCommand command) {
        Upload upload = new Upload(
                command.getFilename(),
                command.getContentType(),
                command.getBytes()
        );
        uploadRepository.save(upload);
        System.out.println("Upload saved: " + upload.getFilename() + " with id: " + upload.getId());
        return upload;
    }

    @Override
    public Optional<Upload> getById(Long id) {
        return uploadRepository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        uploadRepository.deleteById(id);
    }
}
