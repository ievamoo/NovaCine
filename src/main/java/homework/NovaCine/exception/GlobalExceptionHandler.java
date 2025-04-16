package homework.NovaCine.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            InsufficientSeatsException.class
    })
    public ResponseEntity<?> handleException(RuntimeException exception) {
        log.warn("[Exception]: {}", exception.getMessage());
        return generateResponse(exception);
    }

    private ResponseEntity<?> generateResponse(RuntimeException exception) {
        if (exception instanceof EntityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } else if (exception instanceof InsufficientSeatsException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}