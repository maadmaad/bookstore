package pl.fula.bookstore.bookstore;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.fula.bookstore.bookstore.common.validation.BookstoreValidationException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(BookstoreValidationException.class)
    public ResponseEntity<String> handleConflict(BookstoreValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Get all errors
        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " - " + x.getDefaultMessage())
                .sorted()
                .collect(Collectors.toList());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, status);
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
}
