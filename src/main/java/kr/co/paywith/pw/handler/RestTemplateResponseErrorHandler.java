package kr.co.paywith.pw.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse)
      throws IOException {

    return (
        httpResponse.getStatusCode().series() == CLIENT_ERROR
            || httpResponse.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse)
      throws IOException {
    PwError error = this.getError(httpResponse.getBody());
    log.warn("선불서버 연동 오류({}) >>> {}", error.getCode(), error.getMessage());
    throw new PwApiException(error);
  }

  private PwError getError(InputStream errorResponse) {
    Map<String, String> map = null;

    try {
      map = new ObjectMapper().readValue(errorResponse, Map.class);
    } catch (IOException e) {
      log.warn("ERROR >>> 에러 코드 수신 비정상 : {}", errorResponse);
    }

    return new PwError(map);
  }

}