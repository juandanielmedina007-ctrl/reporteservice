package com.medinagarces.reporteservice.controller;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<?> invalid(MethodArgumentNotValidException e){return bad(e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).collect(java.util.stream.Collectors.joining("; ")));}
 @ExceptionHandler({ConstraintViolationException.class,MethodArgumentTypeMismatchException.class,HttpMessageNotReadableException.class})
 ResponseEntity<?> malformed(Exception e){return bad("Solicitud inválida. Verifique DNI, identificador, paginación y formato JSON.");}
 @ExceptionHandler(ResponseStatusException.class)
 ResponseEntity<?> status(ResponseStatusException e){return ResponseEntity.status(e.getStatusCode()).body(Map.of("message",e.getReason()==null?"Error":e.getReason()));}
 private ResponseEntity<?> bad(String message){return ResponseEntity.badRequest().body(Map.of("message",message));}
}
