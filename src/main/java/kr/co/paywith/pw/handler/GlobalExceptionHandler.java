package kr.co.paywith.pw.handler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import kr.co.paywith.pw.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler  {

  @ExceptionHandler(value = {java.sql.SQLException.class})
  protected ResponseEntity<Object> handleDBException(SQLException ex, WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "4100");
    error.put("message", String.format("입력한 데이터 형식에 오류가 있습니다(%s)", ex.getLocalizedMessage()));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "4100");
    error.put("message", "입력한 데이터 형식에 오류가 있습니다");

    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations()
        .forEach(c -> errors.put(((FieldError) c).getField(), ((FieldError) c).getDefaultMessage()));
    error.put("errors", errors);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {org.hibernate.exception.ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(org.hibernate.exception.ConstraintViolationException ex, WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "4100");
    error.put("message", String.format("입력한 데이터 형식에 오류가 있습니다(%s)", ex.getConstraintName()));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  //  @Override
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      WebRequest request) {
    log.error("데이터 검증 오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "2000");
    error.put("message", "저장 데이터에 오류가 있습니다");

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors()
        .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
    error.put("errors", errors);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NoSuchElementException.class})
  protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex,
      WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "2000");
    error.put("message", "요청한 데이터가 없습니다");
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {IncorrectResultSizeDataAccessException.class})
  protected ResponseEntity<Object> IncorrectResultSizeDataAccessException(
      IncorrectResultSizeDataAccessException ex,
      WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = new HashMap<>();
    error.put("code", "2000");
    error.put("message", String.format("데이터 크기가 일치하지 않습니다. (%2d, %2d)", ex.getExpectedSize(), ex.getActualSize()));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {CustomException.class})
  protected ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
    log.error("오류", ex);
    Map<String, Object> error = ex.toMap();
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler(value = {Exception.class})
//  protected ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
//    log.error("오류", ex);
//    Map<String, Object> error = new HashMap<>();
//    error.put("code", "9999");
//    error.put("message", ex.getLocalizedMessage());
//
//    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//  }
}
