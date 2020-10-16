package kr.co.paywith.pw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.paywith.pw.handler.PwApiException;
import kr.co.paywith.pw.handler.PwError;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class FrontInterceptor extends HandlerInterceptorAdapter {

  // 토큰 유무를 확인, 유효성은 멤버십 서버에서 검사
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    if (request.getHeader("Authorization") != null) {
      String[] authArr = request.getHeader("Authorization").split(" ");
      if (authArr.length == 1) {
        throw new PwApiException(new PwError("9000", "인증 정보 없음"));
      } else {
        return super.preHandle(request, response, handler);
      }
    }

    return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    super.postHandle(request, response, handler, modelAndView);
  }
}
