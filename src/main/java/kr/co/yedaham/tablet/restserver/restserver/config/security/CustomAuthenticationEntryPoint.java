package kr.co.yedaham.tablet.restserver.restserver.config.security;

import kr.co.yedaham.tablet.restserver.restserver.advice.ExceptionAdvice;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CAuthenticationEntryPointException;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CTokenExpiredException;
import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CUserNotFoundException;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Autowired
    ExceptionAdvice exceptionAdvice;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException,
            ServletException {
        //response.sendRedirect("/exception/entrypoint");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "1006");

    }
}
