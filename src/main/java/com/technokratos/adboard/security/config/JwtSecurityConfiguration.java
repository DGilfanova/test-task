package com.technokratos.adboard.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technokratos.adboard.security.filter.JwtTokenAuthorizationFilter;
import com.technokratos.adboard.security.provider.JwtTokenProvider;
import com.technokratos.adboard.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PERMIT_ALL = {
            "/api/v1/login",
            "/api/v1/token/refresh",
            "/api/v1/advertisement",
            "/api/v1/advertisement/*",
            "/api/v1/register"
    };

    private static final String[] IGNORE = {
            "/ws/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(IGNORE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenAuthorizationFilter jwtTokenAuthenticationFilter() {
        return new JwtTokenAuthorizationFilter(objectMapper(), jwtTokenService, jwtTokenProvider);
    }
}
