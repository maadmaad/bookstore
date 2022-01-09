package pl.fula.bookstore.bookstore.uploads.application.port;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.fula.bookstore.bookstore.uploads.domain.Upload;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand {
        String filename;
        byte[] bytes;
        String contentType;
    }
}
