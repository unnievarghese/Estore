package com.example.Estore.Estore.Security;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A Class that checks if the particular request has necessary authorizations.
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    /**
     * Class constructor.
     * @param authManager AuthenticationManager.
     * @param userRepository UserRepository.
     */
    public AuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }

    /**
     * Method acts as a filter, gets the authentication of a user from jwt token and sets the security context holder with the authentication.
     * @param req Http request.
     * @param res Http request.
     * @param chain FilterChain.
     * @throws IOException Throws input/output exceptions.
     * @throws ServletException Throws Servlet exceptions.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(req,res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req,res);
    }

    /**
     * Method is used to get the authentication of a user using its jwt token.
     * @param request Http request.
     * @return UsernamePasswordAuthenticationToken.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (user != null) {
                UserEntity userEntity = userRepository.findByEmail(user);
                UserPrincipal userPrincipal =new UserPrincipal(userEntity);
                return  new UsernamePasswordAuthenticationToken(user, null,
                                                                userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
