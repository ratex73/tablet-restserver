package kr.co.yedaham.tablet.restserver.restserver.config.security;

import kr.co.yedaham.tablet.restserver.restserver.advice.exception.CAuthenticationEntryPointException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        //response.sendRedirect("/exception/accessdenied");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "1006");
    }
}
