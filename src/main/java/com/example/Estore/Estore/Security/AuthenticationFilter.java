package com.example.Estore.Estore.Security;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.SpringApplicationContext;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A Class that performs authentication of a particular request.
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * Class constructor.
     * @param authenticationManager AuthenticationManager.
     */
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method takes in login credentials and authenticates it with already available user data.
     * @param req Http request
     * @param res Http response
     * @return Authentication
     * @throws AuthenticationException Throws authentication exception.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException{
        try {
            UserLoginRequestModel creds = new ObjectMapper().
                    readValue(req.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),
                    creds.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method runs when a login request is successfully authenticated.
     * @param req Http request.
     * @param res Http response.
     * @param chain FilterChain.
     * @param auth Authentication
     * @throws IOException Throws input/output exceptions.
     * @throws ServletException Throws servlet exceptions.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain, Authentication auth)
            throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(userName).
                setExpiration(new Date(System.currentTimeMillis() +SecurityConstants.EXPIRATION_TIME)).
                signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret()).compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);

        res.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
        res.addHeader("UserId",userDto.getUserId());
    }
}
