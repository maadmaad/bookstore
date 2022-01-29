package pl.fula.bookstore.bookstore.uploads.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] file;
    private String contentType;
    private String filename;

    @CreatedDate
    private LocalDateTime createdAt;

    public Upload(String filename, String contentType, byte[] file) {
        this.filename = filename;
        this.contentType = contentType;
        this.file = file;
    }
}
