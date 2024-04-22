package huce.edu.vn.appdocsach.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityExceptionHanlder implements AccessDeniedHandler, AuthenticationEntryPoint {
    
    @Autowired
    private ObjectMapper mapper;

    private AppLogger<SecurityExceptionHanlder> logger = new AppLogger<>(SecurityExceptionHanlder.class);
    
    // 403
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        writeResponse(ResponseCode.Forbidden, response);
    }

    // 401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        writeResponse(ResponseCode.Unauthorized, response);
    }

    private void writeResponse(ResponseCode responseCode, HttpServletResponse response) {
        response.setStatus(responseCode.getStatusCode().value());
        ErrorResponse apiResponse = new ErrorResponse();
        apiResponse.setCode(responseCode.getCode());
        apiResponse.setMessages(responseCode.getMessage());
        try {
            response.getWriter().write(mapper.writeValueAsString(apiResponse));
        } catch (IOException e) {
            logger.error(e);
        }
    }
    
}
