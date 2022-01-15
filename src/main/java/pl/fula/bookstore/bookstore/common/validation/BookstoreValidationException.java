package pl.fula.bookstore.bookstore.common.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookstoreValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BookstoreValidationException(String message, Exception e) {
        super(message, e);
    }
}
