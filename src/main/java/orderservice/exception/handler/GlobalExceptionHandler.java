package orderservice.exception.handler;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.GenericResponse;
import orderservice.exception.DuplicateValueExistException;
import orderservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, HttpStatus status, @NonNull WebRequest request) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        GenericResponse genericResponse = new GenericResponse(false, "Invalid input details", errorMessage, status.value(), LocalDateTime.now());
        log.error("handling MethodArgumentNotValidException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, status);
    }

    @NonNull
    protected ResponseEntity<GenericResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.METHOD_NOT_ALLOWED.value(), LocalDateTime.now());
        log.error("handling HttpRequestMethodNotSupported...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        log.error("handling ResourceNotFoundException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = DuplicateValueExistException.class)
    public final ResponseEntity<GenericResponse> handleDuplicateValueExistException(DuplicateValueExistException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        log.error("handling DuplicateValueExistException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public final ResponseEntity<GenericResponse> handleIllegalStateException(IllegalStateException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        log.error("handling IllegalStateException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public final ResponseEntity<GenericResponse> handleHttpClientErrorException(HttpClientErrorException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        log.error("handling HttpClientErrorException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConnectException.class)
    public final ResponseEntity<GenericResponse> handleConnectException(ConnectException exception) {
        GenericResponse genericResponse = new GenericResponse(false, "Remote service refused to connect", Collections.emptyMap(), HttpStatus.SERVICE_UNAVAILABLE.value(), LocalDateTime.now());
        log.error("handling ConnectException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
