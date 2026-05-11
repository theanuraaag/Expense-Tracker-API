package expense_tracker_api.common;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import expense_tracker_api.auth.EmailAlreadyRegisteredException;
import expense_tracker_api.auth.InvalidLoginException;
import expense_tracker_api.expense.ExpenseNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){
        var fieldErrors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a,b)->a));
        return ResponseEntity.badRequest().body(ApiError.withFields("Validation failed", fieldErrors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadArgument(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(ApiError.of(ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleDubplicateEmail(EmailAlreadyRegisteredException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.of(ex.getMessage()));
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ApiError> handleInvalidLogin(InvalidLoginException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.of(ex.getMessage()));
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ExpenseNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.of(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiError.of("Access Denied"));
    }
}