package kr.co.paywith.pw.handler;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {PwApiException.class})
  protected ResponseEntity<Object> handleApiException(PwApiException ex, WebRequest request) {
    log.warn("선불 연동 오류 >>> Exception", ex);

    return handleExceptionInternal(ex, ex.toMap(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(value = {ResourceAccessException.class})
  protected ResponseEntity<Object> handleResourceAccessException(ResourceAccessException ex, WebRequest request) {
    log.warn("멤버십서버 응답 없음 >>> Exception", ex);

    Map<String, Object> error = new HashMap<>();
    error.put("code", 3001);
    error.put("message", "연동 오류");

    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(value = {DataIntegrityViolationException.class})
  protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
      DataIntegrityViolationException ex,
      WebRequest request) {
    log.warn("데이터 연결 오류 >>> Exception", ex);

    Map<String, Object> error = new HashMap<>();
    error.put("code", 1001);
    error.put("message", "참조 데이터 존재");

    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    log.warn("DB 제약조건 오류 >>> Exception", ex);

    Map<String, Object> error = new HashMap<>();
    error.put("code", 8000);
    error.put("message", "제약조건 미일치");

    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    log.error("처리하지 못한 오류 >>> Exception", ex);

    Map<String, Object> error = new HashMap<>();
    error.put("code", "9999");
    error.put("message", "일반 오류");

    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }
}
