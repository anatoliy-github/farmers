package ru.net.pakhomov.farmers.api.exception;

import ru.net.pakhomov.farmers.api.exception.district.DistrictException;
import ru.net.pakhomov.farmers.api.exception.farmer.FarmerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DistrictException.class, FarmerException.class})
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            RuntimeException ex) {
        return new ResponseEntity<>(getErrorMap(ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    private Map<String, String> getErrorMap(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        return errorResponse;
    }
}
