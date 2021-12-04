package com.example.Estore.Estore.Exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is used to throw exception for wrong credentials and absent/wrong authorizations.
 */
@ControllerAdvice
public class LogInException implements AuthenticationEntryPoint {

    /**
     * Method is used to catch wrong credentials while login and wrong authorizations while accessing an endpoint.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authException AuthenticationException
     * @throws IOException Throws input/output exceptions.
     * @throws ServletException Throws servlet exceptions.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        json.put("httpStatus","Unauthorized client error");
        json.put("message", "Email or password incorrect");
        response.getWriter().write(json.toJSONString());
    }

    /**
     * Method used to catch any other unexpected exceptions.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException Throws input/output exceptions.
     */
    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         Exception exception) throws IOException {
        // 500
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.getMessage());
    }

}