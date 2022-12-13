package com.tass.productservice.security;

import com.tass.productservice.services.UserDetailServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;




    public WebSecurity(UserDetailServiceImpl userDetailsServiceImpl) {
        passwordEncoder = new BCryptPasswordEncoder();
        userDetailsService = userDetailsServiceImpl;
    }

    @Override
    public void configure(
        org.springframework.security.config.annotation.web.builders.WebSecurity web)
        throws Exception {
        web.ignoring().antMatchers("/noauth/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter loginFilter =
            new UsernamePasswordAuthenticationImpl(authenticationManager());

        loginFilter.setFilterProcessesUrl("/login");

        HttpSecurity httpSercurity = http.headers().disable()
            .cors()
            .and()
            .requestCache().disable()
            .csrf().disable().authorizeRequests()
            .and()
            .addFilter(loginFilter);

        BasicAuthenticationFilter filter = new Oauth2AuthorizationFilter(authenticationManager());
        httpSercurity.addFilterBefore(filter, BasicAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling();

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
