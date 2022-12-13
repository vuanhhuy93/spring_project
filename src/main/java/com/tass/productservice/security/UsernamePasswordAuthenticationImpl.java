package com.tass.productservice.security;

import com.tass.productservice.utils.JsonHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

public class UsernamePasswordAuthenticationImpl extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public UsernamePasswordAuthenticationImpl(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException {
        try {
            String userName = null;
            String password = null;
            String contentType = request.getContentType();
            if (contentType.equalsIgnoreCase("application/json")) {
                Map<String, String> params = JsonHelper.getStringMap(request.getInputStream());

                userName = params.get("username");
                password = params.get("password");
            } else {
                //  logger.debug(req.getRequestURI() + " Đăng nhập với params {} ;header {}", getParamsStr(req), header(req));
                userName = request.getParameter("username");
                password = request.getParameter("password");

            }
            logger.info(request.getRequestURI() + " Đăng nhập với body {} userName " +  userName);
            if (StringUtils.isEmpty(password) || StringUtils.isEmpty(userName)) {
                throw new AuthenticationServiceException("Invalid param");
            }

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userName,
                    password,
                    new ArrayList<>())
            );
        } catch (DisabledException ex) {
            logger.info("user block");
            throw ex;
        } catch (Exception e) {
            //  logger.error("login exception ", e);
            throw new AuthenticationServiceException("Invalid login request");
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
        throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
