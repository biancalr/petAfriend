package com.pets.petAfriend.infraestructures;

import com.pets.petAfriend.configs.PropertyConfig;
import com.pets.petAfriend.features.client.ClientException;
import com.pets.petAfriend.features.pet.PetException;
import com.pets.petAfriend.features.rent.RentException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private final PropertyConfig config;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("", ex);
        final var errors = config.getAll().entrySet().stream().filter(f -> ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList().stream().anyMatch(m -> m.contains(f.getKey()))).map(Map.Entry::getValue).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{status.value(), new Date(), errors});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpStatusCode status, WebRequest request) {
        log.error("", ex);
        final List<String> errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{status.value(), new Date(), errors});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Override
    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("", ex);
        final Map<String, String> errors = Map.of(Objects.requireNonNull(ex.getParameter().getParameterName()), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{status.value(), new Date(), errors});
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Object[]{status.value(), new Date(), Collections.singletonList(ex.getMessage())});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {PetException.class})
    public ResponseEntity<Object> handlePetException(PetException ex, WebRequest request) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{ex.getCode(), new Date(), Collections.singletonList(ex.getMessage())});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ClientException.class})
    public ResponseEntity<Object> handleClientException(ClientException ex, WebRequest request) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{ex.getCode(), new Date(), Collections.singletonList(ex.getMessage())});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {RentException.class})
    public ResponseEntity<Object> handleRentException(RentException ex, WebRequest request) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{ex.getCode(), new Date(), Collections.singletonList(ex.getMessage())});
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ParseException.class})
    public ResponseEntity<Object> handleParseException(ParseException ex, WebRequest request) {
        log.error("", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{400, new Date(), Collections.singletonList(ex.getMessage())});
    }

}
