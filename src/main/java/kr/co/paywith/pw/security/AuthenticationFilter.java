//package kr.co.paywith.pw.security;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import kr.co.paywith.pw.data.repository.admin.AdminLoginDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//
//
//    private final AuthenticationManager authenticationManager;
//
//    private String contentType;
//
//    public AuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req,
//                                                HttpServletResponse res) throws AuthenticationException {
//        try {
//
//        	contentType = req.getHeader("Accept");
//
//            AdminLoginDto creds = new ObjectMapper()
//                    .readValue(req.getInputStream(), AdminLoginDto.class);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getAdminId(),
//                            creds.getAdminPw(),
//                            new ArrayList<>())
//            );
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//
//        String userName = ((User) auth.getPrincipal()).getUsername();
//
//
//
//
////        // 1) Jwts ??????
////        String token = Jwts.builder()
////                .setSubject(userName)
////                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
////                .signWith(SignatureAlgorithm.HS512, "jf9i4jgu83nfl0jfu57ejf7")
////                .compact();
//////        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
//////        UserDto userDto = userService.getUser(userName);
////
////        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
////        // res.addHeader("UserID", userDto.getUserId());
//
//    }
//
//}
